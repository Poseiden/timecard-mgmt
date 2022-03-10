package hr.model.domain.model.effort_management.timecard;

import hr.model.domain.model.staff_management.staff.Staff;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeCard {
    private String id;
    private String staffId;
    private Period period;
    private int totalHours;
    private List<Entry> entries;
}
