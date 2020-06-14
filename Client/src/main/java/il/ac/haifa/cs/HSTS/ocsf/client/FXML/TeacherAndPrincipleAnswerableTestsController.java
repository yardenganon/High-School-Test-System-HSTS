package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClient;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Bundle;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Events;
import il.ac.haifa.cs.HSTS.server.CommandInterface.AnswerableTestsFacadeReadCommand;
import il.ac.haifa.cs.HSTS.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.server.CommandInterface.Response;
import il.ac.haifa.cs.HSTS.server.Entities.Course;
import il.ac.haifa.cs.HSTS.server.Entities.Principle;
import il.ac.haifa.cs.HSTS.server.Entities.Teacher;
import il.ac.haifa.cs.HSTS.server.Entities.User;
import il.ac.haifa.cs.HSTS.server.Facade.AnswerableTestFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class TeacherAndPrincipleAnswerableTestsController implements Initializable {

    public User user;
    private Response responseFromServer = null;
    private ObservableList<AnswerableTestFacade> testsOL = null;
    private Bundle bundle;
    private HSTSClient client;
    private Teacher teacher = null;
    private Principle principle = null;
    private ObservableList<AnswerableTestFacade> questionsOL = null;
    private List<CourseFacade> coursesListOfPrinciple;
    private Set<Course> coursesListOfTeacher;

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
    private ComboBox<String> coursesComboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = Bundle.getInstance();
        client = (HSTSClient) bundle.get("client");
        user = (User) bundle.get("user");
        client.getHstsClientInterface().addGUIController(this);
        if (user instanceof  Teacher)
            teacher = (Teacher) user;
        else if (user instanceof Principle)
            principle = (Principle) user;
        initializeComboBox();
    }

    private void initializeComboBox() {

        coursesComboBox.getItems().clear();
        if (teacher != null) {
            for (Course course : teacher.getCourses())
            {
                coursesComboBox.getItems().add(course.getCourseName());
            }
            coursesListOfTeacher = teacher.getCourses();
        }
        else if (principle != null)
        {
            InitPrincipleCourses();
        }
        coursesComboBox.getSelectionModel().selectFirst();
    }

    private void InitPrincipleCourses() {
        responseFromServer = null;

        Task<Response> task = new Task<Response>() {
            @Override
            protected Response call() throws Exception {

                // command for getting all courses
                CommandInterface command = new AnswerableTestsFacadeReadCommand();
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

            coursesListOfPrinciple = (List<CourseFacade>) responseFromServer.getReturnedObject();

            for (CourseFacade courseFacade : coursesListOfPrinciple)
                coursesComboBox.getItems().add(courseFacade.getCourseName());

        });
        new Thread(task).start();
    }


    @FXML
    private void OnChangeSubject() {
        responseFromServer = null;

        Task<Response> task = new Task<Response>() {
            @Override
            protected Response call() throws Exception {
                String courseName = coursesComboBox.getSelectionModel().getSelectedItem();

                // לחפש את הקורס ברשימה
                for ()
                // לשנות את זה לcommand חדש שמקבל את המבחנים

                CommandInterface command = new AnswerableTestsFacadeReadCommand();
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

            List<CourseFacade> answerableTestFacadeList = (List<AnswerableTestFacade>) responseFromServer.getReturnedObject();

            columnAnswerableTestId.setCellValueFactory(new PropertyValueFactory<AnswerableTestFacade, Integer>("answerableTestId"));
            columnStudentName.setCellValueFactory(new PropertyValueFactory<AnswerableTestFacade, String>("firstName + lastName"));
            columnGrade.setCellValueFactory(new PropertyValueFactory<AnswerableTestFacade, Integer>("score"));

            questionsOL = FXCollections.observableArrayList();
            for (AnswerableTestFacade answerableTestFacade : answerableTestFacadeList)
                questionsOL.add(new AnswerableTestFacade(answerableTestFacade.getAnswerableTestId(),
                        answerableTestFacade.getFirstName(), answerableTestFacade.getLastName(),
                        answerableTestFacade.getScore()));

            answerableTestsTableView.setItems(questionsOL);
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

    }

    public void OnShowTestsButtonPressed(ActionEvent actionEvent) {
        AnswerableTestFacade selectedTest =  answerableTestsTableView.getSelectionModel().getSelectedItem();
        if (selectedTest == null)
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("For showing test you have to select test");
            errorAlert.showAndWait();
        }
        else
        {
            bundle.put("Client", client);
            bundle.put("Id", selectedTest.getAnswerableTestId());

            // להקפיץ פופאפ
        }
    }
}
