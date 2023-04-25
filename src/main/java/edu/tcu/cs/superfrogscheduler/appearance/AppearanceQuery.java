package edu.tcu.cs.superfrogscheduler.appearance;

import java.time.LocalDate;

public record AppearanceQuery(

        String title,
        AppearanceStatus status,
        String assignedEmail,

        String reqEmail,

        String reqPhoneNumber,

        String reqFirstName,

        String reqLastName,

        LocalDate startDate,

        LocalDate endDate
) {

}
