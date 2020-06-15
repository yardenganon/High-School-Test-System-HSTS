package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClient;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Bundle;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.CustomProgressIndicator;
import il.ac.haifa.cs.HSTS.server.CommandInterface.AnswerableTestReadCommand;
import il.ac.haifa.cs.HSTS.server.CommandInterface.AnswerableTestUpdateCommand;
import il.ac.haifa.cs.HSTS.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.server.CommandInterface.Response;
import il.ac.haifa.cs.HSTS.server.Entities.*;
import il.ac.haifa.cs.HSTS.server.Facade.TestFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.List;

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
    private HBox hboxOfTableView;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button openFileButton;

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
    private TableColumn<UncheckedAnswerableTest, String> columnId;

    @FXML
    private TableColumn<UncheckedAnswerableTest, String> columnQuestion, answer4Column , answer3Column,answer2Column,answer1Column;

    @FXML
    private TableColumn<UncheckedAnswerableTest, String> columnStudentAnswer;

    @FXML
    private TableColumn<UncheckedAnswerableTest, String> columnCorrectAnswer;

    @FXML
    private TextField commentTextField;

    @FXML
    private Button confirmTestButton;

    @FXML
    private TextField gradeButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = Bundle.getInstance();
        idTest = (int) bundle.get("id");
        client = (HSTSClient) bundle.get("client");
        user = (User) bundle.get("user");
        System.out.println(user);
        client.getHstsClientInterface().addGUIController(this);
        initializeAnswerableTest();
    }

    private void initializeAnswerableTest() {
        CustomProgressIndicator progressIndicator = new CustomProgressIndicator(anchorPane);
        progressIndicator.start();
        if (user instanceof Teacher) {
            commentTextField.setEditable(true);
            confirmTestButton.setVisible(true);
            gradeButton.setEditable(true);
        }
        if (user instanceof Student) {
            confirmTestButton.setVisible(false);
            commentTextField.setEditable(false);
            gradeButton.setEditable(false);
        }
        Task<Response> task = new Task<Response>() {
            @Override
            protected Response call() throws Exception {

                // Asking the full answerable test that was selected
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
                if (user instanceof Student)
                    commentTextField.setText(answerableTest.getTeacherComment());
                gradeButton.setText(String.valueOf(answerableTest.getScore()));
                idTextField.setText(String.valueOf(idTest));
                studentNameButton.setText(answerableTest.getStudent().getFirst_name() + " " +
                        answerableTest.getStudent().getLast_name());
                startTimeButton.setText(answerableTest.getTimeStarted().toString());
                endTimeButton.setText(answerableTest.getTimeFinished().toString());
                gradeButton.setText(String.valueOf(answerableTest.getScore()));
                if (answerableTest.getUrl() == null) {
                    initializeQuestionTable();
                    openFileButton.setVisible(false);
                    hboxOfTableView.setPrefHeight(258.0);
                    hboxOfTableView.setPrefWidth(692.0);
                }
                else{
                    hboxOfTableView.setVisible(true);
                    hboxOfTableView.setPrefHeight(0);
                    hboxOfTableView.setPrefWidth(0);
                    questionsTableView.setVisible(false);
                    openFileButton.setVisible(true);
                }
            progressIndicator.stop();
        });
        new Thread(task).start();
    }

    private void initializeQuestionTable() {
        Set<Question> questionSet = answerableTest.getQuestionsSet();
        Map<Question, Integer> answers = answerableTest.getAnswers();

        columnId.setCellValueFactory(new PropertyValueFactory<UncheckedAnswerableTest, String>("idQuestion"));
        columnQuestion.setCellValueFactory(new PropertyValueFactory<UncheckedAnswerableTest, String>("question"));
        columnStudentAnswer.setCellValueFactory(new PropertyValueFactory<UncheckedAnswerableTest, String>("studentAnswer"));
        columnCorrectAnswer.setCellValueFactory(new PropertyValueFactory<UncheckedAnswerableTest, String>("correctAnswer"));
        answer1Column.setCellValueFactory(new PropertyValueFactory<UncheckedAnswerableTest, String>("answer1"));
        answer2Column.setCellValueFactory(new PropertyValueFactory<UncheckedAnswerableTest, String>("answer2"));
        answer3Column.setCellValueFactory(new PropertyValueFactory<UncheckedAnswerableTest, String>("answer3"));
        answer4Column.setCellValueFactory(new PropertyValueFactory<UncheckedAnswerableTest, String>("answer4"));

        questionsOL = FXCollections.observableArrayList();
        for (Question question : questionSet)
        {
            questionsOL.add(new UncheckedAnswerableTest(String.valueOf(question.getId()), question.getQuestion(),
                    String.valueOf(answers.get(question)), String.valueOf(question.getCorrectAnswer()),question.getAnswer(1),
                    question.getAnswer(2),question.getAnswer(3),question.getAnswer(4)));
        }
        questionsTableView.setItems(questionsOL);
    }

    @FXML
    public void confirmTestRequest(javafx.event.ActionEvent actionEvent) {
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
                        answerableTest.setChecked(true);

                        // Asking to update that the test was checked
                        CommandInterface command = new AnswerableTestUpdateCommand(answerableTest);
                        client.getHstsClientInterface().sendCommandToServer(command);

                        while (responseFromServer == null) {
                            Thread.sleep(10);
                        }
                        return responseFromServer;
                    }
                };
                task.setOnSucceeded(e -> {
                    Alert successMessageAlert = new Alert(Alert.AlertType.INFORMATION);
                    successMessageAlert.setHeaderText("Success");
                    Optional<ButtonType> result = successMessageAlert.showAndWait();

                    // After the teacher presses OK, that screen will close

                    bundle.put("ifTestWasChecked", Boolean.TRUE);
                    final Stage stage = (Stage) confirmTestButton.getScene().getWindow();
                    stage.close();
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

    @FXML
    public void openFileRequest(ActionEvent actionEvent) {
        // Open file here
        try {
            //constructor of file class having file as argument
            System.out.println(answerableTest.getUrl());
            File file = new File(String.valueOf(answerableTest.getUrl()));
            System.out.println(file.getAbsolutePath());
            if (!Desktop.isDesktopSupported())//check if Desktop is supported by Platform or not
            {
                System.out.println("not supported");
                return;
            }
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(answerableTest.getUrl().toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
