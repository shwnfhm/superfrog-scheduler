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

    public Appearance save(Appearance newAppearance){
        return this.appearanceRepository.save(newAppearance);
    }

}
