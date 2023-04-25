package edu.tcu.cs.superfrogscheduler.user;

public record UserQuery(
        String firstName,

        String lastName,

        String phoneNumber,

        String email
) {
}
