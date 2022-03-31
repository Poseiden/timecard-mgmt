package working.hour.mgmt.presentation.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import working.hour.mgmt.application.TimeCardApplicationService;
import working.hour.mgmt.application.dto.SubmitTimeCardDTO;

import static org.springframework.http.HttpStatus.OK;

@RestController
//todo rename TimeCard to timecard word
public class TimeCardController {

    private final TimeCardApplicationService timeCardApplicationService;

    public TimeCardController(TimeCardApplicationService timeCardApplicationService) {
        this.timeCardApplicationService = timeCardApplicationService;
    }

    @PostMapping("/timecards/submit")
    @ResponseStatus(OK)
    public void submitTimeCard(@RequestBody SubmitTimeCardDTO submitTimeCardDto) {
        timeCardApplicationService.submit(submitTimeCardDto);
    }

}
