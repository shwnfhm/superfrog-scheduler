package edu.tcu.cs.superfrogscheduler.user.dto;

import edu.tcu.cs.superfrogscheduler.appearance.dto.AppearanceDto;
import edu.tcu.cs.superfrogscheduler.user.PaymentPreference;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record UserDto(
        Long id,
        @NotEmpty(message = "Email is required")
        @Email
        String email,
        String phoneNumber,

        @NotEmpty(message = "First name is required")
        String firstName,

        @NotEmpty(message = "Last name is required")
        String lastName,


        String roles,
        boolean active,

        boolean international,

        PaymentPreference paymentPreference,

        @NotEmpty(message = "Address is required")
        String address,

        List<AppearanceDto> appearanceDtos,

        List<AppearanceDto> completedAppearanceDtos,

        String password
) {
}
