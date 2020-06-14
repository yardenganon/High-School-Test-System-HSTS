package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClient;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Bundle;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Events;
import il.ac.haifa.cs.HSTS.server.CommandInterface.AnswerableTestsFacadeReadCommand;
import il.ac.haifa.cs.HSTS.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.server.CommandInterface.Response;
import il.ac.haifa.cs.HSTS.server.Entities.Teacher;
import il.ac.haifa.cs.HSTS.server.Entities.User;
import il.ac.haifa.cs.HSTS.server.Facade.AnswerableTestFacade;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TeacherAnswerableTestsController implements Initializable {

    public User user;
    private Response responseFromServer = null;
    private ObservableList<AnswerableTestFacade> testsOL = null;
    private Bundle bundle;
    private HSTSClient client;
    private Teacher teacher;
    private ObservableList<AnswerableTestFacade> questionsOL = null;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label helloLB;

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
    private Button aboutButton;

    @FXML
    private Button logoutButton;

    @FXML
    private TableView<AnswerableTestFacade> answerableTestsTableView;

    @FXML
    private TableColumn<AnswerableTestFacade, Integer> cloumnAswerableTestId;

    @FXML
    private TableColumn<AnswerableTestFacade, String> columnCourseName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = Bundle.getInstance();
        client = (HSTSClient) bundle.get("client");
        user = (User) bundle.get("user");
        System.out.println(user);
        client.getHstsClientInterface().addGUIController(this);
        teacher = (Teacher) user;
        initializeAnswerableTest();
    }

    private void initializeAnswerableTest() {
        responseFromServer = null;

        Task<Response> task = new Task<Response>() {
            @Override
            protected Response call() throws Exception {

                CommandInterface command = new AnswerableTestsFacadeReadCommand(teacher);
                client.getHstsClientInterface().sendCommandToServer(command);

                // Waiting for server confirmation
                while (responseFromServer == null) {
                    Thread.onSpinWait();
                }
                return responseFromServer;
            }
        };
        task.setOnSucceeded(e -> {
            responseFromServer = task.getValue();

            List<AnswerableTestFacade> answerableTestFacadeList = (List<AnswerableTestFacade>) responseFromServer.getReturnedObject();

            columnCourseName.setCellValueFactory(new PropertyValueFactory<AnswerableTestFacade, String>("courseName"));
            cloumnAswerableTestId.setCellValueFactory(new PropertyValueFactory<AnswerableTestFacade, Integer>("answerableTestId"));

            answerableTestsTableView.getItems().addAll(answerableTestFacadeList);

        });
        new Thread(task).start();

    }

    public void receivedResponseFromServer(Response response) {
        responseFromServer = response;
        System.out.println("Command received in controller " + response);
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
    void logout(ActionEvent event) throws IOException {
        Events.navigateLogoutEvent(logoutButton);
    }

    public void goToCourses(ActionEvent actionEvent) {
    }

    public void goToMenu(ActionEvent actionEvent) {
        Events.navigateMenuEvent(goToMenuButton);
    }

    public void goToQuestions(ActionEvent actionEvent) {

    }

    public void onMousePressedTableView(MouseEvent mouseEvent) {
        // לפתוח popup
    }
}
