package edu.tcu.cs.superfrogscheduler.payment;

import edu.tcu.cs.superfrogscheduler.user.PaymentPreference;
import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * A payment form needs to be generated for each SuperFrog Student so that
 * she can get paid by TCU Payroll.
 * <p>
 * The amount is calculated based on
 * 1. number of appearances done within a given start and end date
 * 2. event type
 * 3. hours
 * 4. mileage (distance between TCU and the event address, the first 2 miles are free of charge, then $0.75/mile)
 */
@Entity
public class PaymentForm {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer paymentFormId;

    private String firstName;

    private String lastName;

    private Long studentId;

    private PaymentPreference paymentPreference;

    private boolean international;

    private String address;

    @Embedded
    private Period paymentPeriod;

    private BigDecimal amount;


    public PaymentForm() {

    }

    public PaymentForm(String firstName, String lastName, Long studentId, Period paymentPeriod, BigDecimal amount, PaymentPreference paymentPreference, String address, boolean international) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentId = studentId;
        this.paymentPeriod = paymentPeriod;
        this.paymentPreference = paymentPreference;
        this.address = address;
        this.amount = amount;
        this.international = international;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Long getStudentId() {
        return studentId;
    }

    public Period getPaymentPeriod() {
        return paymentPeriod;
    }

    public String getAddress() {
        return this.address;
    }

    public PaymentPreference getPaymentPreference() {
        return this.paymentPreference;
    }
    public boolean isInternational() {
        return this.international;
    }

    public BigDecimal getAmount() {
        return amount;
    }

}
