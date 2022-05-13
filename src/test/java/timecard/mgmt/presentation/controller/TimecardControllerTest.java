package timecard.mgmt.presentation.controller;

import com.alibaba.fastjson.JSON;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import timecard.mgmt.application.dto.EffortDTO;
import timecard.mgmt.application.dto.EntryDTO;
import timecard.mgmt.application.dto.SubEntryDTO;
import timecard.mgmt.application.dto.SubmitTimecardDTO;
import timecard.mgmt.base.APIBaseTest;
import timecard.mgmt.domain.service.VerifyProjectExistDTO;
import timecard.mgmt.domain.model.effortmgmt.effort.Effort;
import timecard.mgmt.domain.service.ProjectService;
import timecard.mgmt.infrastructure.persistence.hibernate.EffortRepoJPA;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static timecard.mgmt.domain.common.exception.ErrorKey.PROJECTS_OR_SUBPROJECTS_NOT_EXIST;

public class TimecardControllerTest extends APIBaseTest {
    @Autowired
    private EffortRepoJPA effortRepoJPA;

    @MockBean
    private ProjectService projectService;

    @Test
    public void should_return_success_when_submit_timecard() throws Exception {
        //given
        when(projectService.verifyProjectsExist(any())).thenReturn(Lists.newArrayList());

        //when
        SubmitTimecardDTO submitTimeCardDTO = buildSubmitTimeCardDTO();
        this.mockMvc.perform(post("/timecards/submit")
                        .contentType(APPLICATION_JSON)
                        .content(JSON.toJSONString(submitTimeCardDTO)))
                .andExpect(status().isOk());

        //then
        Effort ActualEffort = effortRepoJPA.findAll().get(0);

        EntryDTO expectEntry = submitTimeCardDTO.getEntries().get(0);
        SubEntryDTO expectSubEntry = expectEntry.getSubEntries().get(0);
        EffortDTO expectEffort = expectSubEntry.getEfforts().get(0);

        assertEffort(ActualEffort, expectSubEntry, expectEffort, submitTimeCardDTO.getEmployeeId(), expectEntry.getProjectId());
    }

    @Test
    public void should_return_project_not_exists_when_project_id_in_timecard_not_exists() throws Exception {
        //given
        when(projectService.verifyProjectsExist(any())).
                thenReturn(Lists.newArrayList(new VerifyProjectExistDTO("some not exist project ids", Sets.newHashSet())));

        //when
        SubmitTimecardDTO submitTimeCardDTO = buildSubmitTimeCardDTO();
        this.mockMvc.perform(post("/timecards/submit")
                .contentType(APPLICATION_JSON)
                .content(JSON.toJSONString(submitTimeCardDTO)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.error", is(PROJECTS_OR_SUBPROJECTS_NOT_EXIST.toString())));

        //then
        assertTrue(effortRepoJPA.findAll().isEmpty());
    }

    private void assertEffort(Effort actualEffort, SubEntryDTO expectSubEntry, EffortDTO expectEffort,
                              String employeeId, String projectId) {
        assertEquals(employeeId, actualEffort.getEmployeeId());
        assertEquals(expectSubEntry.getLocationCode(), actualEffort.getLocationId());
        assertEquals(expectEffort.getNote(), actualEffort.getNote());
        assertEquals(expectSubEntry.getSubProjectId(), actualEffort.getSubProjectId());
        assertEquals(expectEffort.getDate(), actualEffort.getWorkingDay().toString());
        assertEquals(expectEffort.getWorkingHours(), actualEffort.getWorkingHours());
        assertEquals(expectSubEntry.isBillable(), actualEffort.isBillable());
        assertEquals(projectId, actualEffort.getProjectId());
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