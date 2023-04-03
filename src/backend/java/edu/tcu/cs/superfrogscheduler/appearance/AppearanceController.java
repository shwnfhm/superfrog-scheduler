package backend.java.edu.tcu.cs.superfrogscheduler.appearance;

import backend.java.edu.tcu.cs.superfrogscheduler.user.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appearance")
public class AppearanceController {

    private AppearanceRepository appearanceRepository;

    private UserRepository userRepository;

    @PostMapping
    public String addRequest(Appearance requestedAppearance) {
        Appearance request = new Appearance(

        );
        return "Success";
    }

    @PutMapping("/{eventId}")
    public String approveEvent(@PathVariable("eventId") Long eventId){
        Appearance appearanceToApprove = appearanceRepository.getOne(eventId);
        appearanceToApprove.setStatus(AppearanceStatus.APPROVED);
        appearanceRepository.save(appearanceToApprove);
        return "Success";
    }

    @PutMapping("/{eventId}")
    public String rejectEvent(@PathVariable("eventId") Long eventId){
        Appearance appearanceToReject = appearanceRepository.getOne(eventId);
        appearanceToReject.setStatus(AppearanceStatus.REJECTED);
        appearanceRepository.save(appearanceToReject);
        return "Success";
    }

}
