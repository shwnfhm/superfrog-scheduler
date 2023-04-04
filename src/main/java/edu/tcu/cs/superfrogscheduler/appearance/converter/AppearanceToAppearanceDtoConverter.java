package edu.tcu.cs.superfrogscheduler.appearance.converter;

import edu.tcu.cs.superfrogscheduler.appearance.Appearance;
import edu.tcu.cs.superfrogscheduler.appearance.AppearanceStatus;
import edu.tcu.cs.superfrogscheduler.appearance.AppearanceType;
import edu.tcu.cs.superfrogscheduler.appearance.dto.AppearanceDto;
import edu.tcu.cs.superfrogscheduler.user.User;
import edu.tcu.cs.superfrogscheduler.user.converter.UserToUserDtoConverter;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class AppearanceToAppearanceDtoConverter implements Converter<Appearance, AppearanceDto> {

    private final UserToUserDtoConverter userToUserDtoConverter;

    public AppearanceToAppearanceDtoConverter(UserToUserDtoConverter userToUserDtoConverter) {
        this.userToUserDtoConverter = userToUserDtoConverter;
    }

    @Override
    public AppearanceDto convert(Appearance source) {
        final AppearanceDto appearanceDto = new AppearanceDto(
                source.getRequestId(),
                source.getReqFirstName(),
                source.getReqLastName(),
                source.getReqPhoneNumber(),
                source.getReqEmail(),
                source.getAppearanceType(),
                source.getTitle(),
                source.getOrgName(),
                source.getAddress(),
                source.getMileage(),
                source.getEventDate(),
                source.getStartTime(),
                source.getEndTime(),
                source.getStatus(),
                source.getDesc(),
                source.isOnCampus(),
                source.getInstructions(),
                source.getExpenses(),
                source.getOutsideOrg(),
                source.getAssignedSuperFrog() != null
                        ? this.userToUserDtoConverter.convert(source.getAssignedSuperFrog())
                        : null
        );
        return appearanceDto;
    }
}
