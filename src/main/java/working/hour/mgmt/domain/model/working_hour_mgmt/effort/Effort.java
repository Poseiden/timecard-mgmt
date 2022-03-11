package working.hour.mgmt.domain.model.working_hour_mgmt.effort;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Effort {
    private String id;
    private String employeeId;
    private Calendar workingDay;
    private int workingHours;
    private String locationId;
    private boolean billable;
    private String note;

    private String subEntryId;
    private String subProjectId;

}
