package working.hour.mgmt.domain.service;

import java.util.List;
import java.util.Map;

public interface ProjectProxy {
    Map<String, List<String>> verifyProjectsExist(Map<String, List<String>> toBeVerifiedProjectId);
}
