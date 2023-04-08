package edu.tcu.cs.superfrogscheduler.user.converter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import edu.tcu.cs.superfrogscheduler.user.User;
import edu.tcu.cs.superfrogscheduler.user.dto.UserDto;

@Component
public class UserToUserDtoConverter implements Converter<User, UserDto> {
    public UserDto convert(User source) {
        final UserDto userDto = new UserDto(source.getId(),
                                            source.getEmail(),
                                            source.getPhoneNumber(),
                                            source.getFirstName(),
                                            source.getLastName(),
                                            source.getRoles(),
                                            source.isActive(),
                                            source.isInternational(),
                                            source.getPaymentPreference());
        return userDto;
    }
}
