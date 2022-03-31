package working.hour.mgmt.application;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import working.hour.mgmt.application.dto.EntryDTO;
import working.hour.mgmt.application.dto.SubEntryDTO;
import working.hour.mgmt.application.dto.SubmitTimecardDTO;
import working.hour.mgmt.domain.common.exception.BusinessException;
import working.hour.mgmt.domain.model.effortmgmt.effort.Effort;
import working.hour.mgmt.domain.service.ProjectService;
import working.hour.mgmt.domain.service.TimecardService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static working.hour.mgmt.domain.common.exception.ErrorKey.PROJECT_NOT_EXISTS;

@Service
public class TimecardApplicationService {
    private final TimecardService timecardService;
    private final ProjectService projectService;

    public TimecardApplicationService(TimecardService timecardService, ProjectService projectService) {
        this.timecardService = timecardService;
        this.projectService = projectService;
    }

    public void submit(SubmitTimecardDTO submitTimeCardDto) {
        Map<String, List<String>> toBeVerifiedProjectId = Maps.newHashMap();
        List<Effort> efforts = Lists.newArrayList();

        submitTimeCardDto.getEntries()
                .forEach(entryDTO ->
                        efforts.addAll(
                                buildEffortsAndCollectProjectIds(toBeVerifiedProjectId, entryDTO, submitTimeCardDto.getEmployeeId())));

        Map<String, List<String>> notExistsProjectIds = this.projectService.verifyProjectsExist(toBeVerifiedProjectId);

        if (!notExistsProjectIds.isEmpty()) {
            throw new BusinessException(PROJECT_NOT_EXISTS);
        }

        this.timecardService.saveAll(efforts);
    }

    //todo remove toBeVerifiedProjectId parameter
    private List<Effort> buildEffortsAndCollectProjectIds(Map<String, List<String>> toBeVerifiedProjectId, EntryDTO entryDTO, String employeeId) {
        List<Effort> efforts = Lists.newArrayList();
        entryDTO.getSubEntries().forEach(
                subEntryDTO -> {
                    String subProjectId = subEntryDTO.getSubProjectId();
                    if (toBeVerifiedProjectId.containsKey(entryDTO.getProjectId())) {
                        toBeVerifiedProjectId.get(entryDTO.getProjectId()).add(subProjectId);
                    } else {
                        toBeVerifiedProjectId.putIfAbsent(entryDTO.getProjectId(),
                                Lists.newArrayList(subProjectId));
                    }
                    efforts.addAll(buildEffortsAccordingToEffortDTO(employeeId, subEntryDTO));
                });

        return efforts;
    }

    private List<Effort> buildEffortsAccordingToEffortDTO(String employeeId, SubEntryDTO subEntryDTO) {
        return subEntryDTO.getEfforts().stream()
                .map(effortDTO -> new Effort(employeeId,
                        LocalDate.parse(effortDTO.getDate()), effortDTO.getWorkingHours(),
                    subEntryDTO.getLocationCode(), subEntryDTO.isBillable(),
                    effortDTO.getNote(), subEntryDTO.getSubProjectId()))
                .collect(Collectors.toList());
    }
}
