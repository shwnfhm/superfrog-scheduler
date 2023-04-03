package edu.tcu.cs.superfrogscheduler.user;

import edu.tcu.cs.superfrogscheduler.appearance.Appearance;
import edu.tcu.cs.superfrogscheduler.appearance.AppearanceType;
import edu.tcu.cs.superfrogscheduler.payment.PaymentForm;
import edu.tcu.cs.superfrogscheduler.payment.Period;
import edu.tcu.cs.superfrogscheduler.payment.TransportationFeeCalculator;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Table(name = "Users", uniqueConstraints = {@UniqueConstraint(columnNames = "Email")})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //user's email address
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    //user's phone number (without dashes)
    private String phoneNumber;

    //user's password
    @NotBlank
    @Size(max = 40)
    private String password;

    //user's first name
    @NotBlank
    private String firstName;

    //user's last name
    @NotBlank
    private String lastName;

    //used to indicate whether a SuperFrog Student, SpiritDirector
    //or customer. {"SUPERFROG", "SPIRITDIRECTOR"}
    private String role;

    //true = activated, false = deactivated
    private boolean active;

    public User() {

    }

    public PaymentForm generatePaymentForm(List<Appearance> requests, Period paymentPeriod) {
        /**
         * Group the given requests by their event type (TCU, NONPROFIT, and PRIVATE), then for each event type, calculate the number of hours
         * this SuperFrogStudent has worked. The result of this step is a Map<EventType, Double>.
         * For example:
         *  EventType.TCU -> 2.5 hrs
         *  EventType.NONPROFIT -> 3 hrs
         *  EventType.PRIVATE -> 2 hrs
         */
        Map<AppearanceType, Double> eventTypeHoursMap = requests.stream().collect(Collectors.groupingBy(request -> request.getAppearanceType(),
                Collectors.mapping(request -> request.getStartTime().until(request.getEndTime(), ChronoUnit.MINUTES) / 60.0,
                        Collectors.reducing(0.0, Double::sum))));

        BigDecimal totalAppearanceFee = new BigDecimal(0.0);

        // Calculate the total appearance fee by going over the map.
        for (Map.Entry<AppearanceType, Double> entry : eventTypeHoursMap.entrySet()) {
            totalAppearanceFee = totalAppearanceFee
                    .add(BigDecimal.valueOf(entry.getKey().getHourlyRate())
                            .multiply(BigDecimal.valueOf(entry.getValue())));
        }

        // We also need to consider transportation fee.
        BigDecimal transportationFee = TransportationFeeCalculator.INSTANCE.calculateTransportationFee(requests);

        BigDecimal totalAmount = totalAppearanceFee.add(transportationFee);

        return new PaymentForm(this.firstName, this.lastName, this.id, paymentPeriod, totalAmount);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
