package edu.tcu.cs.superfrogscheduler.user.converter;

import edu.tcu.cs.superfrogscheduler.user.User;
import edu.tcu.cs.superfrogscheduler.user.dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
@Component
public class UserDtoToUserConverter implements Converter<UserDto, User>{
    @Override
    public User convert(UserDto source) {
        User converted = new User();
        converted.setEmail(source.email());
        converted.setRoles(source.roles());
        converted.setId(source.id());
        converted.setFirstName(source.firstName());
        converted.setLastName(source.lastName());
        converted.setActive(source.active());
        converted.setPhoneNumber(source.phoneNumber());
        converted.setInternational(source.international());
        converted.setPaymentPreference(source.paymentPreference());
        converted.setAddress(source.address());
        converted.setPassword(source.password());
        return converted;
    }
}