package timecard.mgmt.application.service;

import org.springframework.stereotype.Service;
import timecard.mgmt.application.dto.SubmitTimecardDTO;
import timecard.mgmt.domain.service.TimecardService;

@Service
public class TimecardApplicationService {
    private TimecardService timecardService;

    public TimecardApplicationService(TimecardService timecardService) {
        this.timecardService = timecardService;
    }

    public void submit(SubmitTimecardDTO submitTimecardDto) {
        this.timecardService.saveTimecard(submitTimecardDto.getEntries(), submitTimecardDto.getEmployeeId());
    }
}