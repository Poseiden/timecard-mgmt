package timecard.mgmt.domain.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class VerifyProjectExistDTO {
    private String projectId;
    private Set<String> subprojectIds;

}
