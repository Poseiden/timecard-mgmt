package timecard.mgmt.presentation.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import timecard.mgmt.application.dto.SubmitTimecardDTO;
import timecard.mgmt.application.service.TimecardApplicationService;

import static org.springframework.http.HttpStatus.OK;

@RestController
public class TimecardController {

    private final TimecardApplicationService timecardApplicationService;

    public TimecardController(TimecardApplicationService timecardApplicationService) {
        this.timecardApplicationService = timecardApplicationService;
    }

    @PostMapping("/timecards/submit")
    @ResponseStatus(OK)
    public void submitTimecard(@RequestBody SubmitTimecardDTO submitTimecardDto) {
        timecardApplicationService.submit(submitTimecardDto);
    }

}
