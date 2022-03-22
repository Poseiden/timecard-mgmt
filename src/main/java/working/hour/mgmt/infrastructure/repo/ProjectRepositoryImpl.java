package working.hour.mgmt.infrastructure.repo;

import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import working.hour.mgmt.domain.repository.ProjectRepository;
import working.hour.mgmt.infrastructure.dto.VerifyProjectExistResponse;

import java.util.List;
import java.util.Map;

@Repository
public class ProjectRepositoryImpl implements ProjectRepository {
    @Override
    public Map<String, List<String>> verifyProjectsExist(Map<String, List<String>> toBeVerifiedProjectId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "this is url";

        VerifyProjectExistResponse response = restTemplate.getForEntity(url, VerifyProjectExistResponse.class).getBody();

        return response.getNotExistsProjectIds();
    }
}
