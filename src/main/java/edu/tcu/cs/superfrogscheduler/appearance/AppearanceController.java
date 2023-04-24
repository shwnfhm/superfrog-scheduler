package edu.tcu.cs.superfrogscheduler.appearance;

import edu.tcu.cs.superfrogscheduler.appearance.converter.AppearanceDtoToAppearanceConverter;
import edu.tcu.cs.superfrogscheduler.appearance.converter.AppearanceToAppearanceDtoConverter;
import edu.tcu.cs.superfrogscheduler.appearance.dto.AppearanceDto;
import edu.tcu.cs.superfrogscheduler.email.EmailService;
import edu.tcu.cs.superfrogscheduler.system.Result;
import edu.tcu.cs.superfrogscheduler.system.StatusCode;
import edu.tcu.cs.superfrogscheduler.user.User;
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

    private EmailService emailService;
    private AppearanceDtoToAppearanceConverter appearanceDtoToAppearanceConverter;

    private AppearanceToAppearanceDtoConverter appearanceToAppearanceDtoConverter;
    private UserDtoToUserConverter userDtoToUserConverter;

    public AppearanceController(AppearanceService appearanceService, EmailService emailService, AppearanceDtoToAppearanceConverter appearanceDtoToAppearanceConverter, AppearanceToAppearanceDtoConverter appearanceToAppearanceDtoConverter, UserDtoToUserConverter userDtoToUserConverter) {
        this.appearanceService = appearanceService;
        this.appearanceDtoToAppearanceConverter = appearanceDtoToAppearanceConverter;
        this.appearanceToAppearanceDtoConverter = appearanceToAppearanceDtoConverter;
        this.userDtoToUserConverter = userDtoToUserConverter;
        this.emailService = emailService;
    }

    @PostMapping
    public Result addRequest(@Valid @RequestBody AppearanceDto req){
        Appearance newAppearance = this.appearanceDtoToAppearanceConverter.convert(req);
        Appearance savedNewAppearance = this.appearanceService.save(newAppearance);
        AppearanceDto savedNewAppearanceDto = this.appearanceToAppearanceDtoConverter.convert(savedNewAppearance);
        emailService.sendEmail(savedNewAppearance.getReqEmail(),"superfrogschedulercite30363@gmail.com",
                                "SuperFrog Request Received","Dear Customer," + "\n" + "We are glad to inform you that your request has been submitted. \n"
                        + "Your request will be reviewed and a SuperFrog will be assigned if approved\n" +
                        "Thank you!");
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
        AppearanceDto updatedAppearanceDto = this.appearanceToAppearanceDtoConverter.convert(updatedAppearance);
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
        return new Result(true, StatusCode.SUCCESS, "Completion Successful", completedAppearance);
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
