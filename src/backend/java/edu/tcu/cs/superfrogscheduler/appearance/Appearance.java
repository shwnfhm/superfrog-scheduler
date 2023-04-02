package backend.java.edu.tcu.cs.superfrogscheduler.appearance;

import jakarta.persistence.*;

@Entity
@Table(name = "Appearances")
public class Appearance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String desc;

    private String date;

    private String stime;

    private String etime;

    private String status;

    private String reqEmail;

    private String assignEmail;

    public Appearance() {

    }

    public Appearance(Long id, String title, String desc, String date, String stime, String etime, String status, String reqEmail, String assignEmail) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.stime = stime;
        this.etime = etime;
        this.status = status;
        this.reqEmail = reqEmail;
        this.assignEmail = assignEmail;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
