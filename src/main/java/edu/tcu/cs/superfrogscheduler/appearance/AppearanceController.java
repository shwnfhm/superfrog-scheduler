package edu.tcu.cs.superfrogscheduler.appearance;

import edu.tcu.cs.superfrogscheduler.appearance.converter.AppearanceDtoToAppearanceConverter;
import edu.tcu.cs.superfrogscheduler.appearance.converter.AppearanceToAppearanceDtoConverter;
import edu.tcu.cs.superfrogscheduler.appearance.dto.AppearanceDto;
import edu.tcu.cs.superfrogscheduler.email.EmailService;
import edu.tcu.cs.superfrogscheduler.system.Result;
import edu.tcu.cs.superfrogscheduler.system.StatusCode;
import edu.tcu.cs.superfrogscheduler.user.User;
import edu.tcu.cs.superfrogscheduler.user.UserService;
import edu.tcu.cs.superfrogscheduler.user.converter.UserDtoToUserConverter;
import edu.tcu.cs.superfrogscheduler.user.dto.UserDto;
import edu.tcu.cs.superfrogscheduler.appearance.export.ExcelExporter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.endpoint.base-url}/appearances")
public class AppearanceController {

    private AppearanceService appearanceService;

    private UserService userService;

    private EmailService emailService;
    private AppearanceDtoToAppearanceConverter appearanceDtoToAppearanceConverter;

    private AppearanceToAppearanceDtoConverter appearanceToAppearanceDtoConverter;
    private UserDtoToUserConverter userDtoToUserConverter;

    public AppearanceController(AppearanceService appearanceService, EmailService emailService, AppearanceDtoToAppearanceConverter appearanceDtoToAppearanceConverter, AppearanceToAppearanceDtoConverter appearanceToAppearanceDtoConverter, UserDtoToUserConverter userDtoToUserConverter, UserService userService) {
        this.appearanceService = appearanceService;
        this.appearanceDtoToAppearanceConverter = appearanceDtoToAppearanceConverter;
        this.appearanceToAppearanceDtoConverter = appearanceToAppearanceDtoConverter;
        this.userDtoToUserConverter = userDtoToUserConverter;
        this.emailService = emailService;
        this.userService = userService;
    }

