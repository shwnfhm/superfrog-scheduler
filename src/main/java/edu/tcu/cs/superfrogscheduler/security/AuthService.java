package edu.tcu.cs.superfrogscheduler.security;

import edu.tcu.cs.superfrogscheduler.system.exception.ObjectNotFoundException;
import edu.tcu.cs.superfrogscheduler.user.MyUserPrincipal;
import edu.tcu.cs.superfrogscheduler.user.User;
import edu.tcu.cs.superfrogscheduler.user.UserRepository;
import edu.tcu.cs.superfrogscheduler.user.UserService;
import edu.tcu.cs.superfrogscheduler.user.converter.UserToUserDtoConverter;
import edu.tcu.cs.superfrogscheduler.user.dto.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final JwtProvider jwtProvider;

    private final UserToUserDtoConverter userToUserDtoConverter;

    private final UserRepository userRepository;

    private final UserService userService;

    private PasswordEncoder passwordEncoder;

    public AuthService(JwtProvider jwtProvider, UserToUserDtoConverter userToUserDtoConverter, UserRepository userRepository, UserService userService, PasswordEncoder passwordEncoder) {
        this.jwtProvider = jwtProvider;
        this.userToUserDtoConverter = userToUserDtoConverter;
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public Map<String, Object> createLoginInfo(Authentication authentication) {

        // Create user info.
        MyUserPrincipal principal = (MyUserPrincipal)authentication.getPrincipal();
        User user = principal.getUser();
        UserDto userDto = this.userToUserDtoConverter.convert(user);
        // Create a JWT.
        String token = this.jwtProvider.createToken(authentication);

        Map<String, Object> loginResultMap = new HashMap<>();

        loginResultMap.put("userInfo", userDto);
        loginResultMap.put("token", token);

        return loginResultMap;
    }

    public User getCurrentUser(Authentication authentication){
        Jwt principal = (Jwt)authentication.getPrincipal();
        Long id = principal.getClaim("id");
        return this.userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("user", id));
    }

    public User update(Authentication authentication, User updatedUser){
        Jwt principal = (Jwt)authentication.getPrincipal();
        Long id = principal.getClaim("id");
        return this.userRepository.findById(id)
                .map(oldUser -> {
                    oldUser.setEmail(updatedUser.getEmail());
                    oldUser.setPhoneNumber(updatedUser.getPhoneNumber());
                    oldUser.setAddress(updatedUser.getAddress());
                    oldUser.setFirstName(updatedUser.getFirstName());
                    oldUser.setLastName(updatedUser.getLastName());
                    oldUser.setInternational(updatedUser.isInternational());
                    oldUser.setPaymentPreference(updatedUser.getPaymentPreference());
                    if(updatedUser.getPassword() != "") {
                        oldUser.setPassword(this.passwordEncoder.encode(updatedUser.getPassword()));
                    }
                    return this.userRepository.save(oldUser);
                })
                .orElseThrow(() -> new ObjectNotFoundException("user", id));
    }


}