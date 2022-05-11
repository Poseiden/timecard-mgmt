package timecard.mgmt.domain.service;

import timecard.mgmt.domain.dto.VerifyProjectExistDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ProjectService {
    Map<String, Set<String>> verifyProjectsExist(List<VerifyProjectExistDTO> verifyProjectExistDTOs);
}
