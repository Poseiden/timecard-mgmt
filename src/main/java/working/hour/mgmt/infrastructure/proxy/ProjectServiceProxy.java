package working.hour.mgmt.infrastructure.proxy;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import working.hour.mgmt.domain.service.ProjectService;
import working.hour.mgmt.infrastructure.dto.VerifyProjectExistResponse;

import java.util.List;
import java.util.Map;

@Component
public class ProjectServiceProxy implements ProjectService {
    @Override
    public Map<String, List<String>> verifyProjectsExist(Map<String, List<String>> toBeVerifiedProjectId) {
        RestTemplate restTemplate = new RestTemplate();
        //todo to change actual url
        String url = "/this is url";

        return restTemplate.getForEntity(url, VerifyProjectExistResponse.class).getBody().getNotExistsProjectIds();
    }
}
