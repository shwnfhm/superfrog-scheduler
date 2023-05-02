package edu.tcu.cs.superfrogscheduler.appearance;

import com.fasterxml.jackson.annotation.JsonBackReference;
import edu.tcu.cs.superfrogscheduler.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.time.LocalTime;

import java.io.Serializable;

@Entity
@Table(name = "appearances")
public class Appearance implements Serializable, Comparable<Appearance>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long requestId;

    //first name of the customer
    @NotEmpty(message = "First name is required")
    private String reqFirstName;

    //last name of the customer
    @NotEmpty(message = "Last name is required")
    private String reqLastName;

    //phone number of the customer
    @NotEmpty(message = "Customer phone number is required")
    private String reqPhoneNumber;

    //email of the customer
    @NotEmpty(message = "Customer email is required")
    private String reqEmail;

    //type of appearance
    //either non-profit, TCU or private
    private AppearanceType appearanceType;

    //name of the event
    @NotEmpty(message = "Event title is required")
    private String title;

    //organization requesting the appearance
    @NotEmpty(message = "Org name is required")
    private String orgName;

    //address of appearance
    @NotEmpty(message = "Address is required")
    private String address;

    //distance from campus
    private Double mileage;

    //appearance date
    private LocalDate eventDate;

    //appearance start time
    private LocalTime startTime;

    //appearance end time
    private LocalTime endTime;

    //status of the event
    private AppearanceStatus status;

    //appearance description, customer supplied
    @NotEmpty(message = "Description is required")
    private String desc;

    //true if event is on campus, false otherwise
    private boolean onCampus;

    //customer supplied instructions
    @NotEmpty(message = "Instructions are required")
    private String instructions;

    //customer supplied benefits/expenses
    @NotEmpty(message = "Expenses/benefits required")
    private String expenses;

    //outside organizations sponsoring the appearance
    @NotEmpty(message = "Outside org required")
    private String outsideOrg;

    @ManyToOne
    @JsonBackReference
    private User assignedSuperFrog;

    private String assignedName;

    private Long assignedId;

    public Appearance() {

    }

    public Double getMileageOver(Double freeMileage) {
        return this.mileage.compareTo(freeMileage) <= 0 ? 0.0 : this.mileage - freeMileage;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getReqFirstName() {
        return reqFirstName;
    }

    public void setReqFirstName(String reqFirstName) {
        this.reqFirstName = reqFirstName;
    }

    public String getReqLastName() {
        return reqLastName;
    }

    public void setReqLastName(String reqLastName) {
        this.reqLastName = reqLastName;
    }

    public String getReqPhoneNumber() {
        return reqPhoneNumber;
    }

    public void setReqPhoneNumber(String reqPhoneNumber) {
        this.reqPhoneNumber = reqPhoneNumber;
    }

    public String getReqEmail() {
        return reqEmail;
    }

    public void setReqEmail(String reqEmail) {
        this.reqEmail = reqEmail;
    }

    public AppearanceType getAppearanceType() {
        return appearanceType;
    }

    public void setAppearanceType(AppearanceType appearanceType) {
        this.appearanceType = appearanceType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public AppearanceStatus getStatus() {
        return status;
    }

    public void setStatus(AppearanceStatus status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isOnCampus() {
        return onCampus;
    }

    public void setOnCampus(boolean onCampus) {
        this.onCampus = onCampus;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getExpenses() {
        return expenses;
    }

    public void setExpenses(String expenses) {
        this.expenses = expenses;
    }

    public String getOutsideOrg() {
        return outsideOrg;
    }

    public void setOutsideOrg(String outsideOrg) {
        this.outsideOrg = outsideOrg;
    }

    public User getAssignedSuperFrog() {
        return assignedSuperFrog;
    }

    public void setAssignedSuperFrog(User assignee) {
        this.assignedSuperFrog = assignee;
    }

    public String getAssignedName() {
        return assignedName;
    }

    public void setAssignedName(String assignedName) {
        this.assignedName = assignedName;
    }

    public Long getAssignedId() {
        return assignedId;
    }

    public void setAssignedId(Long assignedId) {
        this.assignedId = assignedId;
    }

    @Override
    public int compareTo(Appearance o) {
        return this.assignedName.compareTo(o.getAssignedName());
    }
}
