package edu.tcu.cs.superfrogscheduler.appearance.converter;

import edu.tcu.cs.superfrogscheduler.appearance.Appearance;
import edu.tcu.cs.superfrogscheduler.appearance.dto.AppearanceDto;
import org.springframework.core.convert.converter.Converter;

public class AppearanceToAppearanceDtoConverter implements Converter<Appearance, AppearanceDto> {
    @Override
    public AppearanceDto convert(Appearance source) {
        return null;
    }
}
