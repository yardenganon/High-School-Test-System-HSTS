package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClient;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Bundle;
import il.ac.haifa.cs.HSTS.server.CommandInterface.Response;
import il.ac.haifa.cs.HSTS.server.Entities.Test;
import il.ac.haifa.cs.HSTS.server.Entities.User;
import il.ac.haifa.cs.HSTS.server.Facade.TestFacade;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CheckAnswerableTestController {

    public User user;

    private Response responseFromServer = null;
    private static List<TestFacade> testList = null;
    private ObservableList<TestFacade> testsOL = null;
    private String subjectSelected = null;
    Test selectedTest = null;
    private Bundle bundle;

    private HSTSClient client;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField studentNameButton;

    @FXML
    private TextField startTimeButton;

    @FXML
    private TextField endTimeButton;

    @FXML
    private TableView<?> questionsTableView;

    @FXML
    private TableColumn<?, ?> columnQuestion;

    @FXML
    private TableColumn<?, ?> columnAnswer1;

    @FXML
    private TableColumn<?, ?> columnAnswer3;

    @FXML
    private TextField commentTextField;

    @FXML
    private Button confirmTestButton;

    @FXML
    private TextField gradeButton;

    @FXML
    void confirmTestRequest(ActionEvent event) {
        // לצרף ל-answrable את ההערה ואת הציון ואז לשלח לשרת
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = Bundle.getInstance();
        client = (HSTSClient) bundle.get("client");
        user = (User) bundle.get("user");
        System.out.println(user);
        client.getHstsClientInterface().addGUIController(this);
        initializeUser();
        initializeTestsTable();
    }



}
