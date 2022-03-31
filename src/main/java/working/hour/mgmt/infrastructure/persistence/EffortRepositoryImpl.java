package working.hour.mgmt.infrastructure.persistence;

import org.springframework.stereotype.Repository;
import working.hour.mgmt.domain.model.effortmgmt.effort.Effort;
import working.hour.mgmt.domain.model.effortmgmt.effort.EffortRepository;
import working.hour.mgmt.infrastructure.persistence.hibernate.EffortRepoJPA;

import java.util.List;

@Repository
public class EffortRepositoryImpl  implements EffortRepository {
    private final EffortRepoJPA effortRepoJPA;

    public EffortRepositoryImpl(EffortRepoJPA effortRepoJPA) {
        this.effortRepoJPA = effortRepoJPA;
    }

    @Override
    public void saveAll(List<Effort> efforts) {
        this.effortRepoJPA.saveAll(efforts);
    }
}
