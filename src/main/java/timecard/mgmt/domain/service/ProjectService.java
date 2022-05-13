package timecard.mgmt.domain.service;

import java.util.List;

public interface ProjectService {
    List<VerifyProjectExistDTO> verifyProjectsExist(List<VerifyProjectExistDTO> verifyProjectExistDTOs);
}
