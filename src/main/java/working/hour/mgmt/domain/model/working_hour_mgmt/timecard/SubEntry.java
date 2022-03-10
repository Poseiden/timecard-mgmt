package working.hour.mgmt.domain.model.working_hour_mgmt.timecard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubEntry {
    private String id;
    private String locationId;
    private boolean billable;
    private String note;
    private List<String> effortIdList;
}
