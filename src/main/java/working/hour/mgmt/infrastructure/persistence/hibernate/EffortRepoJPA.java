package working.hour.mgmt.infrastructure.persistence.hibernate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import working.hour.mgmt.domain.model.effortmgmt.effort.Effort;

@Repository
public interface EffortRepoJPA extends JpaRepository<Effort, String> {
}
