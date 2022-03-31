package working.hour.mgmt.application;

import com.google.common.collect.Lists;
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
import static working.hour.mgmt.domain.model.effortmgmt.effort.EffortStatus.SUBMITTED;

@Service
public class TimecardApplicationService {
    private final TimecardService timecardService;
    private final ProjectService projectService;

    public TimecardApplicationService(TimecardService timecardService, ProjectService projectService) {
        this.timecardService = timecardService;
        this.projectService = projectService;
    }

    public void submit(SubmitTimecardDTO submitTimecardDto) {
        List<Effort> efforts = Lists.newArrayList();

        submitTimecardDto.getEntries()
                .forEach(entryDTO ->
                        efforts.addAll(buildEfforts(entryDTO, submitTimecardDto.getEmployeeId())));

        Map<String, List<String>> toBeVerifiedProjectId = efforts.stream()
                .collect(Collectors
                        .toMap(Effort::getProjectId,
                                e -> Lists.newArrayList(e.getSubProjectId()),
                                this::mergeValueList));

        Map<String, List<String>> notExistsProjectIds = this.projectService.verifyProjectsExist(toBeVerifiedProjectId);

        if (!notExistsProjectIds.isEmpty()) {
            throw new BusinessException(PROJECT_NOT_EXISTS);
        }

        this.timecardService.saveAll(efforts);
    }

    private List<String> mergeValueList(List<String> oldValue, List<String> newValue) {
        oldValue.addAll(newValue);
        return oldValue;
    }

    private List<Effort> buildEfforts(EntryDTO entryDTO, String employeeId) {
        List<Effort> efforts = Lists.newArrayList();
        entryDTO.getSubEntries().forEach(
                subEntryDTO -> efforts.addAll(
                        buildEffortsAccordingToEffortDTO(employeeId, subEntryDTO, entryDTO.getProjectId())
                ));

        return efforts;
    }

    private List<Effort> buildEffortsAccordingToEffortDTO(String employeeId, SubEntryDTO subEntryDTO, String projectId) {
        return subEntryDTO.getEfforts().stream()
                .map(effortDTO -> new Effort(employeeId,
                        LocalDate.parse(effortDTO.getDate()), effortDTO.getWorkingHours(),
                    subEntryDTO.getLocationCode(), subEntryDTO.isBillable(),
                    effortDTO.getNote(), subEntryDTO.getSubProjectId(),
                        projectId, SUBMITTED))
                .collect(Collectors.toList());
    }
}
