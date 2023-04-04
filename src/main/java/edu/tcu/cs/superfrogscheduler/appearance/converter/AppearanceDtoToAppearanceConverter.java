package edu.tcu.cs.superfrogscheduler.appearance.converter;

import edu.tcu.cs.superfrogscheduler.appearance.Appearance;
import edu.tcu.cs.superfrogscheduler.appearance.dto.AppearanceDto;
import org.springframework.core.convert.converter.Converter;

import java.util.Objects;

public class AppearanceDtoToAppearanceConverter implements Converter<AppearanceDto, Appearance> {
    @Override
    public Appearance convert(AppearanceDto source) {
        Appearance converted = new Appearance();
        converted.setStatus(source.status());
        converted.setAddress(source.address());
        converted.setDesc(source.desc());
        converted.setRequestId(source.requestId());
        converted.setStatus(source.status());
        converted.setAssignedSuperFrog(source.assignedSuperFrog());
        converted.setExpenses(source.expenses());
        converted.setMileage(source.mileage());
        converted.setEndTime(source.endTime());
        converted.setStartTime(source.startTime());
        converted.setEventDate(source.eventDate());
        converted.setInstructions(source.instructions());
        converted.setOnCampus(source.onCampus());
        converted.setTitle(source.title());
        converted.setReqFirstName(source.reqFirstName());
        converted.setReqLastName(source.reqLastName());
        converted.setReqEmail(source.reqEmail());
        converted.setReqPhoneNumber(source.reqPhoneNumber());
        return converted;
    }
}
