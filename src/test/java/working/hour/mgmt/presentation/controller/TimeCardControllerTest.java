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
import working.hour.mgmt.application.dto.SubmitTimeCardDTO;
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

public class TimeCardControllerTest extends BaseTest {
    @Autowired
    //todo rename class name
    private EffortRepoJPA effortDBRepo;

    @MockBean
    private ProjectService projectService;

    @Test
    public void should_return_success_when_submit_time_card() throws Exception {
        //given
        when(projectService.verifyProjectsExist(any())).thenReturn(Maps.newHashMap());

        //when
        SubmitTimeCardDTO submitTimeCardDTO = buildSubmitTimeCardDTO();
        this.mockMvc.perform(post("/timecards/submit")
                        .contentType("application/json")
                        .content(JSON.toJSONString(submitTimeCardDTO)))
                .andExpect(status().isOk())
                .andDo(print());

        //then
        Effort effortInDB = effortDBRepo.findAll().get(0);

        //todo rename to expect...
        SubEntryDTO subEntryDTO = submitTimeCardDTO.getEntries().get(0).getSubEntries().get(0);
        EffortDTO effortDTO = subEntryDTO.getEfforts().get(0);

        //todo 交互expect与actual的顺序
        //todo to extract assert method
        assertEquals(effortInDB.getEmployeeId(), submitTimeCardDTO.getEmployeeId());
        assertEquals(effortInDB.getLocationId(), subEntryDTO.getLocationCode());
        assertEquals(effortInDB.getNote(), effortDTO.getNote());
        assertEquals(effortInDB.getSubProjectId(), subEntryDTO.getSubProjectId());
        assertEquals(effortInDB.getWorkingDay().toString(), effortDTO.getDate());
        assertEquals(effortInDB.getWorkingHours(), effortDTO.getWorkingHours());
        assertEquals(effortInDB.isBillable(), subEntryDTO.isBillable());

    }

    private SubmitTimeCardDTO buildSubmitTimeCardDTO() {
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