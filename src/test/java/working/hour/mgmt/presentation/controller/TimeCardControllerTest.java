package working.hour.mgmt.presentation.controller;

import com.alibaba.fastjson.JSON;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import working.hour.mgmt.application.dto.EffortDTO;
import working.hour.mgmt.application.dto.EntryDTO;
import working.hour.mgmt.application.dto.SubEntryDTO;
import working.hour.mgmt.application.dto.SubmitTimeCardDTO;
import working.hour.mgmt.domain.model.working_hour_mgmt.effort.Effort;
import working.hour.mgmt.domain.repository.ProjectRepository;
import working.hour.mgmt.infrastructure.repo.hibernate.EffortDBRepo;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TimeCardControllerTest extends BaseTest {
    @Autowired
    private EffortDBRepo effortDBRepo;

    @MockBean
    private ProjectRepository projectRepository;

    @Test
    public void should_return_success_when_submit_time_card() throws Exception {
        //given
        when(projectRepository.verifyProjectsExist(any())).thenReturn(null);

        //when
        SubmitTimeCardDTO submitTimeCardDTO = buildSubmitTimeCardDTO();
        MockHttpServletResponse response = this.mockMvc
                .perform(post("/effortentries/submit")
                        .contentType("application/json")
                        .content(JSON.toJSONString(submitTimeCardDTO)))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        //then
        Effort effort = effortDBRepo.findAll().get(0);

        SubEntryDTO subEntryDTO = submitTimeCardDTO.getEntryDTOList().get(0).getSubEntryDTOList().get(0);
        EffortDTO effortDTO = subEntryDTO.getEffortDTOList().get(0);

        assertEquals(effort.getEmployeeId(), submitTimeCardDTO.getEmployeeId());
        assertEquals(effort.getLocationId(), subEntryDTO.getLocationCode());
        assertEquals(effort.getNote(), effortDTO.getNote());
        assertEquals(effort.getSubProjectId(), subEntryDTO.getSubProjectId());
        assertEquals(effort.getWorkingDay().toString(), effortDTO.getDate());
        assertEquals(effort.getWorkingHours(), effortDTO.getWorkingHours());
        assertEquals(effort.isBillable(), subEntryDTO.isBillable());

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

        subEntryDTO.setEffortDTOList(Lists.newArrayList(effortDTO));
        entryDTO.setSubEntryDTOList(Lists.newArrayList(subEntryDTO));
        submitTimeCardDTO.setEntryDTOList(Lists.newArrayList(entryDTO));

        return submitTimeCardDTO;
    }

}