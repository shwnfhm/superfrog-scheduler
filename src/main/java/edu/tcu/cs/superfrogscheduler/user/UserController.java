package edu.tcu.cs.superfrogscheduler.user;

import edu.tcu.cs.superfrogscheduler.system.Result;
import edu.tcu.cs.superfrogscheduler.system.StatusCode;
import edu.tcu.cs.superfrogscheduler.user.converter.UserDtoToUserConverter;
import edu.tcu.cs.superfrogscheduler.user.converter.UserToUserDtoConverter;
import edu.tcu.cs.superfrogscheduler.user.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("${api.endpoint.base-url}/users")
public class UserController {

    private final UserService userService;

    private final UserDtoToUserConverter userDtoToUserConverter; // Convert userDto to user.

    private final UserToUserDtoConverter userToUserDtoConverter; // Convert user to userDto.

    public UserController(UserService userService, UserDtoToUserConverter userDtoToUserConverter, UserToUserDtoConverter userToUserDtoConverter) {
        this.userService = userService;
        this.userDtoToUserConverter = userDtoToUserConverter;
        this.userToUserDtoConverter = userToUserDtoConverter;
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


}
