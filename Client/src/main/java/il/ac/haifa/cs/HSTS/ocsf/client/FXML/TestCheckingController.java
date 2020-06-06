package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClient;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Bundle;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Events;
import il.ac.haifa.cs.HSTS.server.CommandInterface.Response;
import il.ac.haifa.cs.HSTS.server.Entities.AnswerableTest;
import il.ac.haifa.cs.HSTS.server.Entities.User;
import il.ac.haifa.cs.HSTS.server.Facade.TestFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TestCheckingController implements Initializable {

    public User user;
    private Response responseFromServer = null;
    private static List<TestFacade> testList = null;
    private ObservableList<TestFacade> testsOL = null;
    private Bundle bundle;
    private HSTSClient client;
    int sumOfTestsNeededToCheck = 0;
    private ObservableList<QuestionTableView> questionsOL = null;


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
    private Button aboutButton;

    @FXML
    private Button logoutButton;

    @FXML
    private VBox tableViewVbox;

    @FXML
    private TableView<?> TestsTableView;

    @FXML
    private TableColumn<?, ?> idColumn;

    @FXML
    private TableColumn<?, ?> courseColumn;

    @FXML
    private TableColumn<?, ?> studentNameColumn;

    @FXML
    private TableColumn<?, ?> gradeColumn;

    @FXML
    private Button editTestButton;

    @FXML
    private Button confirmTestButton;

    @FXML
    private Label numberOfTestsToCheckButton;

    @FXML
    void confirmTestRequest(ActionEvent event) {

    }

    @FXML
    void editTestRequest(ActionEvent event) throws IOException {

        // צריך לבדוק שבחרו משהו
        TestFacade selectedTest = TestsTableView.getSelectionModel().getSelectedItem();
        if (selectedTest != null) {
            System.out.println(selectedTest + " Is selected");
            bundle.put("id", selectedTest.getId());
            bundle.put("client", client);
            bundle.put("user", user);

            Scene scene = new Scene(MainClass.loadFXML("CheckAnswerableTest"));
            Stage stage = (Stage) editTestButton.getScene().getWindow();
            Stage secondaryStage = new Stage();
            secondaryStage.setScene(scene);
            secondaryStage.setTitle("Check Answerable Test");
            secondaryStage.initModality(Modality.APPLICATION_MODAL);
            secondaryStage.show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = Bundle.getInstance();
        user = (User) bundle.get("user");
        client = (HSTSClient) bundle.get("client");
        client.getHstsClientInterface().addGUIController(this);
        helloLabel.setText("Hello " + user.getFirst_name());
        initializeTestsTable();
    }


    public void receivedRespondFromServer(Response response) {
        responseFromServer = response;
        System.out.println("Command received in controller " + response);
    }

    @FXML
    void initializeTestsTable() {
        // insert all the tests to the table

        Task<Response> task = new Task<Response>() {
            @Override
            protected Response call() throws Exception {

                // Asking the test from server for showing question test
                //CommandInterface command = new getTestsToCheck(teacher);
                //client.getHstsClientInterface().sendCommandToServer(command);

                while (responseFromServer == null) {
                    Thread.sleep(10);
                }

                return responseFromServer;
            }
        };
        task.setOnSucceeded(e -> {

            List<AnswerableTest> = (AnswerableTest) responseFromServer.getReturnedObject();

            idColumn.setCellValueFactory(new PropertyValueFactory<UncheckedTestTable, String>("question"));
            courseColumn.setCellValueFactory(new PropertyValueFactory<UncheckedTestTable, String>("points"));
            studentNameColumn.setCellValueFactory(new PropertyValueFactory<UncheckedTestTable, String>("points"));
            gradeColumn.setCellValueFactory(new PropertyValueFactory<UncheckedTestTable, String>("points"));
//            subjectNameColumn.setCellValueFactory(new PropertyValueFactory<UncheckedTestTable, String>("points"));

            questionsOL = FXCollections.observableArrayList();

            for (AnswerableTest answerableTest : ) {
                sumOfTestsNeededToCheck++;
                questionsOL.add(new UncheckedTestTable(answerableTest.getQuestion(), String.valueOf(answerableTest.)));
            }
            TestsTableView.setItems(questionsOL);
        });
        new Thread(task).start();
    }

    public void refreshList() {
        sumOfTestsNeededToCheck--;
        numberOfTestsToCheckButton.setText(String.valueOf(sumOfTestsNeededToCheck));
        // remove the relevant cell from the table
    }

    @FXML
    void about(ActionEvent event) {
        Events.aboutWindowEvent();
    }

    @FXML
    void goToCourses(ActionEvent event) throws IOException {

    }

    @FXML
    void goToMenu(ActionEvent event) throws IOException {
        Events.navigateMenuEvent(goToMenuButton);
    }

    @FXML
    void goToQuestions(ActionEvent event) throws IOException {
        Events.navigateQuestionsEvent(goToQuestionsButton);
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        Events.navigateLogoutEvent(logoutButton);
    }
}
