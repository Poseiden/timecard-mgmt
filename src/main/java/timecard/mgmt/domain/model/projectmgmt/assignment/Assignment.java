package timecard.mgmt.domain.model.projectmgmt.assignment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Assignment {
    private String id;
    private String employeeId;
    private String clientProjectId;
}
