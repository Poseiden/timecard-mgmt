package timecard.mgmt.infrastructure.proxy;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import timecard.mgmt.domain.service.VerifyProjectExistDTO;
import timecard.mgmt.domain.service.ProjectService;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpMethod.GET;

@Component
public class ProjectServiceProxy implements ProjectService {
    private static final String DOMAIN_NAME = "http://localhost:8081";
    public static final String INVALID_PROJECT_URI = "/projects/invalid-project-ids";

    @Override
    public List<VerifyProjectExistDTO> verifyProjectsExist(List<VerifyProjectExistDTO> verifyProjectExistDTOs) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> uriVariables = Maps.newHashMap();
        uriVariables.put("projects", JSONObject.toJSONString(verifyProjectExistDTOs));
        return restTemplate.exchange(String.format("%s%s%s", DOMAIN_NAME, INVALID_PROJECT_URI,  "?projects={projects}"),
                GET, null, new ParameterizedTypeReference<List<VerifyProjectExistDTO>>() {
        }, uriVariables).getBody();
    }
}
