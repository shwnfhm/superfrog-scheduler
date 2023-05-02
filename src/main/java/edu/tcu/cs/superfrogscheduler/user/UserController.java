package edu.tcu.cs.superfrogscheduler.user;

import edu.tcu.cs.superfrogscheduler.appearance.Appearance;
import edu.tcu.cs.superfrogscheduler.appearance.AppearanceService;
import edu.tcu.cs.superfrogscheduler.appearance.AppearanceStatus;
import edu.tcu.cs.superfrogscheduler.email.EmailService;
import edu.tcu.cs.superfrogscheduler.system.Result;
import edu.tcu.cs.superfrogscheduler.system.StatusCode;
import edu.tcu.cs.superfrogscheduler.user.converter.UserDtoToUserConverter;
import edu.tcu.cs.superfrogscheduler.user.converter.UserToUserDtoConverter;
import edu.tcu.cs.superfrogscheduler.user.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("${api.endpoint.base-url}/users")
public class UserController {

    private final UserService userService;

    private final UserDtoToUserConverter userDtoToUserConverter; // Convert userDto to user.

    private final UserToUserDtoConverter userToUserDtoConverter; // Convert user to userDto.

    private EmailService emailService;

    private AppearanceService appearanceService;

    public UserController(UserService userService, UserDtoToUserConverter userDtoToUserConverter, UserToUserDtoConverter userToUserDtoConverter, EmailService emailService, AppearanceService appearanceService) {
        this.userService = userService;
        this.userDtoToUserConverter = userDtoToUserConverter;
        this.userToUserDtoConverter = userToUserDtoConverter;
        this.emailService = emailService;
        this.appearanceService = appearanceService;
    }

    @GetMapping
    public Result findAllUsers() {
        List<User> foundUsers = this.userService.findAll();

        // Convert foundUsers to a list of UserDtos.
        List<UserDto> userDtos = foundUsers.stream()
                .map(this.userToUserDtoConverter::convert)
                .collect(Collectors.toList());

        // Note that UserDto does not contain password field.
        return new Result(true, StatusCode.SUCCESS, "Find All Success", userDtos);
    }

    @GetMapping("/{userId}")
    public Result findUserById(@PathVariable Long userId) {
        User foundUser = this.userService.findById(userId);
        UserDto userDto = this.userToUserDtoConverter.convert(foundUser);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", userDto);
    }

    @PostMapping
    public Result addUser(@Valid @RequestBody User user){
        User savedUser = this.userService.save(user);
        UserDto savedUserDto = this.userToUserDtoConverter.convert(savedUser);
        emailService.sendEmail(savedUser.getEmail(), "superfrogschedulercite30363@gmail.com",
                "SuperFrog Account Created", "Dear SuperFrog," + "\n" + "An account has been created for you\n"
                + "Your temporary password is 'superfrogswag'. Please log in to change your password."
        );
        return new Result(true, StatusCode.SUCCESS, "Success", savedUserDto);
    }

    // We are not using this to update password, need another changePassword method in this class.
    @PutMapping("/{userId}")
    public Result updateUser(@PathVariable Long userId, @Valid @RequestBody UserDto userDto) {
        User update = this.userDtoToUserConverter.convert(userDto);
        User updatedUser = this.userService.update(userId, update);
        UserDto updatedUserDto = this.userToUserDtoConverter.convert(updatedUser);
        return new Result(true, StatusCode.SUCCESS, "Update Success", updatedUserDto);
    }

    @PostMapping("/inactive/{userId}")
    public Result deactivateUser(@PathVariable Long userId, @Valid @RequestBody Map<String, String> reason){
        User deactivatedUser = this.userService.deactivate(userId);
        UserDto deactivatedUserDto = this.userToUserDtoConverter.convert(deactivatedUser);
        emailService.sendEmail(deactivatedUser.getEmail(), "superfrogschedulercite30363@gmail.com",
                "SuperFrog Account Deactivated", "Dear SuperFrog," + "\n" + "Your account has been deactivated\n"
                + "The reason is: " + reason.get("message") + "\n"
        );
        return new Result(true, StatusCode.SUCCESS, "Deactivation Success", deactivatedUserDto);
    }

