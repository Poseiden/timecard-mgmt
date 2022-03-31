package working.hour.mgmt.domain.service;

import org.springframework.stereotype.Service;
import working.hour.mgmt.domain.model.effortmgmt.effort.Effort;
import working.hour.mgmt.domain.model.effortmgmt.effort.EffortRepository;

import java.util.List;

@Service
public class TimeCardService {
    private final EffortRepository effortRepository;

    public TimeCardService(EffortRepository effortRepository) {
        this.effortRepository = effortRepository;
    }

    public void saveAll(List<Effort> efforts) {
        this.effortRepository.saveAll(efforts);
    }
}
