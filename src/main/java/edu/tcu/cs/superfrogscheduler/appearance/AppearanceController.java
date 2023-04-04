package edu.tcu.cs.superfrogscheduler.appearance;

import edu.tcu.cs.superfrogscheduler.appearance.converter.AppearanceDtoToAppearanceConverter;
import edu.tcu.cs.superfrogscheduler.appearance.converter.AppearanceToAppearanceDtoConverter;
import edu.tcu.cs.superfrogscheduler.appearance.dto.AppearanceDto;
import edu.tcu.cs.superfrogscheduler.system.Result;
import edu.tcu.cs.superfrogscheduler.system.StatusCode;
import edu.tcu.cs.superfrogscheduler.user.User;
import edu.tcu.cs.superfrogscheduler.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("${api.endpoint.base-url}/appearances")
public class AppearanceController {

    private AppearanceService appearanceService;

    private AppearanceDtoToAppearanceConverter appearanceDtoToAppearanceConverter;

    private AppearanceToAppearanceDtoConverter appearanceToAppearanceDtoConverter;

    public AppearanceController(AppearanceService appearanceService, AppearanceDtoToAppearanceConverter appearanceDtoToAppearanceConverter, AppearanceToAppearanceDtoConverter appearanceToAppearanceDtoConverter) {
        this.appearanceService = appearanceService;
        this.appearanceDtoToAppearanceConverter = appearanceDtoToAppearanceConverter;
        this.appearanceToAppearanceDtoConverter = appearanceToAppearanceDtoConverter;
    }

    @PostMapping
    public Result addRequest(@Valid @RequestBody AppearanceDto req){
        Appearance newAppearance = this.appearanceDtoToAppearanceConverter.convert(req);
        System.out.println(newAppearance.getAppearanceType());
        System.out.println(newAppearance.getStatus());
        System.out.println(newAppearance.getEventDate());
        System.out.println(newAppearance.getStartTime());
        System.out.println(newAppearance.getEndTime());
        System.out.println(newAppearance.getAssignedSuperFrog());
        Appearance savedNewAppearance = this.appearanceService.save(newAppearance);
        AppearanceDto savedNewAppearanceDto = this.appearanceToAppearanceDtoConverter.convert(savedNewAppearance);
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedNewAppearanceDto);
    }

    @PutMapping("/1/{eventId}")
    public String approveEvent(@PathVariable("eventId") Long eventId){
        /*
        Appearance appearanceToApprove = appearanceRepository.getOne(eventId);
        appearanceToApprove.setStatus(AppearanceStatus.APPROVED);
        appearanceRepository.save(appearanceToApprove);

         */
        return "Success";
    }

    @PutMapping("/2/{eventId}")
    public String rejectEvent(@PathVariable("eventId") Long eventId){
        /*
        Appearance appearanceToReject = appearanceRepository.getOne(eventId);
        appearanceToReject.setStatus(AppearanceStatus.REJECTED);
        appearanceRepository.save(appearanceToReject);

         */
        return "Success";
    }

    @PutMapping("/3/{eventId}")
    public String assignEvent(@PathVariable("eventId") Long eventId, @PathVariable("frogId") Long frogId){
        /*
        Appearance appearanceToAssign = appearanceRepository.getOne(eventId);
        User frogToAssign = userRepository.getOne(frogId);
        appearanceToAssign.setAssignedSuperFrog(frogToAssign);
        appearanceRepository.save(appearanceToAssign);

         */
        return "Success";
    }

    @PutMapping("/4/{eventId}")
    public String unassignEvent(@PathVariable("eventId") Long eventId){
        /*
        Appearance appearanceToUnassign = appearanceRepository.getOne(eventId);
        appearanceToUnassign.setAssignedSuperFrog(null);
        appearanceRepository.save(appearanceToUnassign);

         */
        return "Success";
    }

}
