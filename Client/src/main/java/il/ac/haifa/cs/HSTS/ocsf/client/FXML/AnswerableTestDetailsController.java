package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClient;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Bundle;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.CustomProgressIndicator;
import il.ac.haifa.cs.HSTS.server.CommandInterface.AnswerableTestReadCommand;
import il.ac.haifa.cs.HSTS.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.server.CommandInterface.Response;
import il.ac.haifa.cs.HSTS.server.Entities.AnswerableTest;
import il.ac.haifa.cs.HSTS.server.Entities.Question;
import il.ac.haifa.cs.HSTS.server.Facade.AnswerableTestFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class AnswerableTestDetailsController implements Initializable {

    private Response responseFromServer = null;
    private AnswerableTest answerableTest = null;
    private ObservableList<AnswerableTestFacade> testsOL = null;
    private Bundle bundle;
    private HSTSClient client;
    private int testId;
    private ObservableList<UncheckedAnswerableTest> questionsOL = null;

    @FXML
    private Button openFileButton;

    @FXML
    private HBox hboxOfTableView;

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
    private TableColumn<UncheckedAnswerableTest, String> columnId;

    @FXML
    private TableColumn<UncheckedAnswerableTest, String> columnQuestion;

    @FXML
    private TableColumn<UncheckedAnswerableTest, String> answer1Column;

    @FXML
    private TableColumn<UncheckedAnswerableTest, String> answer2Column;

    @FXML
    private TableColumn<UncheckedAnswerableTest, String> answer3Column;

    @FXML
    private TableColumn<UncheckedAnswerableTest, String> answer4Column;

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
        client = (HSTSClient) bundle.get("client");
        testId = (Integer)bundle.get("id");
        client.getHstsClientInterface().addGUIController(this);

        getAnswerableTest();
    }

    private void getAnswerableTest() {
        responseFromServer = null;

        CustomProgressIndicator progressIndicator = new CustomProgressIndicator(anchorPane);
        progressIndicator.start();

        Task<Response> task = new Task<Response>() {
            @Override
            protected Response call() throws Exception {


                // command for getting the answerable test
                CommandInterface command = new AnswerableTestReadCommand(testId);
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

            answerableTest = (AnswerableTest)responseFromServer.getReturnedObject();

            // Setting test details
            idTextField.setText(String.valueOf(answerableTest.getId()));
            studentNameButton.setText(answerableTest.getStudent().getFirst_name()
                    + " " + answerableTest.getStudent().getLast_name());
            if (answerableTest.getTimeStarted() != null)
                startTimeButton.setText(answerableTest.getTimeStarted().toString());
            if (answerableTest.getTimeFinished() != null)
                endTimeButton.setText(answerableTest.getTimeFinished().toString());
            commentTextField.setText(answerableTest.getTeacherComment());
            gradeButton.setText(String.valueOf(answerableTest.getScore()));

            if (answerableTest.getUrl() == null) {
                initializeQuestionTable(answerableTest);
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

    private void initializeQuestionTable(AnswerableTest answerableTest) {
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
        for (Question question : questionSet) {
            questionsOL.add(new UncheckedAnswerableTest(String.valueOf(question.getId()), question.getQuestion(),
                    String.valueOf(answers.get(question)), String.valueOf(question.getCorrectAnswer()), question.getAnswer(1),
                    question.getAnswer(2), question.getAnswer(3), question.getAnswer(4)));
        }
        System.out.println("The answers are :" + answerableTest.getAnswers());
        questionsTableView.setItems(questionsOL);
        questionsTableView.getSortOrder().add(columnId);
    }

    public void receivedResponseFromServer(Response response) {
        responseFromServer = response;
        System.out.println("Command received in controller " + response);
    }

    @FXML
    public void openFileRequest(ActionEvent actionEvent) {
        // Open file here
        try {

            if (answerableTest.getUrl() == null) {
                Alert successMessageAlert = new Alert(Alert.AlertType.ERROR);
                successMessageAlert.setHeaderText("The test has not been uploaded yet");
                successMessageAlert.showAndWait();
            } else {
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
            }
            } catch(Exception e){
                e.printStackTrace();
            }
    }
}
