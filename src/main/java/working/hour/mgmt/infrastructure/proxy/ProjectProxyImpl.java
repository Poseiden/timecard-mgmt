package working.hour.mgmt.infrastructure.proxy;

import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import working.hour.mgmt.domain.service.ProjectProxy;
import working.hour.mgmt.infrastructure.dto.VerifyProjectExistResponse;

import java.util.List;
import java.util.Map;

@Repository
public class ProjectProxyImpl implements ProjectProxy {
    @Override
    public Map<String, List<String>> verifyProjectsExist(Map<String, List<String>> toBeVerifiedProjectId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "/this is url";

        return restTemplate.getForEntity(url, VerifyProjectExistResponse.class).getBody().getNotExistsProjectIdList();
    }
}
