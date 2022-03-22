package working.hour.mgmt.domain.repository;

import java.util.List;
import java.util.Map;

public interface ProjectRepository {
    Map<String, List<String>> verifyProjectsExist(Map<String, List<String>> toBeVerifiedProjectId);
}
