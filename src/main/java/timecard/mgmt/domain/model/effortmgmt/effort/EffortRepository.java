package timecard.mgmt.domain.model.effortmgmt.effort;

import java.util.List;

public interface EffortRepository {
    void saveAll(List<Effort> efforts);
}
