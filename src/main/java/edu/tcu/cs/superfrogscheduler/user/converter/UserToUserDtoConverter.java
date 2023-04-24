package edu.tcu.cs.superfrogscheduler.user.converter;

import edu.tcu.cs.superfrogscheduler.appearance.converter.AppearanceToAppearanceDtoConverter;
import edu.tcu.cs.superfrogscheduler.appearance.dto.AppearanceDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import edu.tcu.cs.superfrogscheduler.user.User;
import edu.tcu.cs.superfrogscheduler.user.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserToUserDtoConverter implements Converter<User, UserDto> {

    private final AppearanceToAppearanceDtoConverter appearanceToAppearanceDtoConverter;

    public UserToUserDtoConverter(AppearanceToAppearanceDtoConverter appearanceToAppearanceDtoConverter) {
        this.appearanceToAppearanceDtoConverter = appearanceToAppearanceDtoConverter;
    }

    public UserDto convert(User source) {

        List<AppearanceDto> appearanceDtos = new ArrayList<>();
        for(int i = 0; i < source.getAssignedAppearances().size(); i++){
            appearanceDtos.add(this.appearanceToAppearanceDtoConverter.convert(source.getAssignedAppearances().get(i)));
        }

        List<AppearanceDto> completedAppearanceDtos = new ArrayList<>();
        for(int i = 0; i < source.getCompletedAppearances().size(); i++){
            completedAppearanceDtos.add(this.appearanceToAppearanceDtoConverter.convert(source.getCompletedAppearances().get(i)));
        }

        final UserDto userDto = new UserDto(source.getId(),
                source.getEmail(),
                source.getPhoneNumber(),
                source.getFirstName(),
                source.getLastName(),
                source.getRoles(),
                source.isActive(),
                source.isInternational(),
                source.getPaymentPreference(),
                source.getAddress(),
                appearanceDtos,
                completedAppearanceDtos,
                ""
        );
        return userDto;
    }
}