package working.hour.mgmt.presentation.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import working.hour.mgmt.application.dto.EffortDTO;
import working.hour.mgmt.application.dto.EntryDTO;
import working.hour.mgmt.application.dto.SubEntryDTO;
import working.hour.mgmt.application.dto.SubmitTimecardDTO;
import working.hour.mgmt.domain.model.effortmgmt.effort.Effort;
import working.hour.mgmt.domain.service.ProjectService;
import working.hour.mgmt.infrastructure.persistence.hibernate.EffortRepoJPA;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TimecardControllerTest extends BaseTest {
    @Autowired
    private EffortRepoJPA effortRepoJPA;

    @MockBean
    private ProjectService projectService;

    @Test
    public void should_return_success_when_submit_time_card() throws Exception {
        //given
        when(projectService.verifyProjectsExist(any())).thenReturn(Maps.newHashMap());

        //when
        SubmitTimecardDTO submitTimeCardDTO = buildSubmitTimeCardDTO();
        this.mockMvc.perform(post("/timecards/submit")
                        .contentType("application/json")
                        .content(JSON.toJSONString(submitTimeCardDTO)))
                .andExpect(status().isOk())
                .andDo(print());

        //then
        Effort ActualEffort = effortRepoJPA.findAll().get(0);

        SubEntryDTO expectSubEntry = submitTimeCardDTO.getEntries().get(0).getSubEntries().get(0);
        EffortDTO expectEffort = expectSubEntry.getEfforts().get(0);

        assertEffort(ActualEffort, expectSubEntry, expectEffort, submitTimeCardDTO.getEmployeeId());

    }

    private void assertEffort(Effort actualEffort, SubEntryDTO expectSubEntry, EffortDTO expectEffort, String employeeId) {
        assertEquals(employeeId, actualEffort.getEmployeeId());
        assertEquals(expectSubEntry.getLocationCode(), actualEffort.getLocationId());
        assertEquals(expectEffort.getNote(), actualEffort.getNote());
        assertEquals(expectSubEntry.getSubProjectId(), actualEffort.getSubProjectId());
        assertEquals(expectEffort.getDate(), actualEffort.getWorkingDay().toString());
        assertEquals(expectEffort.getWorkingHours(), actualEffort.getWorkingHours());
        assertEquals(expectSubEntry.isBillable(), actualEffort.isBillable());
    }

    private SubmitTimecardDTO buildSubmitTimeCardDTO() {
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