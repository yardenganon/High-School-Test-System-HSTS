package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.server.Entities.Teacher;
import javafx.scene.control.ComboBox;

public class TimeExtensionRequestTableView {

    private int testId;
    private String courseName;
    private String timeExtension;
    private String timeExtensionReason;
    private String status;
    private String teacherUserName;
    private ComboBox active;

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

    public TimeExtensionRequestTableView(int testId, String courseName, boolean isActive)
    {
        this.testId = testId;
        this.courseName = courseName;
        this.timeExtension = "";
        this.timeExtensionReason = "";
        this.status = "";
        active.getItems().clear();
        active.getItems().add("Yes");
        active.getItems().add("No");

        if (isActive == true)
            active.getSelectionModel().selectFirst();
        else active.getSelectionModel().select(2);
    }

    public TimeExtensionRequestTableView(String courseName, Teacher teacher, String timeExtension, String timeExtensionReason)
    {
        this.courseName = courseName;
        this.timeExtension = timeExtension;
        this.timeExtensionReason = "";
        this.teacherUserName = teacher.getUsername();
    }
}
