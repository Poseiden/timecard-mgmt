package working.hour.mgmt.domain.model.staff_mgmt.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private String id;
    private List<String> assignmentIdList;
    private List<String> effortIdList;
    private List<String> timeCardIdList;
}
