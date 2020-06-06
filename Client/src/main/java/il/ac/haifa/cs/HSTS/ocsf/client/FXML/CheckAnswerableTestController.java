package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClient;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Bundle;
import il.ac.haifa.cs.HSTS.server.CommandInterface.AnswerableTestReadCommand;
import il.ac.haifa.cs.HSTS.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.server.CommandInterface.Response;
import il.ac.haifa.cs.HSTS.server.Entities.AnswerableTest;
import il.ac.haifa.cs.HSTS.server.Entities.Question;
import il.ac.haifa.cs.HSTS.server.Entities.User;
import il.ac.haifa.cs.HSTS.server.Facade.TestFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.*;

public class CheckAnswerableTestController implements Initializable {

    public User user;
    private Response responseFromServer = null;
    private static List<TestFacade> testList = null;
    private ObservableList<TestFacade> testsOL = null;
    private String subjectSelected = null;
    private Bundle bundle;
    private int idTest;
    private HSTSClient client;
    private AnswerableTest answerableTest;
    private ObservableList<UncheckedAnswerableTest> questionsOL = null;

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
    private TableView<UncheckedAnswerableTest> questionsTableView;

    @FXML
    private TableColumn<TestFacade, String> idColumn;

    @FXML
    private TableColumn<TestFacade, String> courseColumn;

    @FXML
    private TableColumn<TestFacade, String> studentNameColumn;

    @FXML
    private TableColumn<TestFacade, String> gradeColumn;

    @FXML
    private TextField commentTextField;

    @FXML
    private Button confirmTestButton;

    @FXML
    private TextField gradeButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = Bundle.getInstance();
        //idTest = (int) bundle.get("id");
        client = (HSTSClient) bundle.get("client");
        user = (User) bundle.get("user");
        System.out.println(user);
        client.getHstsClientInterface().addGUIController(this);
        initializeAnswerableTest();
    }

    private void initializeAnswerableTest() {
        Task<Response> task = new Task<Response>() {
            @Override
            protected Response call() throws Exception {

                // Asking the full answerable test that was selected
                idTest = 2;
                CommandInterface command = new AnswerableTestReadCommand(idTest);
                client.getHstsClientInterface().sendCommandToServer(command);
                while (responseFromServer == null) {
                    Thread.sleep(10);
                }
                return responseFromServer;
            }
        };
        task.setOnSucceeded(e -> {

                answerableTest = (AnswerableTest) responseFromServer.getReturnedObject();

                idTextField.setText(String.valueOf(idTest));
                studentNameButton.setText(answerableTest.getStudent().getFirst_name() + " " +
                        answerableTest.getStudent().getLast_name());
                startTimeButton.setText(answerableTest.getTimeStarted().toString());
                endTimeButton.setText(answerableTest.getTimeFinished().toString());
                gradeButton.setText(String.valueOf(answerableTest.getScore()));
                initializeQuestionTable();
        });
        new Thread(task).start();
    }

    private void initializeQuestionTable() {
        Set<Question> questionSet = answerableTest.getQuestionsSet();
        Map<Question, Integer> answers = answerableTest.getAnswers();

        questionsOL = FXCollections.observableArrayList();
        for (Question question : questionSet)
        {
            questionsOL.add(new UncheckedAnswerableTest(String.valueOf(question.getId()), question.getQuestion(),
                    String.valueOf(answers.get(question)), String.valueOf(question.getCorrectAnswer())));
        }
        questionsTableView.setItems(questionsOL);
    }

    @FXML
    void confirmTestRequest(ActionEvent event) {
        boolean inputError = false;

        if (gradeButton.getText().isEmpty()) {
            gradeButton.setText("Invalid input");
            gradeButton.setStyle("-fx-text-inner-color: #ff0000;");
        } else {
            for (int i = 0; i < gradeButton.getText().length(); i++) {
                if (gradeButton.getText().charAt(i) < '0' || gradeButton.getText().charAt(i) > '9') {
                    gradeButton.setText("Invalid input");
                    gradeButton.setStyle("-fx-text-inner-color: #ff0000;");
                    inputError = true;
                    break;
                }
            }
            if (!inputError) {
                Task<Response> task = new Task<Response>() {
                    @Override
                    protected Response call() throws Exception {
                        answerableTest.setTeacherComment(commentTextField.getText());
                        answerableTest.setScore(Integer.parseInt(gradeButton.getText()));

                        // Asking to update that the test was checked
                        //CommandInterface command = new updateAnswerableTest(answerableTest, teacher);
                        //client.getHstsClientInterface().sendCommandToServer(command);

                        while (responseFromServer == null) {
                            Thread.sleep(10);
                        }
                        return responseFromServer;
                    }
                };
                task.setOnSucceeded(e -> {
                    Alert successMessageAlert = new Alert(Alert.AlertType.INFORMATION);
                    successMessageAlert.setHeaderText("The test was kept successfully");
                    Optional<ButtonType> result = successMessageAlert.showAndWait();

                    // After the teacher presses OK, that screen will close
                    if (result.isPresent() && result.get() != ButtonType.OK) {
                        bundle.put("ifTestWasChecked", true);
                        final Stage stage = (Stage) confirmTestButton.getScene().getWindow();
                        stage.close();
                    }
                });
                new Thread(task).start();
            }
        }
    }

    @FXML
    void gradeOnClick(MouseEvent event) {
        if (gradeButton.getText().equals("Invalid input"))
        {
            gradeButton.setText("");
            gradeButton.setStyle("-fx-text-inner-color: #000000;");
        }
    }

    public void receivedResponseFromServer(Response response) {
        responseFromServer = response;
        System.out.println("Command received in controller " + response);
    }

    public void confirmTestRequest(javafx.event.ActionEvent actionEvent) {
    }
}