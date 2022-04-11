package working.hour.mgmt.domain.model.effortmgmt.timecard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Period {
    private LocalDate start;
    private LocalDate end;
}
