package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClient;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Bundle;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.CustomProgressIndicator;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Events;
import il.ac.haifa.cs.HSTS.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.server.CommandInterface.Response;
import il.ac.haifa.cs.HSTS.server.CommandInterface.TestFacadeReadAllByTeacherCommand;
import il.ac.haifa.cs.HSTS.server.Entities.Principle;
import il.ac.haifa.cs.HSTS.server.Entities.Teacher;
import il.ac.haifa.cs.HSTS.server.Entities.User;
import il.ac.haifa.cs.HSTS.server.Facade.AnswerableTestFacade;
import il.ac.haifa.cs.HSTS.server.Facade.TestFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AnswerableTestsWrittenByTeacherController implements Initializable {

    public User user;
    private Response responseFromServer = null;
    private ObservableList<AnswerableTestFacade> testsOL = null;
    private Bundle bundle;
    private HSTSClient client;
    private Teacher teacher = null;
    private Principle principle = null;
    private ObservableList<AnswerableTestFacade> questionsOL = null;
    private List<TestFacade> testsFacadeList  = null;

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
    private TableColumn<AnswerableTestFacade, Integer> columnAnswerableTestId;

    @FXML
    private TableColumn<AnswerableTestFacade, String> columnStudentName;

    @FXML
    private TableColumn<AnswerableTestFacade, Integer> columnGrade;

    @FXML
    private Button showTestsButton;

    @FXML
    private ComboBox<String> testsComboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //CustomProgressIndicator progressIndicator = new CustomProgressIndicator(anchorPane);
        //progressIndicator.start();
        bundle = Bundle.getInstance();
        client = (HSTSClient) bundle.get("client");
        user = (User) bundle.get("user");
        client.getHstsClientInterface().addGUIController(this);
        if (user instanceof  Teacher) {
            teacher = (Teacher) user;
            InitAnswerableTestsWrittenByTeacher();
        }
    }


    private void InitAnswerableTestsWrittenByTeacher() {
        responseFromServer = null;

        CustomProgressIndicator progressIndicator = new CustomProgressIndicator(anchorPane);
        progressIndicator.start();

        Task<Response> task = new Task<Response>() {
            @Override
            protected Response call() throws Exception {

                // command for getting all answerable tests written by teacher
                CommandInterface command = new TestFacadeReadAllByTeacherCommand(teacher);
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

            testsFacadeList = (List<TestFacade>) responseFromServer.getReturnedObject();

            if (testsFacadeList != null) {
                for (TestFacade testFacade : testsFacadeList)
                    testsComboBox.getItems().add(testFacade.getSubject() + " " +
                            testFacade.getId() + " " + testFacade.getDateCreated());
            }

            progressIndicator.stop();
        });
        new Thread(task).start();
    }


    @FXML
    private void OnChangeSubject() {

        CustomProgressIndicator progressIndicator = new CustomProgressIndicator(anchorPane);
        progressIndicator.start();

        responseFromServer = null;

        Task<Response> task = new Task<Response>() {

            @Override
            protected Response call() throws Exception {

                String testName = testsComboBox.getSelectionModel().getSelectedItem();

                TestFacade selectedTestFacade = null;

                if (testsFacadeList  != null) {
                    for (TestFacade testFacade : testsFacadeList )
                        if (testFacade.getSubject() + " " + testFacade.getId() +
                                " " + testFacade.getDateCreated() == testName) {
                            selectedTestFacade = testFacade;
                            break;
                        }
                }

                // new command for getting all answerableTests
                //CommandInterface command = new AnswerableTestsFacadeReadByCourseCommand(selectedTestFacade);
                //client.getHstsClientInterface().sendCommandToServer(command);

                // Waiting for server confirmation
                while (responseFromServer == null) {
                    Thread.onSpinWait();
                }

                return responseFromServer;
            }
        };
        task.setOnSucceeded(e -> {
            responseFromServer = task.getValue();

            columnAnswerableTestId.setCellValueFactory(new PropertyValueFactory<AnswerableTestFacade, Integer>("answerableTestId"));
            columnStudentName.setCellValueFactory(new PropertyValueFactory<AnswerableTestFacade, String>("fullName"));
            columnGrade.setCellValueFactory(new PropertyValueFactory<AnswerableTestFacade, Integer>("score"));

            List<AnswerableTestFacade> answerableTestFacadeList = (List<AnswerableTestFacade>) responseFromServer.getReturnedObject();

            questionsOL = FXCollections.observableArrayList();

            if (answerableTestFacadeList.size() > 0) {
                for (AnswerableTestFacade answerableTestFacade : answerableTestFacadeList) {
                    questionsOL.add(new AnswerableTestFacade(answerableTestFacade.getAnswerableTestId(),
                            answerableTestFacade.getFirstName(), answerableTestFacade.getLastName(),
                            answerableTestFacade.getScore()));
                }
            }

            answerableTestsTableView.setItems(questionsOL);
            progressIndicator.stop();
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

    public void goToCourses(ActionEvent actionEvent) {}

    public void goToMenu(ActionEvent actionEvent) {
        Events.navigateMenuEvent(goToMenuButton);
    }

    public void goToQuestions(ActionEvent actionEvent) {
        Events.navigateQuestionsEvent(goToQuestionsButton);
    }

    public void OnShowTestsButtonPressed(ActionEvent actionEvent) throws IOException {
        AnswerableTestFacade selectedTest =  answerableTestsTableView.getSelectionModel().getSelectedItem();
        if (selectedTest == null)
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("For showing test you have to select test");
            errorAlert.showAndWait();
        }
        else
        {
            bundle.put("client", client);
            bundle.put("id", selectedTest.getAnswerableTestId());

            System.out.println(selectedTest + " Is selected");
            Scene scene = new Scene(MainClass.loadFXML("AnswerableTestDetails"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();
        }
    }
}
