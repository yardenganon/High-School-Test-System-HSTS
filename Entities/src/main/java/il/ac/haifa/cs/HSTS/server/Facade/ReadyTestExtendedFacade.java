package il.ac.haifa.cs.HSTS.server.Facade;

import il.ac.haifa.cs.HSTS.server.Status.Status;

import java.io.Serializable;
import java.util.Date;

public class ReadyTestExtendedFacade implements Serializable {
    int id;
    String teacherWriter;
    String courseName;
    Date dateCreated;
    int time;
    private Boolean isManual;
    private Boolean isActive;
    private String code;
    private int timeToAdd;
    private String timeExtensionReason;
    private Status timeExtensionRequestStatus;

    public ReadyTestExtendedFacade(){}

    public ReadyTestExtendedFacade(int id, String teacherWriter, String courseName, Date dateCreated,
                           int time, Boolean isManual, Boolean isActive, String code, int timeToAdd,
                           String timeExtensionReason, Status timeExtensionRequestStatus) {
        System.out.println("in ReadyTestFacadeExtended Constructor");
        this.id = id;
        this.teacherWriter = teacherWriter;
        this.dateCreated = dateCreated;
        this.time = time;
        this.isActive = isActive;
        this.isManual = isManual;
        this.code = code;
        this.courseName = courseName;
        this.timeToAdd = timeToAdd;
        this.timeExtensionReason = timeExtensionReason;
        this.timeExtensionRequestStatus = timeExtensionRequestStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeacherWriter() {
        return teacherWriter;
    }

    public void setTeacherWriter(String teacherWriter) {
        this.teacherWriter = teacherWriter;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Boolean getManual() {
        return isManual;
    }

    public void setManual(Boolean manual) {
        isManual = manual;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ReadyTestFacade{" +
                "id=" + id +
                ", teacherWriter='" + teacherWriter + '\'' +
                ", courseName='" + courseName + '\'' +
                ", dateCreated=" + dateCreated +
                ", time=" + time +
                ", isManual=" + isManual +
                ", isActive=" + isActive +
                ", code='" + code + '\'' +
                ", time to add= " + timeToAdd +
                ", reason= " + timeExtensionReason +
                ", status= " + timeExtensionRequestStatus +
                '}';
    }

    public int getTimeToAdd() {
        return timeToAdd;
    }

    public String getTimeExtensionReason() {
        return timeExtensionReason;
    }

    public Status getTimeExtensionRequestStatus() {
        return timeExtensionRequestStatus;
    }
}
