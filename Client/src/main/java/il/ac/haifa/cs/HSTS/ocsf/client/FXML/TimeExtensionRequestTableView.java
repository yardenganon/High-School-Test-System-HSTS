package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.server.Entities.Teacher;

public class TimeExtensionRequestTableView {

    private int testId;
    private String courseName;
    private String timeExtension;
    private String timeExtensionReason;
    private String status;
    private String teacherUserName;

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

    public TimeExtensionRequestTableView(int testId, String courseName)
    {
        this.testId = testId;
        this.courseName = courseName;
        this.timeExtension = "";
        this.timeExtensionReason = "";
        this.status = "";
    }

    public TimeExtensionRequestTableView(String courseName, Teacher teacher, String timeExtension, String timeExtensionReason)
    {
        this.courseName = courseName;
        this.timeExtension = timeExtension;
        this.timeExtensionReason = "";
        this.teacherUserName = teacher.getUsername();
    }
}
