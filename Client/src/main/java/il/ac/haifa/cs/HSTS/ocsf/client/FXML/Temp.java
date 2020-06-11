/**
 * Sample Skeleton for 'Menu.fxml' Controller Class
 */

package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClient;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Bundle;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Events;
import il.ac.haifa.cs.HSTS.server.Entities.Student;
import il.ac.haifa.cs.HSTS.server.Entities.Teacher;
import il.ac.haifa.cs.HSTS.server.Entities.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Temp implements Initializable {

    private HSTSClient client;
    public User user;
    Bundle bundle;

    @FXML
    private Label helloLabel;

    @FXML
    private Pane teacherPane;

    @FXML
    private Button coursesButton;

    @FXML
    private Button showQuestionButton;

    @FXML
    private Button goToTestsButton;

    @FXML
    private Button aboutButton;

    @FXML
    private AnchorPane studentMenu;

    @FXML
    private Button enterCodeButton;

    @FXML
    private Button logoutButton;
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button checkingTestsButton;

    @FXML
    private Button myTestButton;

    @FXML
    private Button myCoursesButton;

    @FXML
    void goToTests(ActionEvent event) throws IOException {
        Events.navigateTestsEvent(goToTestsButton);
    }

    @FXML
    void about(ActionEvent event) {
        Events.aboutWindowEvent();
    }

    @FXML
    void showQuestions(ActionEvent event) throws IOException {
        Events.navigateQuestionsEvent(showQuestionButton);
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        Events.navigateLogoutEvent(logoutButton);
    }


    @FXML
    void goToCheckingTests(ActionEvent event) {
        bundle.put("user", user);
        bundle.put("client", client);
        Events.navigateCheckingTestsEvent(checkingTestsButton);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = Bundle.getInstance();
        user = (User) bundle.get("user");
        client = (HSTSClient) bundle.get("client");
        client.getHstsClientInterface().getGuiControllers().clear();
        client.getHstsClientInterface().addGUIController(this);
        helloLabel.setText("Hello " + user.getFirst_name());
        if (user instanceof Teacher) {
            teacherPane.setVisible(true);
            myTestButton.setVisible(false);
        }

        if (user instanceof Student)
            initStudentMenu();
    }
    public void initStudentMenu() {
        studentMenu.setVisible(true);
        myTestButton.setVisible(true);
        enterCodeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            Scene scene = null;
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    scene = new Scene(MainClass.loadFXML("EnterExecutionCodePopup"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage menuStage = (Stage) helloLabel.getScene().getWindow();
                bundle.put("menuStage",menuStage);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.show();
            }
        });
    }

    @FXML
    void showMyCourses(ActionEvent event) {

    }

    @FXML
    void showMyTests(ActionEvent event) {
        Events.navigateCheckingTestsEvent(myTestButton);
    }
}
