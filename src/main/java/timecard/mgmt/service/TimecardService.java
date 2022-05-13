package timecard.mgmt.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;
import timecard.mgmt.bean.Effort;
import timecard.mgmt.bean.VerifyProjectExistDTO;
import timecard.mgmt.dao.EffortRepositoryImpl;
import timecard.mgmt.dto.SubmitTimecardDTO;
import timecard.mgmt.exception.BusinessException;
import timecard.mgmt.exception.ErrorKey;
import timecard.mgmt.proxy.ProjectServiceProxy;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static timecard.mgmt.bean.EffortStatus.SUBMITTED;
import static timecard.mgmt.exception.ErrorKey.WRONG_WORKING_HOURS;

@Service
public class TimecardService {
    //讲师注释：bad smell, 依赖倒置问题，直接依赖 repo 的具体实现，而没有依赖接口
    private final EffortRepositoryImpl effortRepository;
    private final ProjectServiceProxy projectService;

    public TimecardService(EffortRepositoryImpl effortRepository,
                           ProjectServiceProxy projectService) {
        this.effortRepository = effortRepository;
        this.projectService = projectService;
    }

    //讲师注释：bad smell, 方法命名。 建议将 save 改为 submitTimecard
    //讲师注释： bad smell, 过长方法，建议拆成三到四个方法
    public void save(SubmitTimecardDTO submitTimecardDto) {
        //讲师注释：bad smell, 建议将list改为 toBeSavedEfforts
        List<Effort> list = Lists.newArrayList();
        submitTimecardDto.getEntries().forEach(entry -> {
            //从 subentrydto 中组装出所需的 effort
            List<Effort> efforts = Lists.newArrayList();
            entry.getSubEntries().forEach(
                    subEntryDTO -> efforts.addAll(
                            // 将每一个 EffortDto 转换为 Effort
                            subEntryDTO.getEfforts().stream()
                                    //讲师注释: bad smell, 参数过多。建议使用 builder 模式
                                    .map(effortDTO -> {
                                        //讲师注释: bad smell, 特性依恋，可将校验逻辑抽成EffortDTO 的实例化方法
                                        if (effortDTO.getWorkingHours() < 4 || effortDTO.getWorkingHours() > 8) {
                                            throw new BusinessException(WRONG_WORKING_HOURS);
                                        }
                                        return new Effort(submitTimecardDto.getEmployeeId(),
                                                LocalDate.parse(effortDTO.getDate()), effortDTO.getWorkingHours(),
                                                subEntryDTO.getLocationCode(), subEntryDTO.isBillable(),
                                                effortDTO.getNote(), subEntryDTO.getSubProjectId(),
                                                entry.getProjectId(), SUBMITTED);
                                    })
                                    .collect(Collectors.toList())
                    ));

            list.addAll(efforts);
        });

        //讲师注释：bad smell, map 建议命名为toBeVerifiedProjectIds
        //从上一步的数据中拿出相应的 projectid 和 subprojectid , 并校验是否存在
        Map<String, Set<String>> map = list.stream()
                .collect(Collectors
                        .toMap(Effort::getProjectId,
                                effort -> Sets.newHashSet(effort.getSubProjectId()),
                                //如果key 相同，那么将新value合并入已有value
                                (oldValue, newValue) -> {
                                    oldValue.addAll(newValue);
                                    return oldValue;
                                }));

        //将 上一步的数据转换为 相应的 dto
        List<VerifyProjectExistDTO> toBeVerifiedProjectIds = map.entrySet()
                .stream()
                .map(e -> new VerifyProjectExistDTO(e.getKey(), e.getValue()))
                .collect(Collectors.toList());

        //讲师注释：bad smell, verifyData 建议改为verifyProjectsExist
        List<VerifyProjectExistDTO> notExistsProjectIds = this.projectService.verifyData(toBeVerifiedProjectIds);

        if (!notExistsProjectIds.isEmpty()) {
            throw new BusinessException(ErrorKey.PROJECTS_OR_SUBPROJECTS_NOT_EXIST);
        }

        this.effortRepository.saveAll(list);
    }

}
