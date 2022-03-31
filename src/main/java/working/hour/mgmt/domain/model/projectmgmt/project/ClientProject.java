package working.hour.mgmt.domain.model.projectmgmt.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientProject extends Project {
    private String id;
    private List<String> assignmentIdList;
}
