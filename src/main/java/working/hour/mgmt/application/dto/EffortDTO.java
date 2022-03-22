package working.hour.mgmt.application.dto;

import lombok.Data;

import java.util.Calendar;

@Data
public class EffortDTO {
    private Calendar date;
    private int workingHours;
    private String note;
}
