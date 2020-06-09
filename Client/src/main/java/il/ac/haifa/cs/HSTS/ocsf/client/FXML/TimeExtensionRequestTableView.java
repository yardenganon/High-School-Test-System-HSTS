package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.server.Entities.Teacher;

public class TimeExtensionRequestTableView {

    private int testId;
    private String courseName;
    private String timeExtension;
    private String timeExtensionReason;
    private String status;
    private String teacherUserName;
    private String active;

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

    public void setActive(String active) {
        this.active = active;
    }

    public String getTeacherUserName() {
        return teacherUserName;
    }

    public String getActive() {
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

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getTimeExtensionReason() {
        return timeExtensionReason;
    }

    public TimeExtensionRequestTableView(int testId, String courseName, String timeExtension, String timeExtensionReason, String status,  boolean isActive)
    {
        this.testId = testId;
        this.courseName = courseName;
        if (timeExtension != null)
            this.timeExtension = timeExtension;
        else this.timeExtension = "";
        if (getTimeExtensionReason() != null)
            this.timeExtensionReason = timeExtensionReason;
        this.timeExtensionReason = "";
        if (status != null)
            this.status = status;
        else
            this.status = "";
        if (isActive == Boolean.TRUE)
            this.active = "YES";
        else this.active = "NO";
    }

    public TimeExtensionRequestTableView(String courseName, Teacher teacher, String timeExtension, String timeExtensionReason)
    {
        this.courseName = courseName;
        this.timeExtension = timeExtension;
        this.timeExtensionReason = timeExtensionReason;
        this.teacherUserName = teacher.getUsername();
    }
}

timeExtension;
private String timeExtensionReason;
private String status;