package timecard.mgmt.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@Data
@NoArgsConstructor
//todo 放至 service 包中
public class VerifyProjectExistDTO {
    private String projectId;
    private Set<String> subprojectIds;

}
