package edu.tcu.cs.superfrogscheduler.user.converter;

import edu.tcu.cs.superfrogscheduler.user.User;
import edu.tcu.cs.superfrogscheduler.user.dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
@Component
public class UserDtoToUserConverter implements Converter<UserDto, User>{
    @Override
    public User convert(UserDto source) {
        return null;
    }
}
