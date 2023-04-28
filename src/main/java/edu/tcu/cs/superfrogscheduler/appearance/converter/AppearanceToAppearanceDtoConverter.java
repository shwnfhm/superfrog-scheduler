package edu.tcu.cs.superfrogscheduler.appearance.converter;

import edu.tcu.cs.superfrogscheduler.appearance.Appearance;
import edu.tcu.cs.superfrogscheduler.appearance.dto.AppearanceDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AppearanceToAppearanceDtoConverter implements Converter<Appearance, AppearanceDto> {

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
                source.getAssignedName(),
                source.getAssignedId()
        );
        return appearanceDto;
    }
}
