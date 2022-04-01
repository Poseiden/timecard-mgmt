package working.hour.mgmt.infrastructure.dto;

import lombok.Data;
import lombok.NonNull;

import java.util.Map;
import java.util.Set;

@Data
public class VerifyProjectExistResponse {
    @NonNull
    private Map<String, Set<String>> notExistsProjectIds;
}
