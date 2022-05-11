package timecard.mgmt.infrastructure.proxy;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import timecard.mgmt.domain.service.ProjectService;
import timecard.mgmt.domain.dto.VerifyProjectExistDTO;
import timecard.mgmt.infrastructure.dto.VerifyProjectExistResponse;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class ProjectServiceProxy implements ProjectService {
    private static final String DOMAIN_NAME = "http://localhost:8081";

    @Override
    //todo define a data type for toBeVerifiedProjectId, put it to domain/service
    //todo rename SubProject to Subproject
    public Map<String, Set<String>> verifyProjectsExist(List<VerifyProjectExistDTO> verifyProjectExistDTOs) {
        RestTemplate restTemplate = new RestTemplate();
        String url = DOMAIN_NAME + "/projects/invalid-project-ids?projects={projects}";

        Map<String, String> uriVariables = Maps.newHashMap();
        uriVariables.put("projects", JSONObject.toJSONString(verifyProjectExistDTOs));
        return restTemplate.getForEntity(url, VerifyProjectExistResponse.class, uriVariables).getBody()
                .getNotExistsProjectIds();
    }
}
