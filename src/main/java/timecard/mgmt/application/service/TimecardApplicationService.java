package timecard.mgmt.application.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;
import timecard.mgmt.application.dto.EntryDTO;
import timecard.mgmt.application.dto.SubEntryDTO;
import timecard.mgmt.application.dto.SubmitTimecardDTO;
import timecard.mgmt.domain.common.exception.BusinessException;
import timecard.mgmt.domain.common.exception.ErrorKey;
import timecard.mgmt.domain.model.effortmgmt.effort.Effort;
import timecard.mgmt.domain.model.effortmgmt.effort.EffortRepository;
import timecard.mgmt.domain.service.ProjectService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static timecard.mgmt.domain.model.effortmgmt.effort.EffortStatus.SUBMITTED;

@Service
public class TimecardApplicationService {
    private final EffortRepository effortRepository;
    private final ProjectService projectService;

    public TimecardApplicationService(EffortRepository effortRepository, ProjectService projectService) {
        this.effortRepository = effortRepository;
        this.projectService = projectService;
    }

    public void submit(SubmitTimecardDTO submitTimecardDto) {
        List<Effort> toBeSavedEfforts = Lists.newArrayList();
        submitTimecardDto.getEntries()
                .forEach(entryDTO ->
                        toBeSavedEfforts.addAll(collectEffortsFromSubEntryDtos(entryDTO, submitTimecardDto.getEmployeeId())));

        Map<String, Set<String>> toBeVerifiedProjectId = toBeSavedEfforts.stream()
                .collect(Collectors
                        .toMap(Effort::getProjectId,
                                effort -> Sets.newHashSet(effort.getSubProjectId()),
                                this::mergeValueList));

        verifyProjectIdsExist(toBeVerifiedProjectId);

        this.effortRepository.saveAll(toBeSavedEfforts);
    }

    private void verifyProjectIdsExist(Map<String, Set<String>> toBeVerifiedProjectId) {
        Map<String, Set<String>> notExistsProjectIds = this.projectService.verifyProjectsExist(toBeVerifiedProjectId);

        if (!notExistsProjectIds.isEmpty()) {
            throw new BusinessException(ErrorKey.PROJECTS_OR_SUBPROJECTS_NOT_EXIST);
        }
    }

    private Set<String> mergeValueList(Set<String> oldValue, Set<String> newValue) {
        oldValue.addAll(newValue);
        return oldValue;
    }

    private List<Effort> collectEffortsFromSubEntryDtos(EntryDTO entryDTO, String employeeId) {
        List<Effort> efforts = Lists.newArrayList();
        entryDTO.getSubEntries().forEach(
                subEntryDTO -> efforts.addAll(
                        buildEffortsFromEffortDtos(
                                employeeId,
                                subEntryDTO,
                                entryDTO.getProjectId())
                ));

        return efforts;
    }

    private List<Effort> buildEffortsFromEffortDtos(String employeeId, SubEntryDTO subEntryDTO, String projectId) {
        return subEntryDTO.getEfforts().stream()
                .map(effortDTO -> new Effort(employeeId,
                        LocalDate.parse(effortDTO.getDate()), effortDTO.getWorkingHours(),
                    subEntryDTO.getLocationCode(), subEntryDTO.isBillable(),
                    effortDTO.getNote(), subEntryDTO.getSubProjectId(),
                        projectId, SUBMITTED))
                .collect(Collectors.toList());
    }
}
