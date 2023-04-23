package edu.tcu.cs.superfrogscheduler.security;

import edu.tcu.cs.superfrogscheduler.appearance.converter.AppearanceDtoToAppearanceConverter;
import edu.tcu.cs.superfrogscheduler.system.Result;
import edu.tcu.cs.superfrogscheduler.system.StatusCode;
import edu.tcu.cs.superfrogscheduler.user.User;
import edu.tcu.cs.superfrogscheduler.user.converter.UserDtoToUserConverter;
import edu.tcu.cs.superfrogscheduler.user.converter.UserToUserDtoConverter;
import edu.tcu.cs.superfrogscheduler.user.dto.UserDto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.endpoint.base-url}/users")
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    private final UserToUserDtoConverter userToUserDtoConverter;

    private final UserDtoToUserConverter userDtoToUserConverter;

    @Autowired
    public AuthController(AuthService authService, UserToUserDtoConverter userToUserDtoConverter, UserDtoToUserConverter userDtoToUserConverter) {
        this.authService = authService;
        this.userToUserDtoConverter = userToUserDtoConverter;
        this.userDtoToUserConverter = userDtoToUserConverter;
    }

    /**
     * Generate a JSON web token if username and password has been authenticated by the BasicAuthenticationFilter.
     * In summary, this filter is responsible for processing any request that has an HTTP request header of Authorization
     * with an authentication scheme of Basic and a Base64-encoded username:password token.
     * <p>
     * BasicAuthenticationFilter will prepare the Authentication object for this login method.
     * Note: before this login method gets called, Spring Security already authenticated the username and password through Basic Auth.
     * Only successful authentication can make it to this method.
     *
     * @return User information and JSON web token
     */
    @PostMapping("/login")
    public Result getLoginInfo(Authentication authentication) {
        LOGGER.debug("Authenticated user: '{}'", authentication.getName());
        return new Result(true, StatusCode.SUCCESS, "User Info and JSON Web Token", this.authService.createLoginInfo(authentication));
    }

    @GetMapping("/me")
    public Result getUser(Authentication authentication){
        LOGGER.debug("Authenticated user: '{}'", authentication.getPrincipal());
        User currentUser = this.authService.getCurrentUser(authentication);
        UserDto currentUserDto = this.userToUserDtoConverter.convert(currentUser);
        return new Result(true, StatusCode.SUCCESS, "Current User Info", currentUserDto);
    }

    @PutMapping("/me")
    public Result updateCurrentUser(Authentication authentication, @Valid @RequestBody UserDto userDto){
        LOGGER.debug("Authenticated user: '{}'", authentication.getPrincipal());
        User update = this.userDtoToUserConverter.convert(userDto);
        User updatedUser = this.authService.update(authentication, update);
        UserDto updatedUserDto = this.userToUserDtoConverter.convert(updatedUser);
        return new Result(true, StatusCode.SUCCESS, "Update Success", updatedUserDto);
    }

}
