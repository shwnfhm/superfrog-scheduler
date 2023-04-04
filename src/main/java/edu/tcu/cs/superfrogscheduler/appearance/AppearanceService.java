package edu.tcu.cs.superfrogscheduler.appearance;

import edu.tcu.cs.superfrogscheduler.system.IdWorker;
import org.springframework.stereotype.Service;

@Service
public class AppearanceService {

    private final AppearanceRepository appearanceRepository;

    private final IdWorker idWorker;

    public AppearanceService(AppearanceRepository appearanceRepository, IdWorker idWorker) {
        this.appearanceRepository = appearanceRepository;
        this.idWorker = idWorker;
    }
}
