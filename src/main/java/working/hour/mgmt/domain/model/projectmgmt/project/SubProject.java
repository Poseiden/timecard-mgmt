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
public class SubProject {
    private String id;
    private List<String> effortIds;
    private List<String> subEntries;
}
