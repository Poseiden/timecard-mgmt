package working.hour.mgmt.infrastructure.repo;

import org.springframework.stereotype.Repository;
import working.hour.mgmt.domain.model.working_hour_mgmt.effort.Effort;
import working.hour.mgmt.domain.repository.EffortRepository;
import working.hour.mgmt.infrastructure.repo.hibernate.EffortDBRepo;

import java.util.List;

@Repository
public class EffortRepositoryImpl  implements EffortRepository {
    private final EffortDBRepo effortDBRepo;

    public EffortRepositoryImpl(EffortDBRepo effortDBRepo) {
        this.effortDBRepo = effortDBRepo;
    }

    @Override
    public void saveAll(List<Effort> efforts) {
        this.effortDBRepo.saveAll(efforts);
    }
}
