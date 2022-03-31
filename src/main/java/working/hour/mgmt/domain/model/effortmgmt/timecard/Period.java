package working.hour.mgmt.domain.model.effortmgmt.timecard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Period {
    private Calendar start;
    private Calendar end;
}
