package edu.tcu.cs.superfrogscheduler.appearance;

import edu.tcu.cs.superfrogscheduler.user.User;
import edu.tcu.cs.superfrogscheduler.user.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("${api.endpoint.base-url}/appearance")
public class AppearanceController {

    private AppearanceRepository appearanceRepository;

    private UserRepository userRepository;

    @PostMapping
    public String addRequest(String reqFirstName, String reqLastName, String reqPhoneNumber, String reqEmail, AppearanceType appearanceType, String title, String orgName, String address, Double mileage, LocalDate eventDate, LocalTime startTime, LocalTime endTime, String desc, boolean onCampus, String instructions, String expenses, String outsideOrg) {
        Appearance request = new Appearance(
                reqFirstName,
                reqLastName,
                reqPhoneNumber,
                reqEmail,
                appearanceType,
                title,
                address,
                orgName,
                mileage,
                eventDate,
                startTime,
                endTime,
                AppearanceStatus.PENDING,
                desc,
                onCampus,
                instructions,
                expenses,
                outsideOrg,
                null
        );
        appearanceRepository.save(request);
        return "Success";
    }

    @PutMapping("/1/{eventId}")
    public String approveEvent(@PathVariable("eventId") Long eventId){
        Appearance appearanceToApprove = appearanceRepository.getOne(eventId);
        appearanceToApprove.setStatus(AppearanceStatus.APPROVED);
        appearanceRepository.save(appearanceToApprove);
        return "Success";
    }

    @PutMapping("/2/{eventId}")
    public String rejectEvent(@PathVariable("eventId") Long eventId){
        Appearance appearanceToReject = appearanceRepository.getOne(eventId);
        appearanceToReject.setStatus(AppearanceStatus.REJECTED);
        appearanceRepository.save(appearanceToReject);
        return "Success";
    }

    @PutMapping("/3/{eventId}")
    public String assignEvent(@PathVariable("eventId") Long eventId, @PathVariable("frogId") Long frogId){
        Appearance appearanceToAssign = appearanceRepository.getOne(eventId);
        User frogToAssign = userRepository.getOne(frogId);
        appearanceToAssign.setAssignedSuperFrog(frogToAssign);
        appearanceRepository.save(appearanceToAssign);
        return "Success";
    }

    @PutMapping("/4/{eventId}")
    public String unassignEvent(@PathVariable("eventId") Long eventId){
        Appearance appearanceToUnassign = appearanceRepository.getOne(eventId);
        appearanceToUnassign.setAssignedSuperFrog(null);
        appearanceRepository.save(appearanceToUnassign);
        return "Success";
    }

}
