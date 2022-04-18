package timecard.mgmt.infrastructure.proxy;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import timecard.mgmt.domain.service.ProjectService;
import timecard.mgmt.infrastructure.dto.VerifyProjectExistResponse;

import java.util.Map;
import java.util.Set;

@Component
public class ProjectServiceProxy implements ProjectService {
    @Override
    public Map<String, Set<String>> verifyProjectsExist(Map<String, Set<String>> toBeVerifiedProjectId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081/projects/invalid-project-ids?projects={projects}";

        Map<String, String> uriVariables = Maps.newHashMap();
        uriVariables.put("projects", JSONObject.toJSONString(toBeVerifiedProjectId));
        return restTemplate.getForEntity(url, VerifyProjectExistResponse.class, uriVariables).getBody()
                .getNotExistsProjectIds();
    }
}
