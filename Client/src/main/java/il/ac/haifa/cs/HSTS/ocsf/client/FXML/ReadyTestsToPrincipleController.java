package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClient;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Bundle;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Events;
import il.ac.haifa.cs.HSTS.server.CommandInterface.Response;
import il.ac.haifa.cs.HSTS.server.Entities.Test;
import il.ac.haifa.cs.HSTS.server.Entities.User;
import il.ac.haifa.cs.HSTS.server.Facade.TestFacade;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.apache.poi.ss.formula.functions.Even;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ReadyTestsToPrincipleController implements Initializable {

    public User user;
    private Response responseFromServer = null;
    private Bundle bundle;

    private HSTSClient client;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label helloLabel;

    @FXML
    private Button goToMenuButton;

    @FXML
    private Button goToCoursesButton;

    @FXML
    private Button goToQuestionsButton;

    @FXML
    private Button goToTestsButton;

    @FXML
    private Button logoutButton;

    @FXML
    private VBox tableViewVbox;

    @FXML
    private TableView<?> TestsTableView;

    @FXML
    private TableColumn<?, ?> columnId;

    @FXML
    private TableColumn<?, ?> columnAuthor;

    @FXML
    private TableColumn<?, ?> columnCourse;

    @FXML
    private TableColumn<?, ?> columnCreationDate;

    @FXML
    private TableColumn<?, ?> columnQuestionsNumber;

    @FXML
    private TableColumn<?, ?> columnTime;

    @FXML
    private ComboBox<?> coursesComboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = Bundle.getInstance();
        client = (HSTSClient) bundle.get("client");
        user = (User) bundle.get("user");
        System.out.println(user);
        client.getHstsClientInterface().getGuiControllers().clear();
        client.getHstsClientInterface().addGUIController(this);
        initializeUser();
//        initializeSubjectsComboBox();
//        initializeTestsTable();
    }

    public void initializeUser() {
        helloLabel.setText("Hello " + user.getFirst_name());
    }


    @FXML
    void goToCourses(ActionEvent event) {

    }

    @FXML
    void goToMenu(ActionEvent event) {
        Events.navigateMenuEvent(goToMenuButton);
    }

    @FXML
    void goToTests(ActionEvent event) throws IOException {
        Events.navigateTestsEvent(goToTestsButton);
    }

    @FXML
    void about(ActionEvent event) {
        Events.aboutWindowEvent();
    }

    @FXML
    void goToQuestions(ActionEvent event) throws IOException {
        Events.navigateQuestionsEvent(goToQuestionsButton);
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        Events.navigateLogoutEvent(logoutButton);
    }

    @FXML
    void subjectSelect(ActionEvent event) {

    }

}
