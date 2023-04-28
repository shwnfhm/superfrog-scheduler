package edu.tcu.cs.superfrogscheduler.appearance.dto;

import edu.tcu.cs.superfrogscheduler.appearance.AppearanceStatus;
import edu.tcu.cs.superfrogscheduler.appearance.AppearanceType;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppearanceDto(

        Long requestId,

        @NotEmpty(message = "First name is required")
        String reqFirstName,

        @NotEmpty(message = "Last name is required")
        String reqLastName,

        @NotEmpty(message = "Phone number is required")
        String reqPhoneNumber,
        @NotEmpty(message = "Email is required.")
        String reqEmail,

        AppearanceType appearanceType,

        @NotEmpty(message = "Title is required")
        String title,

        @NotEmpty(message = "Org name is required")
        String orgName,

        @NotEmpty(message = "Address is required")
        String address,
        Double mileage,
        LocalDate eventDate,
        LocalTime startTime,
        LocalTime endTime,
        AppearanceStatus status,

        @NotEmpty(message = "Description is required")
        String desc,
        boolean onCampus,

        @NotEmpty(message = "Instructions are required")
        String instructions,

        @NotEmpty(message = "Expenses/benefits are required")
        String expenses,

        @NotEmpty(message = "Outside org is required")
        String outsideOrg,

        String assignedName,

        Long assignedId
) {
}
