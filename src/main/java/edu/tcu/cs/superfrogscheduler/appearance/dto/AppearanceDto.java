package edu.tcu.cs.superfrogscheduler.appearance.dto;

import edu.tcu.cs.superfrogscheduler.appearance.AppearanceStatus;
import edu.tcu.cs.superfrogscheduler.appearance.AppearanceType;
import edu.tcu.cs.superfrogscheduler.user.User;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppearanceDto(
        String reqFirstName,
        String reqLastName,

        String reqPhoneNumber,

        @NotEmpty(message = "Email is required.")
        String reqEmail,
        AppearanceType appearanceType,
        String title,
        String orgName,
        String address,
        Double mileage,
        LocalDate eventDate,
        LocalTime startTime,
        LocalTime endTime,
        AppearanceStatus status,
        String desc,
        boolean onCampus,
        String instructions,
        String expenses,
        String outsideOrg,
        User assignedSuperFrog
) {
}
