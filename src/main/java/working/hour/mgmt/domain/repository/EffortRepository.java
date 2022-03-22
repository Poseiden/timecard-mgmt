package working.hour.mgmt.domain.repository;

import working.hour.mgmt.domain.model.working_hour_mgmt.effort.Effort;

import java.util.List;

public interface EffortRepository {
    void saveAll(List<Effort> efforts);
}
