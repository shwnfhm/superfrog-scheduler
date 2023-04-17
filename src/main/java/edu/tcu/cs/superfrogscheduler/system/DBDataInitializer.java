package edu.tcu.cs.superfrogscheduler.system;

import edu.tcu.cs.superfrogscheduler.appearance.AppearanceRepository;
import edu.tcu.cs.superfrogscheduler.user.PaymentPreference;
import edu.tcu.cs.superfrogscheduler.user.User;
import edu.tcu.cs.superfrogscheduler.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBDataInitializer implements CommandLineRunner {

    private final UserService userService;

    private final AppearanceRepository appearanceRepository;

    public DBDataInitializer(UserService userService, AppearanceRepository appearanceRepository) {
        this.userService = userService;
        this.appearanceRepository = appearanceRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User spiritDirector = new User();
        spiritDirector.setEmail("superfrogschedulercite30363@gmail.com");
        spiritDirector.setRoles("SPIRITDIRECTOR USER");
        spiritDirector.setPassword("spiritdirector");
        spiritDirector.setActive(true);
        spiritDirector.setInternational(false);
        spiritDirector.setFirstName("Spirit");
        spiritDirector.setLastName("Director");
        spiritDirector.setPaymentPreference(PaymentPreference.MAILCHECK);
        spiritDirector.setPhoneNumber("4697682085");
        this.userService.save(spiritDirector);
    }
}
