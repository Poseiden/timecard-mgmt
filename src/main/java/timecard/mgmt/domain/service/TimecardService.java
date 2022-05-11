package timecard.mgmt.domain.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;
import timecard.mgmt.application.dto.EntryDTO;
import timecard.mgmt.domain.common.exception.BusinessException;
import timecard.mgmt.domain.common.exception.ErrorKey;
import timecard.mgmt.domain.dto.VerifyProjectExistDTO;
import timecard.mgmt.domain.model.effortmgmt.effort.Effort;
import timecard.mgmt.domain.model.effortmgmt.effort.EffortRepository;
import timecard.mgmt.infrastructure.proxy.ProjectServiceProxy;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static timecard.mgmt.domain.model.effortmgmt.effort.EffortStatus.SUBMITTED;

@Service
public class TimecardService {
    private final EffortRepository effortRepository;
    private final ProjectServiceProxy projectService;

    public TimecardService(EffortRepository effortRepository,
                                      ProjectServiceProxy projectService) {
        this.effortRepository = effortRepository;
        this.projectService = projectService;
    }

    public void saveTimecard(List<EntryDTO> entries, String employeeId) {
        List<Effort> toBeSavedEfforts = Lists.newArrayList();
        entries.forEach(entryDTO -> {
            List<Effort> efforts = Lists.newArrayList();
            entryDTO.getSubEntries().forEach(
                    subEntryDTO -> efforts.addAll(
                            subEntryDTO.getEfforts().stream()
                                    .map(effortDTO -> new Effort(employeeId,
                                            LocalDate.parse(effortDTO.getDate()), effortDTO.getWorkingHours(),
                                            subEntryDTO.getLocationCode(), subEntryDTO.isBillable(),
                                            effortDTO.getNote(), subEntryDTO.getSubProjectId(),
                                            entryDTO.getProjectId(), SUBMITTED))
                                    .collect(Collectors.toList())
                    ));

            toBeSavedEfforts.addAll(efforts);
        });

        Map<String, Set<String>> toBeVerifiedProjectIds1 = toBeSavedEfforts.stream()
                .collect(Collectors
                        .toMap(Effort::getProjectId,
                                effort -> Sets.newHashSet(effort.getSubProjectId()),
                                this::mergeValueList));

        List<VerifyProjectExistDTO> toBeVerifiedProjectIds = toBeVerifiedProjectIds1.entrySet()
                .stream()
                .map(e -> new VerifyProjectExistDTO(e.getKey(), e.getValue()))
                .collect(Collectors.toList());

        verifyProjectIdsExist(toBeVerifiedProjectIds);

        this.effortRepository.saveAll(toBeSavedEfforts);
    }

    private void verifyProjectIdsExist(List<VerifyProjectExistDTO> toBeVerifiedProjectIds) {
        List<VerifyProjectExistDTO> notExistsProjectIds = this.projectService.verifyProjectsExist(toBeVerifiedProjectIds);

        if (!notExistsProjectIds.isEmpty()) {
            throw new BusinessException(ErrorKey.PROJECTS_OR_SUBPROJECTS_NOT_EXIST);
        }
    }

    private Set<String> mergeValueList(Set<String> oldValue, Set<String> newValue) {
        oldValue.addAll(newValue);
        return oldValue;
    }

}
