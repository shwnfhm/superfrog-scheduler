package edu.tcu.cs.superfrogscheduler.user.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import edu.tcu.cs.superfrogscheduler.user.User;
import edu.tcu.cs.superfrogscheduler.user.dto.UserDto;

@Component
public class UserToUserDtoConverter implements Converter<User, UserDto> {
    public UserDto convert(User user) {
        return null;
    }
}
