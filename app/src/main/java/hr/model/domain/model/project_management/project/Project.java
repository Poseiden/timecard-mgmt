package hr.model.domain.model.project_management.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    private String id;
    private List<String> entryIds;
    private List<SubProject> subProjects;
}
