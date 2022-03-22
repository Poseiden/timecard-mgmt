package working.hour.mgmt.application;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import working.hour.mgmt.application.dto.EntryDTO;
import working.hour.mgmt.application.dto.SubEntryDTO;
import working.hour.mgmt.application.dto.SubmitTimeCardDTO;
import working.hour.mgmt.domain.common.BusinessException;
import working.hour.mgmt.domain.model.working_hour_mgmt.effort.Effort;
import working.hour.mgmt.domain.repository.ProjectRepository;
import working.hour.mgmt.domain.service.TimeCardService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static working.hour.mgmt.domain.common.ErrorKey.PROJECT_NOT_EXISTS;

@Service
public class TimeCardApplicationService {
    private final TimeCardService timeCardService;
    private final ProjectRepository projectRepository;

    public TimeCardApplicationService(TimeCardService timeCardService,
                                      ProjectRepository projectRepository) {
        this.timeCardService = timeCardService;
        this.projectRepository = projectRepository;
    }

    public void submit(SubmitTimeCardDTO submitTimeCardDto) {
        Map<String, List<String>> toBeVerifiedProjectId = Maps.newHashMap();
        List<Effort> efforts = Lists.newArrayList();
        submitTimeCardDto.getEntryDTOList()
                .forEach(entryDTO ->
                        efforts.addAll(buildEffortsAndCollectProjectIds(toBeVerifiedProjectId, entryDTO, submitTimeCardDto.getEmployeeId())));

        Map<String, List<String>> verifyResult = this.projectRepository.verifyProjectsExist(toBeVerifiedProjectId);

        if (verifyResult.size() != 0) {
            throw new BusinessException(PROJECT_NOT_EXISTS);
        }

        this.timeCardService.saveAll(efforts);
    }

    private List<Effort> buildEffortsAndCollectProjectIds(Map<String, List<String>> toBeVerifiedProjectId, EntryDTO entryDTO, String employeeId) {
        List<Effort> efforts = Lists.newArrayList();
        entryDTO.getSubEntryDTOList().forEach(
                subEntryDTO -> {
                    String subProjectId = subEntryDTO.getSubProjectId();
                    toBeVerifiedProjectId.putIfAbsent(entryDTO.getProjectId(),
                            Lists.newArrayList(subProjectId)).add(subProjectId);
                    efforts.addAll(buildEffortsAccordingToEffortDTO(employeeId, subEntryDTO));
                });

        return efforts;
    }

    private List<Effort> buildEffortsAccordingToEffortDTO(String employeeId, SubEntryDTO subEntryDTO) {
        return subEntryDTO.getEffortDTOList().stream()
                .map(effortDTO -> new Effort(employeeId,
                    effortDTO.getDate(), effortDTO.getWorkingHours(),
                    subEntryDTO.getLocationCode(), subEntryDTO.isBillable(),
                    effortDTO.getNote(), subEntryDTO.getSubProjectId()))
                .collect(Collectors.toList());
    }
}
