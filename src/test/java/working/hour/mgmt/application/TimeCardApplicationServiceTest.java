package working.hour.mgmt.application;

import com.google.common.collect.Maps;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import working.hour.mgmt.application.dto.EffortDTO;
import working.hour.mgmt.application.dto.EntryDTO;
import working.hour.mgmt.application.dto.SubEntryDTO;
import working.hour.mgmt.application.dto.SubmitTimeCardDTO;
import working.hour.mgmt.domain.model.effortmgmt.effort.Effort;
import working.hour.mgmt.domain.service.ProjectService;
import working.hour.mgmt.domain.service.TimeCardService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TimeCardApplicationServiceTest {
    @Mock
    private ProjectService projectService;
    @Mock
    private TimeCardService timeCardService;

    @Test
    public void should_save_all_efforts_when_request() {
        //given
        TimeCardApplicationService timeCardApplicationService = new TimeCardApplicationService(timeCardService, projectService);
        when(this.projectService.verifyProjectsExist(any())).thenReturn(Maps.newHashMap());
        doNothing().when(this.timeCardService).saveAll(isA(List.class));

        //when
        SubmitTimeCardDTO submitTimeCardDTO = buildSubmitTimeCardDTOData();
        timeCardApplicationService.submit(submitTimeCardDTO);

        //then
        SubEntryDTO subEntryDTO = submitTimeCardDTO.getEntries().get(0).getSubEntries().get(0);
        EffortDTO effortDTO = subEntryDTO.getEfforts().get(0);

        Effort effort = new Effort();
        effort.setEmployeeId(submitTimeCardDTO.getEmployeeId());
        effort.setLocationId(subEntryDTO.getLocationCode());
        effort.setNote(effortDTO.getNote());
        effort.setSubProjectId(subEntryDTO.getSubProjectId());
        effort.setWorkingDay(LocalDate.parse(effortDTO.getDate()));
        effort.setWorkingHours(effortDTO.getWorkingHours());
        effort.setBillable(subEntryDTO.isBillable());

        Map<String, List<String>> projectIdMap = Maps.newHashMap();
        projectIdMap.put(submitTimeCardDTO.getEntries().get(0).getProjectId(), Lists.newArrayList(subEntryDTO.getSubProjectId()));

        verify(this.projectService, times(1)).verifyProjectsExist(projectIdMap);
        verify(this.timeCardService, times(1)).saveAll(Lists.newArrayList(effort));
    }

    private SubmitTimeCardDTO buildSubmitTimeCardDTOData() {
        SubmitTimeCardDTO submitTimeCardDTO = new SubmitTimeCardDTO();
        submitTimeCardDTO.setEmployeeId("employeeId");

        EntryDTO entryDTO = new EntryDTO();
        entryDTO.setProjectId("projectId");

        EffortDTO effortDTO = new EffortDTO();
        effortDTO.setDate(LocalDate.of(2022, 1, 1).toString());
        effortDTO.setWorkingHours(8);
        effortDTO.setNote("note");

        SubEntryDTO subEntryDTO = new SubEntryDTO();
        subEntryDTO.setBillable(true);
        subEntryDTO.setLocationCode("CN");
        subEntryDTO.setSubProjectId("subprojectID");

        subEntryDTO.setEfforts(Lists.newArrayList(effortDTO));
        entryDTO.setSubEntries(Lists.newArrayList(subEntryDTO));
        submitTimeCardDTO.setEntries(Lists.newArrayList(entryDTO));

        return submitTimeCardDTO;
    }



}