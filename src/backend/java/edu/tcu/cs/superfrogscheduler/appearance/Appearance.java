package backend.java.edu.tcu.cs.superfrogscheduler.appearance;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Appearances")
public class Appearance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private AppearanceType appearanceType;

    private String address;

    private Double mileage;

    private LocalDate eventDate;

    private LocalTime eventTime;

    private AppearanceStatus status;

    private String title;

    private String desc;

    private String date;

    private String orgName;

    private boolean onCampus;

    private String instructions;

    private String expenses;

    private String outsideOrg;

    private String stime;

    private String etime;

    private String reqEmail;

    private String assignEmail;

    public Appearance() {

    }

    public Appearance(Long id, AppearanceType appearanceType, String address, Double mileage, LocalDate eventDate, LocalTime eventTime, AppearanceStatus status, String title, String desc, String date, String orgName, boolean onCampus, String instructions, String expenses, String outsideOrg, String stime, String etime, String reqEmail, String assignEmail) {
        this.id = id;
        this.appearanceType = appearanceType;
        this.address = address;
        this.mileage = mileage;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.status = status;
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.orgName = orgName;
        this.onCampus = onCampus;
        this.instructions = instructions;
        this.expenses = expenses;
        this.outsideOrg = outsideOrg;
        this.stime = stime;
        this.etime = etime;
        this.reqEmail = reqEmail;
        this.assignEmail = assignEmail;
    }

    public Double getMileageOver(Double freeMileage) {
        return this.mileage.compareTo(freeMileage) <= 0 ? 0.0 : this.mileage - freeMileage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppearanceType getAppearanceType() {
        return appearanceType;
    }

    public void setAppearanceType(AppearanceType appearanceType) {
        this.appearanceType = appearanceType;
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

    public LocalTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalTime eventTime) {
        this.eventTime = eventTime;
    }

    public AppearanceStatus getStatus() {
        return status;
    }

    public void setStatus(AppearanceStatus status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
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

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public String getReqEmail() {
        return reqEmail;
    }

    public void setReqEmail(String reqEmail) {
        this.reqEmail = reqEmail;
    }

    public String getAssignEmail() {
        return assignEmail;
    }

    public void setAssignEmail(String assignEmail) {
        this.assignEmail = assignEmail;
    }
}
