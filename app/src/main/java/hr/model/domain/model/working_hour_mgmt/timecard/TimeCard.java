package hr.model.domain.model.working_hour_mgmt.timecard;

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
