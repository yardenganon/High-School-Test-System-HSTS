package il.ac.haifa.cs.HSTS.server.Facade;

import java.io.Serializable;
import java.util.Date;

public class ReadyTestFacade implements Serializable {
    int id;
    String teacherWriter;
    String courseName;
    Date dateCreated;
    int time;
    private Boolean isManual;
    private Boolean isActive;
    private String code;

    public ReadyTestFacade(){}

    public ReadyTestFacade(int id, String teacherWriter, String courseName, Date dateCreated,
                           int time, Boolean isManual, Boolean isActive, String code) {
        System.out.println("in Facade Constructor");
        this.id = id;
        this.teacherWriter = teacherWriter;
        this.dateCreated = dateCreated;
        this.time = time;
        this.isActive = isActive;
        this.isManual = isManual;
        this.code = code;
        this.courseName = courseName;
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
                '}';
    }
}
