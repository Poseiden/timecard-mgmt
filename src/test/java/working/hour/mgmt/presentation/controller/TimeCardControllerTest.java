package working.hour.mgmt.presentation.controller;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class TimeCardControllerTest extends BaseTest {

    @Test
    public void should_return_success_when_submit_timecard() throws Exception {
        //todo preparing data

        MockHttpServletResponse response = this.mockMvc.perform(post("/effortentries/submit"))
                .andReturn().getResponse();


        //todo assertion


    }

}