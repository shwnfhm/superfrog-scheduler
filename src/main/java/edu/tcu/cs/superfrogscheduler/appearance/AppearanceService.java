package edu.tcu.cs.superfrogscheduler.appearance;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AppearanceService {

    private final AppearanceRepository appearanceRepository;


    public AppearanceService(AppearanceRepository appearanceRepository) {
        this.appearanceRepository = appearanceRepository;
    }


}
