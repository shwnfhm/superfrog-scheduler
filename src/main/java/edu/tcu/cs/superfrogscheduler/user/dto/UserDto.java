package edu.tcu.cs.superfrogscheduler.user.dto;

import edu.tcu.cs.superfrogscheduler.user.PaymentPreference;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

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

        @NotEmpty(message = "Roles are required")
        String roles,
        boolean active,

        boolean international,

        PaymentPreference paymentPreference
) {
}
