package timecard.mgmt.domain.service;

import java.util.Map;
import java.util.Set;

public interface ProjectService {
    Map<String, Set<String>> verifyProjectsExist(Map<String, Set<String>> toBeVerifiedProjectId);
}
