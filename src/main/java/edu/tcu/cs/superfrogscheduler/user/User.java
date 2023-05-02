package edu.tcu.cs.superfrogscheduler.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import edu.tcu.cs.superfrogscheduler.appearance.Appearance;
import edu.tcu.cs.superfrogscheduler.appearance.AppearanceType;
import edu.tcu.cs.superfrogscheduler.payment.PaymentForm;
import edu.tcu.cs.superfrogscheduler.payment.Period;
import edu.tcu.cs.superfrogscheduler.payment.TransportationFeeCalculator;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //user's email address
    @NotEmpty(message = "Email is required.")
    @Email
    private String email;

    //user's phone number (without dashes)
    private String phoneNumber;

    //user's password
    @NotEmpty(message = "Password is required.")
    private String password;

    //user's first name
    @NotBlank
    private String firstName;

    @NotBlank
    private String address;

    //user's last name
    @NotBlank
    private String lastName;

    //used to indicate whether a SuperFrog Student, SpiritDirector
    //{"SUPERFROG", "SPIRITDIRECTOR"}
    @NotEmpty(message = "Roles are required")
    private String roles;

    //true = activated, false = deactivated
    private boolean active;

    //false = domestic, true = international student
    private boolean international;

    private PaymentPreference paymentPreference;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference
    @Fetch(FetchMode.SELECT)
    private List<Appearance> assignedAppearances = new ArrayList<>();


    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<Appearance> completedAppearances = new ArrayList<>();

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

        return new PaymentForm(this.firstName, this.lastName, this.id, paymentPeriod, totalAmount, this.paymentPreference, this.address, this.international);
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

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isInternational() {
        return international;
    }

    public void setInternational(boolean international) {
        this.international = international;
    }

    public PaymentPreference getPaymentPreference() {
        return paymentPreference;
    }

    public void setPaymentPreference(PaymentPreference paymentPreference) {
        this.paymentPreference = paymentPreference;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Appearance> getAssignedAppearances() {
        return assignedAppearances;
    }

    public void addAssignedAppearance(Appearance appearance){
        appearance.setAssignedSuperFrog(this);
        appearance.setAssignedName(this.firstName + " " + this.lastName);
        appearance.setAssignedId(this.id);
        this.assignedAppearances.add(appearance);
    }

    public void removeAssignedAppearance(Appearance appearance){
        appearance.setAssignedSuperFrog(null);
        appearance.setAssignedName(null);
        appearance.setAssignedId(null);
        this.assignedAppearances.remove(appearance);
    }

    public void setAssignedAppearances(List<Appearance> appearances){
        this.assignedAppearances = appearances;
    }

    public void addCompletedAppearance(Appearance appearance){
        this.assignedAppearances.remove(appearance);
        this.completedAppearances.add(appearance);
    }

    public List<Appearance> getCompletedAppearances(){
        return completedAppearances;
    }

    public void setCompletedAppearances(List<Appearance> appearances){
        this.completedAppearances = appearances;
    }

}
