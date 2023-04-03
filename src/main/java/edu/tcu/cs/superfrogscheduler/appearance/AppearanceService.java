package edu.tcu.cs.superfrogscheduler.appearance;

import org.springframework.stereotype.Service;

@Service
public class AppearanceService {

    private final AppearanceRepository appearanceRepository;

    public AppearanceService(AppearanceRepository appearanceRepository) {
        this.appearanceRepository = appearanceRepository;
    }
}
