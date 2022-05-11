package timecard.mgmt.domain.service;

import timecard.mgmt.domain.dto.VerifyProjectExistDTO;

import java.util.List;

public interface ProjectService {
    List<VerifyProjectExistDTO> verifyProjectsExist(List<VerifyProjectExistDTO> verifyProjectExistDTOs);
}
