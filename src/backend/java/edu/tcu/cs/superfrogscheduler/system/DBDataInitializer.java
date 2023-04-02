package backend.java.edu.tcu.cs.superfrogscheduler.system;

import backend.java.edu.tcu.cs.superfrogscheduler.appearance.AppearanceRepository;
import backend.java.edu.tcu.cs.superfrogscheduler.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBDataInitializer implements CommandLineRunner {

    private UserRepository userRepository;

    private AppearanceRepository appearanceRepository;

    @Override
    public void run(String... args) throws Exception {

    }
}
