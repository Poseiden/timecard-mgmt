package timecard.mgmt.infrastructure.persistence;

import org.springframework.stereotype.Repository;
import timecard.mgmt.domain.model.effortmgmt.effort.Effort;
import timecard.mgmt.domain.model.effortmgmt.effort.EffortRepository;
import timecard.mgmt.infrastructure.persistence.hibernate.EffortRepoJPA;

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