    @PostMapping("/active/{userId}")
    public Result activateUser(@PathVariable Long userId){
        User activatedUser = this.userService.activate(userId);
        UserDto activatedUserDto = this.userToUserDtoConverter.convert(activatedUser);
        emailService.sendEmail(activatedUser.getEmail(), "superfrogschedulercite30363@gmail.com",
                "SuperFrog Account Activated", "Dear SuperFrog," + "\n" + "Your account has been activated\n"
        );
        return new Result(true, StatusCode.SUCCESS, "Activation Successful", activatedUserDto);
    }

    @PostMapping("/assignments/{userId}/{requestId}")
    public Result assignUserToAppearance(@PathVariable Long requestId, @PathVariable Long userId){
        Appearance oldAppearance = this.appearanceService.findById(requestId);
        if(oldAppearance.getStatus() == AppearanceStatus.ASSIGNED){
            emailService.sendEmail(oldAppearance.getAssignedSuperFrog().getEmail(), "superfrogschedulercite30363@gmail.com",
                    "Appearance ID " + oldAppearance.getRequestId().toString() + " Unassigned", "Dear Superfrog," + "\n" + "You have been unassigned from appearance " + oldAppearance.getRequestId().toString() + " \n"
                            + "We apologize for any inconvenience\n" +
                            "Thank you!");
        }
        Appearance updatedAppearance = this.userService.assign(requestId, userId);
        emailService.sendEmail(updatedAppearance.getAssignedSuperFrog().getEmail(), "superfrogschedulercite30363@gmail.com",
                "Appearance ID " + oldAppearance.getRequestId().toString() + " Assigned", "Dear SuperFrog," + "\n" + "You have been successfully assigned to appearance " + updatedAppearance.getRequestId().toString() + " \n"
                        + "Please make any necessary preparations for this event.\n" +
                        "Thank you!");
        emailService.sendEmail(updatedAppearance.getReqEmail(), "superfrogschedulercite30363@gmail.com",
                "SuperFrog Assigned", "Dear Customer," + "\n" + "We are glad to inform you that a SuperFrog has been assigned to your event \n"
                        + "Please submit payment if you have not already\n" +
                        "Thank you!");
        return new Result(true, StatusCode.SUCCESS, "Assignment Successful", updatedAppearance);
    }

    @DeleteMapping("/assignments/{userId}/{requestId}")
    public Result removeUserFromAppearance(@PathVariable Long requestId, @PathVariable Long userId){
        Appearance oldAppearance = this.appearanceService.findById(requestId);
        emailService.sendEmail(oldAppearance.getAssignedSuperFrog().getEmail(), "superfrogschedulercite30363@gmail.com",
                "Appearance ID " + oldAppearance.getRequestId().toString() + " Unassigned", "Dear SuperFrog," + "\n" + "You have been unassigned from appearance " + oldAppearance.getRequestId().toString() + " .\n"
                        + "We apologize for any inconvenience\n");
        Appearance updatedAppearance = this.userService.unassign(requestId);
        if(updatedAppearance.getStatus() == AppearanceStatus.APPROVED) {
            emailService.sendEmail(updatedAppearance.getReqEmail(), "superfrogschedulercite30363@gmail.com",
                    "SuperFrog Unassigned for Request " + updatedAppearance.getRequestId().toString(), "Dear Customer," + "\n" + "We regret to inform you that the SuperFrog assigned to your event (ID: " + updatedAppearance.getRequestId().toString() + " ) has been removed \n"
                            + "We will try to assign a replacement as soon as possible\n" +
                            "Thank you for your patience");
            List<User> allUsers = this.userService.findAll();
            emailService.emailAllUsers(allUsers, requestId);
        }
        emailService.sendEmail("superfrogschedulercite30363@gmail.com", "superfrogschedulercite30363@gmail.com",
                "SuperFrog ID: " + userId.toString() + " Unassigned for Request " + updatedAppearance.getRequestId().toString(), "Dear Spirit Director," + "\n" + "The SuperFrog assigned to event ID: " + updatedAppearance.getRequestId().toString() + " has been removed/unassigned. \n"
        );
        return new Result(true, StatusCode.SUCCESS, "Unassignment Successful", updatedAppearance);
    }

    @PostMapping("/criteria")
    public Result searchUsers(@Valid @RequestBody UserQuery userQuery){
        List<User> resultUsers = this.userService.searchUsers(userQuery);
        List<UserDto> resultUsersDto = new ArrayList<>();
        for(int i = 0; i<resultUsers.size(); i++){
            resultUsersDto.add(this.userToUserDtoConverter.convert(resultUsers.get(i)));
        }
        return new Result(true, StatusCode.SUCCESS, "Search Successful", resultUsersDto);
    }
}
