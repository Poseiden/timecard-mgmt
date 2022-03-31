package working.hour.mgmt.infrastructure.dto;

import lombok.Data;
import lombok.NonNull;

import java.util.List;
import java.util.Map;

@Data
public class VerifyProjectExistResponse {
    @NonNull
    private Map<String, List<String>> notExistsProjectIds;
}
