package edu.tcu.cs.superfrogscheduler.appearance;

import edu.tcu.cs.superfrogscheduler.appearance.converter.AppearanceDtoToAppearanceConverter;
import edu.tcu.cs.superfrogscheduler.appearance.converter.AppearanceToAppearanceDtoConverter;
import edu.tcu.cs.superfrogscheduler.appearance.dto.AppearanceDto;
import edu.tcu.cs.superfrogscheduler.system.Result;
import edu.tcu.cs.superfrogscheduler.system.StatusCode;
import edu.tcu.cs.superfrogscheduler.user.User;
import edu.tcu.cs.superfrogscheduler.user.UserRepository;
import edu.tcu.cs.superfrogscheduler.user.converter.UserDtoToUserConverter;
import edu.tcu.cs.superfrogscheduler.user.dto.UserDto;
import edu.tcu.cs.superfrogscheduler.appearance.export.ExcelExporter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.endpoint.base-url}/appearances")
public class AppearanceController {

    private AppearanceService appearanceService;

    private final JavaMailSender javaMailSender;

    private AppearanceDtoToAppearanceConverter appearanceDtoToAppearanceConverter;

    private AppearanceToAppearanceDtoConverter appearanceToAppearanceDtoConverter;
    private UserDtoToUserConverter userDtoToUserConverter;

    public AppearanceController(AppearanceService appearanceService, JavaMailSender javaMailSender, AppearanceDtoToAppearanceConverter appearanceDtoToAppearanceConverter, AppearanceToAppearanceDtoConverter appearanceToAppearanceDtoConverter, UserDtoToUserConverter userDtoToUserConverter) {
        this.appearanceService = appearanceService;
        this.javaMailSender = javaMailSender;
        this.appearanceDtoToAppearanceConverter = appearanceDtoToAppearanceConverter;
        this.appearanceToAppearanceDtoConverter = appearanceToAppearanceDtoConverter;
        this.userDtoToUserConverter = userDtoToUserConverter;
    }

    @PostMapping
    public Result addRequest(@Valid @RequestBody AppearanceDto req){
        Appearance newAppearance = this.appearanceDtoToAppearanceConverter.convert(req);
        Appearance savedNewAppearance = this.appearanceService.save(newAppearance);
        AppearanceDto savedNewAppearanceDto = this.appearanceToAppearanceDtoConverter.convert(savedNewAppearance);
        SimpleMailMessage assignMail = new SimpleMailMessage();
        assignMail.setTo(savedNewAppearance.getReqEmail());
        assignMail.setFrom("superfrogschedulercite30363@gmail.com");
        assignMail.setSubject("SuperFrog Request Received");
        assignMail.setText("Dear Customer," + "\n" + "We are glad to inform you that your request has been submitted. \n"
                + "Your request will be reviewed and a SuperFrog will be assigned if approved\n" +
                "Thank you!");
        javaMailSender.send(assignMail);
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
    
    @PostMapping("/{requestId}/user")
    public Result assignUserToAppearance(@PathVariable Long requestId, @Valid @RequestBody UserDto assigneeDto){
        User assignee = this.userDtoToUserConverter.convert(assigneeDto);
        Appearance updatedAppearance = this.appearanceService.assign(requestId, assignee);
        SimpleMailMessage assignMail = new SimpleMailMessage();
        assignMail.setTo(updatedAppearance.getReqEmail());
        assignMail.setFrom("superfrogschedulercite30363@gmail.com");
        assignMail.setSubject("SuperFrog Assigned");
        assignMail.setText("Dear Customer," + "\n" + "We are glad to inform you that a SuperFrog has been assigned to your event \n"
                + "Please submit payment if you have not already\n" +
                "Thank you!");
        javaMailSender.send(assignMail);
        return new Result(true, StatusCode.SUCCESS, "Assignment Successful", updatedAppearance);
    }

    @DeleteMapping("/{requestId}/user")
    public Result removeUserFromAppearance(@PathVariable Long requestId){
        Appearance updatedAppearance = this.appearanceService.unassign(requestId);
        SimpleMailMessage assignMail = new SimpleMailMessage();
        assignMail.setTo(updatedAppearance.getReqEmail());
        assignMail.setFrom("superfrogschedulercite30363@gmail.com");
        assignMail.setSubject("SuperFrog Unassigned");
        assignMail.setText("Dear Customer," + "\n" + "We regret to inform you that the SuperFrog assigned to your event has been removed \n"
                + "We will try to assign a replacement as soon as possible\n" +
                "Thank you for your patience");
        javaMailSender.send(assignMail);
        return new Result(true, StatusCode.SUCCESS, "Unassignment Successful", updatedAppearance);
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

}
