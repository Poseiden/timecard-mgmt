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
    //todo 依赖倒置问题，直接依赖 repo 的具体实现，而没有依赖接口
    private final EffortRepository effortRepository;
    private final ProjectServiceProxy projectService;

    public TimecardService(EffortRepository effortRepository,
                                      ProjectServiceProxy projectService) {
        this.effortRepository = effortRepository;
        this.projectService = projectService;
    }

    //todo application service 和 domain service 分层不清晰。 此方法以及相应的依赖都应该放在 TimecardApplicationService 中
    //todo 方法命名。 save* 应为 submit*，save 有其他业务含义
    public void saveTimecard(List<EntryDTO> entries, String employeeId) {
        // 加注释，模拟开发日常通过注释理解代码的场景
        List<Effort> toBeSavedEfforts = Lists.newArrayList();
        entries.forEach(entry -> {
            List<Effort> efforts = Lists.newArrayList();
            entry.getSubEntries().forEach(
                    subEntryDTO -> efforts.addAll(
                            subEntryDTO.getEfforts().stream()
                                    //todo 参数过多。使用 builder 模式
                                    //todo 设计 effort 一些自校验，用来凸显 特性依恋 坏味道
                                    .map(effortDTO -> new Effort(employeeId,
                                            LocalDate.parse(effortDTO.getDate()), effortDTO.getWorkingHours(),
                                            subEntryDTO.getLocationCode(), subEntryDTO.isBillable(),
                                            effortDTO.getNote(), subEntryDTO.getSubProjectId(),
                                            entry.getProjectId(), SUBMITTED))
                                    .collect(Collectors.toList())
                    ));

            toBeSavedEfforts.addAll(efforts);
        });

        // 加注释，模拟开发日常通过注释理解代码的场景
        Map<String, Set<String>> toBeVerifiedProjectIds1 = toBeSavedEfforts.stream()
                .collect(Collectors
                        .toMap(Effort::getProjectId,
                                effort -> Sets.newHashSet(effort.getSubProjectId()),
                                // 加注释，模拟开发日常通过注释理解代码的场景
                                (oldValue, newValue) -> {
                                    oldValue.addAll(newValue);
                                    return oldValue;
                                }));

        // 加注释，模拟开发日常通过注释理解代码的场景
        List<VerifyProjectExistDTO> toBeVerifiedProjectIds = toBeVerifiedProjectIds1.entrySet()
                .stream()
                .map(e -> new VerifyProjectExistDTO(e.getKey(), e.getValue()))
                .collect(Collectors.toList());

        List<VerifyProjectExistDTO> notExistsProjectIds = this.projectService.verifyProjectsExist(toBeVerifiedProjectIds);

        if (!notExistsProjectIds.isEmpty()) {
            throw new BusinessException(ErrorKey.PROJECTS_OR_SUBPROJECTS_NOT_EXIST);
        }

        this.effortRepository.saveAll(toBeSavedEfforts);
    }

}
