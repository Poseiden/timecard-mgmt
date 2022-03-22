package working.hour.mgmt.infrastructure.repo;

import org.springframework.stereotype.Repository;
import working.hour.mgmt.domain.repository.ProjectRepository;

import java.util.List;
import java.util.Map;

@Repository
public class ProjectRepositoryImpl implements ProjectRepository {
    @Override
    public Map<String, List<String>> verifyProjectsExist(Map<String, List<String>> toBeVerifiedProjectId) {
        return null;
    }
}