    @PostMapping
    public Result addRequest(@Valid @RequestBody AppearanceDto req){
        Appearance newAppearance = this.appearanceDtoToAppearanceConverter.convert(req);
        Appearance savedNewAppearance = this.appearanceService.save(newAppearance);
        AppearanceDto savedNewAppearanceDto = this.appearanceToAppearanceDtoConverter.convert(savedNewAppearance);
        emailService.sendEmail(savedNewAppearance.getReqEmail(),"superfrogschedulercite30363@gmail.com",
                                "SuperFrog Request " + savedNewAppearance.getRequestId().toString() + " Received","Dear Customer," + "\n" + "We are glad to inform you that your request (ID: " + savedNewAppearance.getRequestId().toString() + " has been submitted. \n"
                        + "Your request will be reviewed and a SuperFrog will be assigned if approved\n" +
                        "Thank you!");
        emailService.sendEmail("superfrogschedulercite30363@gmail.com","superfrogschedulercite30363@gmail.com",
                "SuperFrog Request Submitted","Dear Spirit Director," + "\n" + "A customer has submitted a new appearance request \n"
                        );
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedNewAppearanceDto);
    }

    @GetMapping
    public Result findAllAppearances(){
        List<Appearance> foundAppearances = this.appearanceService.findAll();
        // Convert foundArtifacts to a list of artifactDtos
        List<AppearanceDto> appearanceDtos = foundAppearances.stream()
                .map(this.appearanceToAppearanceDtoConverter::convert)
                .collect(Collectors.toList());
        return new Result(true, StatusCode.SUCCESS, "Find All Success", appearanceDtos);
    }

    @GetMapping("/{requestId}")
    public Result findAppearanceById(@PathVariable Long requestId){
        Appearance foundAppearance = this.appearanceService.findById(requestId);
        AppearanceDto appearanceDto = this.appearanceToAppearanceDtoConverter.convert(foundAppearance);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", appearanceDto);
    }

    @PutMapping("/{requestId}")
    public Result updateAppearance(@PathVariable Long requestId, @Valid @RequestBody AppearanceDto appearanceDto){
        Appearance update = this.appearanceDtoToAppearanceConverter.convert(appearanceDto);
        Appearance updatedAppearance = this.appearanceService.update(requestId, update);
        if(updatedAppearance.getAssignedSuperFrog() != null) {
            emailService.sendEmail(updatedAppearance.getAssignedSuperFrog().getEmail(),"superfrogschedulercite30363@gmail.com",
                    "SuperFrog Request " + updatedAppearance.getRequestId().toString() + " Unassigned","Dear Superfrog," + "\n" + "A customer has modified an existing appearance request (ID: " + updatedAppearance.getRequestId().toString() + ". \n"
                    + "You have been unassigned from the event.");
            updatedAppearance = this.userService.unassign(requestId);
        }
        AppearanceDto updatedAppearanceDto = this.appearanceToAppearanceDtoConverter.convert(updatedAppearance);
        emailService.sendEmail(updatedAppearance.getReqEmail(),"superfrogschedulercite30363@gmail.com",
                "SuperFrog Request " + updatedAppearance.getRequestId().toString() + " Modified","Dear Customer," + "\n" + "We are glad to inform you that your request (ID: " + updatedAppearance.getRequestId().toString() + " modification has been submitted. \n"
                        + "Your modified request will be reviewed and a SuperFrog will be assigned if approved\n" +
                        "Thank you!");
        emailService.sendEmail("superfrogschedulercite30363@gmail.com","superfrogschedulercite30363@gmail.com",
                "SuperFrog Request " + updatedAppearance.getRequestId().toString() + " Modified","Dear Spirit Director," + "\n" + "A customer has modified an existing appearance request (ID: " + updatedAppearance.getRequestId().toString() + ". \n"
        );
        return new Result(true, StatusCode.SUCCESS, "Update Success", updatedAppearanceDto);
    }

    @PostMapping("/{requestId}/approval")
    public Result approveRequest(@PathVariable Long requestId){
        Appearance approvedAppearance = this.appearanceService.approve(requestId);
        emailService.sendEmail(approvedAppearance.getReqEmail(), "superfrogschedulercite30363@gmail.com",
                "SuperFrog Request Approved!", "Dear Customer," + "\n" + "We are happy to inform you that your SuperFrog appearance request has been approved! \n"
                        + "We will assign a SuperFrog to your event as soon as possible\n" +
                        "Thank you for your business");
        return new Result(true, StatusCode.SUCCESS, "Approval Successful", approvedAppearance);
    }

    @DeleteMapping("/{requestId}/approval")
    public Result rejectRequest(@PathVariable Long requestId){
        Appearance rejectedAppearance = this.appearanceService.reject(requestId);
        emailService.sendEmail(rejectedAppearance.getReqEmail(), "superfrogschedulercite30363@gmail.com",
                "SuperFrog Request Rejected", "Dear Customer," + "\n" + "We regret to inform you that your SuperFrog appearance request has been rejected. \n"
                        + "We hope you will understand and thank you for your business\n");
        return new Result(true, StatusCode.SUCCESS, "Rejection Successful", rejectedAppearance);
    }

    @GetMapping("/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Appearance> listAppearances = appearanceService.findAll();

        ExcelExporter excelExporter = new ExcelExporter(listAppearances);

        excelExporter.export(response);
    }

    @PostMapping("/{requestId}/complete")
    public Result completeAppearance(@PathVariable Long requestId){
        Appearance completedAppearance = this.appearanceService.complete(requestId);
        this.userService.completeAppearance(requestId);
        AppearanceDto completedAppearanceDto = this.appearanceToAppearanceDtoConverter.convert(completedAppearance);
        emailService.sendEmail("superfrogschedulercite30363@gmail.com", "superfrogschedulercite30363@gmail.com",
                "SuperFrog Appearance Complete", "Dear Spirit Director," + "\n" + "Appearance " + completedAppearance.getRequestId().toString() + " has been completed\n"
                        + "by SuperFrog user " + completedAppearance.getAssignedSuperFrog().getId().toString() + ".\n");
        return new Result(true, StatusCode.SUCCESS, "Completion Successful", completedAppearanceDto);
    }

    @PostMapping("/{requestId}/cancel")
    public Result cancelRequest(@PathVariable Long requestId){
        Appearance cancelledAppearance = this.appearanceService.cancel(requestId);
        if(cancelledAppearance.getAssignedSuperFrog() != null){
            emailService.sendEmail(cancelledAppearance.getAssignedSuperFrog().getEmail(),"superfrogschedulercite30363@gmail.com",
                    "SuperFrog Request " + cancelledAppearance.getRequestId().toString() + " Cancelled","Dear Superfrog," + "\n" + "An existing appearance request (ID: " + cancelledAppearance.getRequestId().toString() + " has been cancelled. \n"
                            + "You have been unassigned from the event.");
            cancelledAppearance = this.userService.unassign(requestId);
            cancelledAppearance = this.appearanceService.cancel(requestId);
        }
        AppearanceDto cancelledAppearanceDto = this.appearanceToAppearanceDtoConverter.convert(cancelledAppearance);
        emailService.sendEmail(cancelledAppearance.getReqEmail(), "superfrogschedulercite30363@gmail.com",
                "SuperFrog Request " + cancelledAppearance.getRequestId().toString() + " Cancelled", "Dear Customer," + "\n" + "Your SuperFrog appearance request has been cancelled. \n"
                        + "We apologize for any inconvenience and thank you for your business\n");
        return new Result(true, StatusCode.SUCCESS, "Rejection Successful", cancelledAppearanceDto);
    }

    @GetMapping("/approvals/open")
    public Result getOpenApprovedAppearances(){
        List<Appearance> openApprovedAppearances = this.appearanceService.getOpenApprovedAppearances();
        List<AppearanceDto> openApprovedAppearancesDto = new ArrayList<>();
        for(int i = 0; i<openApprovedAppearances.size(); i++){
            openApprovedAppearancesDto.add(this.appearanceToAppearanceDtoConverter.convert(openApprovedAppearances.get(i)));
        }
        return new Result(true, StatusCode.SUCCESS, "Get Open Approved Appearances Successful", openApprovedAppearancesDto);
    }

}
