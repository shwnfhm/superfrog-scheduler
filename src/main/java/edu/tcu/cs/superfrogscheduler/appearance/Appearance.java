package edu.tcu.cs.superfrogscheduler.appearance;

import edu.tcu.cs.superfrogscheduler.user.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Appearances")
public class Appearance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    //first name of the customer
    private String reqFirstName;

    //last name of the customer
    private String reqLastName;

    //phone number of the customer
    private String reqPhoneNumber;

    //email of the customer
    private String reqEmail;

    //type of appearance
    //either non-profit, TCU or private
    private AppearanceType appearanceType;

    //name of the event
    private String title;

    //organization requesting the appearance
    private String orgName;

    //address of appearance
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
    //either "PENDING",
    private AppearanceStatus status;

    //appearance description, customer supplied
    private String desc;

    //true if event is on campus, false otherwise
    private boolean onCampus;

    //customer supplied instructions
    private String instructions;

    //customer supplied benefits/expenses
    private String expenses;

    //outside organizations sponsoring the appearance
    private String outsideOrg;

    @ManyToOne
    private User assignedSuperFrog;

    public Appearance() {

    }

    public Appearance(String reqFirstName, String reqLastName, String reqPhoneNumber, String reqEmail, AppearanceType appearanceType, String title, String orgName, String address, Double mileage, LocalDate eventDate, LocalTime startTime, LocalTime endTime, AppearanceStatus status, String desc, boolean onCampus, String instructions, String expenses, String outsideOrg, User assignedSuperFrog) {
        this.reqFirstName = reqFirstName;
        this.reqLastName = reqLastName;
        this.reqPhoneNumber = reqPhoneNumber;
        this.reqEmail = reqEmail;
        this.appearanceType = appearanceType;
        this.title = title;
        this.orgName = orgName;
        this.address = address;
        this.mileage = mileage;
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.desc = desc;
        this.onCampus = onCampus;
        this.instructions = instructions;
        this.expenses = expenses;
        this.outsideOrg = outsideOrg;
        this.assignedSuperFrog = assignedSuperFrog;
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

    public void setAssignedSuperFrog(User assignedSuperFrog) {
        this.assignedSuperFrog = assignedSuperFrog;
    }
}
