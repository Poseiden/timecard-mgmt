package working.hour.mgmt.application;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import working.hour.mgmt.application.dto.SubmitTimeCardDTO;
import working.hour.mgmt.domain.model.working_hour_mgmt.effort.Effort;
import working.hour.mgmt.domain.repository.ProjectRepository;
import working.hour.mgmt.domain.service.TimeCardService;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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
//        submitTimeCardDto.getEntryDTOList()
//            .forEach(entryDTO -> {
//                entryDTO.getSubEntryDTOList().forEach(
//                        subEntryDTO -> {
//                            List<String> subProjectIdList = toBeVerifiedProjectId.get(entryDTO.getProjectId());
//                            if (Objects.nonNull(subProjectIdList)) {
//                                subProjectIdList.add(subEntryDTO.getSubProjectId());
//                            } else {
//                                List<String> subProjectIds = Lists.newArrayList();
//                                subProjectIds.add(subEntryDTO.getSubProjectId());
//                                toBeVerifiedProjectId.put(entryDTO.getProjectId(), subProjectIds);
//                            }
//
//                            subEntryDTO.getEffortDTOList().forEach(
//                                effortDTO ->
//                                        efforts.add(new Effort(submitTimeCardDto.getEmployeeId(),
//                                                effortDTO.getDate(), effortDTO.getWorkingHours(),
//                                                subEntryDTO.getLocationCode(), subEntryDTO.isBillable(),
//                                                effortDTO.getNote(), subEntryDTO.getSubProjectId())));
//                        });
//            });

        Map<String, List<String>> verifyResult = this.projectRepository.verifyProjectsExist(toBeVerifiedProjectId);

        if (verifyResult.size() != 0) {
            //todo to throw exception and break
        }

        this.timeCardService.save(efforts);
    }
}
