package timecard.mgmt.domain.model.effortmgmt.timecard;

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
    private String employeeId;
    private Period period;
    private int totalHours;
    private List<Entry> entries;
}
