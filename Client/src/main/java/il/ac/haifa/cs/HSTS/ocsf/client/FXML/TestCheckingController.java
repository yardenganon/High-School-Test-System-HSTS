package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClient;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Bundle;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.CustomProgressIndicator;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Events;
import il.ac.haifa.cs.HSTS.server.CommandInterface.*;
import il.ac.haifa.cs.HSTS.server.Entities.Student;
import il.ac.haifa.cs.HSTS.server.Entities.Teacher;
import il.ac.haifa.cs.HSTS.server.Entities.User;
import il.ac.haifa.cs.HSTS.server.Facade.AnswerableTestFacade;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TestCheckingController implements Initializable {

    public User user;
    public Text gpaLabel;
    public Label gpaResultLabel;
    private Student student;
    private Response responseFromServer = null;
    private static List<AnswerableTestFacade> testList = null;
    private ObservableList<UncheckedTestTable> testsOL = null;
    private Bundle bundle;
    private HSTSClient client;
    int sumOfTestsNeededToCheck = 0;
    double sumOfGrades = 0;
    private ObservableList<UncheckedTestTable> questionsOL = null;
    private Teacher teacher;
    private int checkedTestId;
    public boolean theTestWasChecked = false;
    UncheckedTestTable selectedTest;

    @FXML
    private Label titleLabel;

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
    private Text numberOfTestsLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private VBox tableViewVbox;

    @FXML
    private TableView<UncheckedTestTable> TestsTableView;

    @FXML
    private TableColumn<UncheckedTestTable, String> idColumn;

    @FXML
    private TableColumn<UncheckedTestTable, String> courseColumn;

    @FXML
    private TableColumn<UncheckedTestTable, String> studentNameColumn;

    @FXML
    private TableColumn<UncheckedTestTable, String> gradeColumn;

    @FXML
    private Button editTestButton;

    @FXML
    private Button confirmTestButton;

    @FXML
    private Label numberOfTestsToCheckButton;

    @FXML
    void confirmTestRequest(ActionEvent event) {

        selectedTest = TestsTableView.getSelectionModel().getSelectedItem();

        if (selectedTest != null) {
            checkedTestId = Integer.parseInt(selectedTest.getId());
            Task<Response> task = new Task<Response>() {
                @Override
                protected Response call() throws Exception {

                    // Asking to update that the test was checked
                    CommandInterface command = new AnswerableTestUpdateByIdCommand(checkedTestId, teacher);
                    client.getHstsClientInterface().sendCommandToServer(command);

                    while (responseFromServer == null) {
                        Thread.sleep(10);
                    }
                    return responseFromServer;
                }
            };
            task.setOnSucceeded(e -> {
                Alert successMessageAlert = new Alert(Alert.AlertType.INFORMATION);
                successMessageAlert.setHeaderText("Test checking was kept successfully");
                successMessageAlert.showAndWait();
                theTestWasChecked = true;
                refreshList();
            });
            new Thread(task).start();
        }
        else {
            Alert needChooseTestAlert = new Alert(Alert.AlertType.ERROR);
            needChooseTestAlert.setHeaderText("For test confirm you need to select a test and then push \"Confirm Test\" button");
            Optional<ButtonType> result = needChooseTestAlert.showAndWait();
        }
    }

    @FXML
    void editTestRequest(ActionEvent event) throws IOException {

        selectedTest = TestsTableView.getSelectionModel().getSelectedItem();

        if (selectedTest != null) {
            theTestWasChecked = false;
            checkedTestId = Integer.parseInt(selectedTest.getId());
            System.out.println(selectedTest + " Is selected");
            bundle.put("id", Integer.parseInt(selectedTest.getId()));
            bundle.put("client", client);
            bundle.put("user", user);

            Scene scene = new Scene(MainClass.loadFXML("CheckAnswerableTest"));
            Stage stage = (Stage) editTestButton.getScene().getWindow();
            Stage secondaryStage = new Stage();
            secondaryStage.setScene(scene);
            secondaryStage.setTitle("Check Answerable Test");
            secondaryStage.initModality(Modality.APPLICATION_MODAL);
            if (teacher != null) bundle.remove("ifTestWasChecked");
            secondaryStage.showAndWait();

            if (teacher != null && bundle.get("ifTestWasChecked") != null) {
                theTestWasChecked = (boolean) bundle.get("ifTestWasChecked");
                if (theTestWasChecked)
                    refreshList();
            }

//            if (student != null)
//                refreshList();
        }
        else
        {
            String output;
            if (student != null)
                output = "You need to select a test and then press \"Watch Test\" button";
            else
                output = "You need to select a test and then press \"Edit Test\" button";
            Alert needChooseTestAlert = new Alert(Alert.AlertType.ERROR);
            needChooseTestAlert.setHeaderText(output);
            Optional<ButtonType> result = needChooseTestAlert.showAndWait();
        }
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
            teacher = (Teacher) user;
            student = null;
        }
        if (user instanceof Student) {
            student = (Student) user;
            teacher = null;
        }
        initializeTestsTable();
    }

    public void receivedResponseFromServer(Response response) {
        responseFromServer = response;
        System.out.println("Command received in controller " + response);
    }

    @FXML
    void initializeTestsTable() {
        // insert all the tests to the table

        Task<Response> task = new Task<Response>() {
            @Override
            protected Response call() throws Exception {

                    // Asking unchecked tests of the teacher from server
                CommandInterface command;
                if (teacher != null) {
                    command = new AnswerableTestsFacadeReadCommand(teacher);
                    titleLabel.setText("Test Checking");
                    editTestButton.setText("Edit Test");
                    confirmTestButton.setVisible(true);
                    numberOfTestsLabel.setVisible(true);
                    numberOfTestsToCheckButton.setVisible(true);
                    gpaLabel.setVisible(false);
                    gpaResultLabel.setVisible(false);
                    studentNameColumn.setVisible(true);
                }
                else if (student != null) {
                    command = new AnswerableTestsFacadeReadByStudentCommand(student);
                    titleLabel.setText("My Tests");
                    editTestButton.setText("Watch Test");
                    confirmTestButton.setVisible(false);
                    numberOfTestsLabel.setVisible(false);
                    numberOfTestsToCheckButton.setVisible(false);
                    gpaLabel.setVisible(true);
                    gpaResultLabel.setVisible(true);
                    studentNameColumn.setVisible(false);
                }
                else
                {
                    command = null;
                }
                    client.getHstsClientInterface().sendCommandToServer(command);

                    while (responseFromServer == null) {
                        Thread.sleep(10);
                    }
                    return responseFromServer;
                }
        };
        task.setOnSucceeded(e -> {
            responseFromServer = task.getValue();
            List<AnswerableTestFacade> listOfAnswerableTestFacade = (List<AnswerableTestFacade>) responseFromServer.getReturnedObject();
            idColumn.setCellValueFactory(new PropertyValueFactory<UncheckedTestTable, String>("id"));
            courseColumn.setCellValueFactory(new PropertyValueFactory<UncheckedTestTable, String>("courseName"));
            if (teacher != null) studentNameColumn.setCellValueFactory(new PropertyValueFactory<UncheckedTestTable, String>("studentName"));
            gradeColumn.setCellValueFactory(new PropertyValueFactory<UncheckedTestTable, String>("grade"));

            questionsOL = FXCollections.observableArrayList();
            sumOfTestsNeededToCheck = 0;
            sumOfGrades = 0;

            for (AnswerableTestFacade answerableTestFacade : listOfAnswerableTestFacade) {
                sumOfTestsNeededToCheck++;
                System.out.println("Unchecked num " + sumOfTestsNeededToCheck + ":");
                System.out.println(String.valueOf(answerableTestFacade.getAnswerableTestId()) +
                        answerableTestFacade.getCourseName() + answerableTestFacade.getFirstName() + answerableTestFacade.getLastName() +
                        String.valueOf(answerableTestFacade.getScore()));

                if (student != null)
                    sumOfGrades += answerableTestFacade.getScore();

                questionsOL.add(new UncheckedTestTable(String.valueOf(answerableTestFacade.getAnswerableTestId()),
                        answerableTestFacade.getCourseName(), answerableTestFacade.getFirstName() + " " + answerableTestFacade.getLastName(),
                        String.valueOf(answerableTestFacade.getScore())));
            }
            TestsTableView.getItems().addAll(questionsOL);

            if (teacher != null)
                numberOfTestsToCheckButton.setText(String.valueOf(sumOfTestsNeededToCheck));
            else
                if (student != null)
                    gpaResultLabel.setText(String.format("%.2f", sumOfGrades / sumOfTestsNeededToCheck));
        });
        new Thread(task).start();
    }

    public void refreshList() {
        // Update the number of remaining tests to check
//        CustomProgressIndicator progressIndicator = new CustomProgressIndicator(anchorPane);
//        progressIndicator.start();

        // Remove checked test cell from the table
        if (theTestWasChecked && selectedTest != null) {
            TestsTableView.getItems().remove(selectedTest);
            selectedTest = null;
            sumOfTestsNeededToCheck--;
            numberOfTestsToCheckButton.setText(String.valueOf(sumOfTestsNeededToCheck));
        }

        //progressIndicator.stop();
        theTestWasChecked = false;
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
