package timecard.mgmt.presentation.controller;

import com.alibaba.fastjson.JSON;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import timecard.mgmt.application.dto.EffortDTO;
import timecard.mgmt.application.dto.EntryDTO;
import timecard.mgmt.application.dto.SubEntryDTO;
import timecard.mgmt.application.dto.SubmitTimecardDTO;
import timecard.mgmt.base.APIBaseTest;
import timecard.mgmt.domain.dto.VerifyProjectExistDTO;
import timecard.mgmt.infrastructure.proxy.ProjectServiceProxy;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static timecard.mgmt.domain.common.exception.ErrorKey.PROJECTS_OR_SUBPROJECTS_NOT_EXIST;

public class TimecardControllerTest extends APIBaseTest {
    @MockBean
    private ProjectServiceProxy projectService;

    @Test
    public void should_return_success_when_submit_timecard() throws Exception {
        //given
        when(projectService.verifyProjectsExist(any())).thenReturn(Lists.newArrayList());

        //when and then
        SubmitTimecardDTO submitTimeCardDTO = buildSubmitTimeCardDTO();
        this.mockMvc.perform(post("/timecards/submit")
                        .contentType(APPLICATION_JSON)
                        .content(JSON.toJSONString(submitTimeCardDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void should_return_project_not_exists_when_project_id_in_timecard_not_exists() throws Exception {
        //given
        when(projectService.verifyProjectsExist(any())).
                thenReturn(Lists.newArrayList(new VerifyProjectExistDTO("some not exist project ids", Sets.newHashSet())));

        //when and then
        SubmitTimecardDTO submitTimeCardDTO = buildSubmitTimeCardDTO();
        this.mockMvc.perform(post("/timecards/submit")
                .contentType(APPLICATION_JSON)
                .content(JSON.toJSONString(submitTimeCardDTO)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.error", is(PROJECTS_OR_SUBPROJECTS_NOT_EXIST.toString())));
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