package working.hour.mgmt.infrastructure.repo.hibernate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import working.hour.mgmt.domain.model.working_hour_mgmt.effort.Effort;

@Repository
public interface EffortDBRepo extends JpaRepository<Effort, String> {
}
