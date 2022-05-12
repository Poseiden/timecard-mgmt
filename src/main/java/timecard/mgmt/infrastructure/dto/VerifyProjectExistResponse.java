package timecard.mgmt.infrastructure.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
//todo to delete this class
public class VerifyProjectExistResponse {
    @NonNull
    private Map<String, Set<String>> notExistsProjectIds;
}
