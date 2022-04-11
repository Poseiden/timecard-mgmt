package timecard.mgmt.domain.service;

import org.springframework.stereotype.Service;
import timecard.mgmt.domain.model.effortmgmt.effort.Effort;
import timecard.mgmt.domain.model.effortmgmt.effort.EffortRepository;

import java.util.List;

@Service
public class TimecardService {
    private final EffortRepository effortRepository;

    public TimecardService(EffortRepository effortRepository) {
        this.effortRepository = effortRepository;
    }

    public void saveAll(List<Effort> efforts) {
        this.effortRepository.saveAll(efforts);
    }
}
