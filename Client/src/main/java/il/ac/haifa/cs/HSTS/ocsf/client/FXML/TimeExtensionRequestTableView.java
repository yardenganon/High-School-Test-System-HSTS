package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.server.Entities.Teacher;
import il.ac.haifa.cs.HSTS.server.Status.Status;

public class TimeExtensionRequestTableView {

    private int testId;
    private String courseName;
    private String timeExtension;
    private String timeExtensionReason;
    private Status status;
    private String teacherUserName;
    private Boolean active;

    public TimeExtensionRequestTableView(int id, String courseName, Teacher teacher, String timeExtension, String timeExtensionReason, String status, Boolean active) {
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setTimeExtension(String timeExtension) {
        this.timeExtension = timeExtension;
    }

    public void setTimeExtensionReason(String timeExtensionReason) {
        this.timeExtensionReason = timeExtensionReason;
    }

    public void setTeacherUserName(String teacherUserName) {
        this.teacherUserName = teacherUserName;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getTeacherUserName() {
        return teacherUserName;
    }

    public Boolean getActive() {
        return active;
    }

    public int getTestId() {
        return testId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getTimeExtension() {
        return timeExtension;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public String getTimeExtensionReason() {
        return timeExtensionReason;
    }

    public TimeExtensionRequestTableView(int testId, String courseName, Teacher teacher, String timeExtension, String timeExtensionReason,Status status, boolean isActive)
    {
        this.testId = testId;
        this.courseName = courseName;
        if (timeExtension != null)
            this.timeExtension = timeExtension;
        else this.timeExtension = "";
        if (getTimeExtensionReason() != null)
            this.timeExtensionReason = timeExtensionReason;
        this.timeExtensionReason = "";
        this.active = isActive;
    }

    public TimeExtensionRequestTableView(String courseName, Teacher teacher, String timeExtension, String timeExtensionReason)
    {
        this.courseName = courseName;
        this.timeExtension = timeExtension;
        this.timeExtensionReason = timeExtensionReason;
        this.teacherUserName = teacher.getUsername();
    }
}