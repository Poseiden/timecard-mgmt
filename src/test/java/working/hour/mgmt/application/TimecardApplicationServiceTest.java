package working.hour.mgmt.application;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import working.hour.mgmt.application.dto.EffortDTO;
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
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static working.hour.mgmt.domain.model.effortmgmt.effort.EffortStatus.SUBMITTED;

@RunWith(MockitoJUnitRunner.class)
public class TimecardApplicationServiceTest {
    @Mock
    private ProjectService projectService;
    @Mock
    private TimecardService timecardService;

    @Test
    public void should_save_all_efforts_when_request() {
        //given
        TimecardApplicationService timeCardApplicationService = new TimecardApplicationService(timecardService, projectService);
        when(this.projectService.verifyProjectsExist(any())).thenReturn(Maps.newHashMap());
        doNothing().when(this.timecardService).saveAll(isA(List.class));

        //when
        SubmitTimecardDTO submitTimeCardDTO = buildSubmitTimeCardDTOData();
        timeCardApplicationService.submit(submitTimeCardDTO);

        //then
        EntryDTO expectEntry = submitTimeCardDTO.getEntries().get(0);
        SubEntryDTO expectSubEntryDTO = expectEntry.getSubEntries().get(0);
        EffortDTO expectEffortDTO = expectSubEntryDTO.getEfforts().get(0);

        Effort expectEffort = new Effort();
        expectEffort.setEmployeeId(submitTimeCardDTO.getEmployeeId());
        expectEffort.setLocationId(expectSubEntryDTO.getLocationCode());
        expectEffort.setNote(expectEffortDTO.getNote());
        expectEffort.setSubProjectId(expectSubEntryDTO.getSubProjectId());
        expectEffort.setWorkingDay(LocalDate.parse(expectEffortDTO.getDate()));
        expectEffort.setWorkingHours(expectEffortDTO.getWorkingHours());
        expectEffort.setBillable(expectSubEntryDTO.isBillable());
        expectEffort.setProjectId(expectEntry.getProjectId());
        expectEffort.setEffortStatus(SUBMITTED);

        Map<String, Set<String>> projectIdMap = Maps.newHashMap();
        projectIdMap.put(submitTimeCardDTO.getEntries().get(0).getProjectId(), Sets.newHashSet(expectSubEntryDTO.getSubProjectId()));

        verify(this.projectService, times(1)).verifyProjectsExist(projectIdMap);
        verify(this.timecardService, times(1)).saveAll(Lists.newArrayList(expectEffort));
    }

    @Test(expected = BusinessException.class)
    public void should_throw_exception_when_project_id_not_exists_() {
        //given
        TimecardApplicationService timeCardApplicationService = new TimecardApplicationService(timecardService, projectService);
        Map<String, Set<String>> errorResult = Maps.newHashMap();
        errorResult.put("error project id", Sets.newHashSet());
        when(this.projectService.verifyProjectsExist(any())).thenReturn(errorResult);

        //when
        SubmitTimecardDTO submitTimeCardDTO = buildSubmitTimeCardDTOData();
        timeCardApplicationService.submit(submitTimeCardDTO);

        //then
        EntryDTO expectEntry = submitTimeCardDTO.getEntries().get(0);
        SubEntryDTO expectSubEntryDTO = expectEntry.getSubEntries().get(0);
        Map<String, Set<String>> projectIdMap = Maps.newHashMap();
        projectIdMap.put(submitTimeCardDTO.getEntries().get(0).getProjectId(), Sets.newHashSet(expectSubEntryDTO.getSubProjectId()));

        verify(this.projectService, times(1)).verifyProjectsExist(projectIdMap);
        verify(this.timecardService, never()).saveAll(any());
    }

    private SubmitTimecardDTO buildSubmitTimeCardDTOData() {
        SubmitTimecardDTO submitTimeCardDTO = new SubmitTimecardDTO();
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