package working.hour.mgmt.domain.model.project_mgmt.assignment;

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
