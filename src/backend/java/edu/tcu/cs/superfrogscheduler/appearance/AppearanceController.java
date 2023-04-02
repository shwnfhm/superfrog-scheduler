package backend.java.edu.tcu.cs.superfrogscheduler.appearance;

import backend.java.edu.tcu.cs.superfrogscheduler.user.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppearanceController {

    private AppearanceRepository appearanceRepository;

    private UserRepository userRepository;

    @PostMapping("/appearance")
    public String addRequest(Appearance request){
        return "success";
    }
}
