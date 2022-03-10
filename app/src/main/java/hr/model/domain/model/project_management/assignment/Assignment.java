package hr.model.domain.model.project_management.assignment;

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
    private String staffId;
    private String clientProjectId;
}
