package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClient;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Bundle;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.CustomProgressIndicator;
import il.ac.haifa.cs.HSTS.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.server.CommandInterface.ReadyTestReadByIdCommand;
import il.ac.haifa.cs.HSTS.server.CommandInterface.Response;
import il.ac.haifa.cs.HSTS.server.CommandInterface.TestsFacadeReadBySubjectCommand;
import il.ac.haifa.cs.HSTS.server.Entities.Question;
import il.ac.haifa.cs.HSTS.server.Entities.ReadyTest;
import il.ac.haifa.cs.HSTS.server.Entities.Test;
import il.ac.haifa.cs.HSTS.server.Entities.User;
import il.ac.haifa.cs.HSTS.server.Facade.TestFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TestFullDetailsController implements Initializable {

    public User user;
    public Test test;
    private ReadyTest readyTest;
    private Response responseFromServer = null;
    private ObservableList<QuestionOfTestTableView> questionsOL = null;
    Bundle bundle;

    private HSTSClient client;

    @FXML
    private Text authorLabel;

    @FXML
    private HBox codeHbox;

    @FXML
    private TextField codeTextField;


    @FXML
    private Text subjectOrCourseLabel;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField authorTextField;

    @FXML
    private TextField subjectTextField;

    @FXML
    private TextField dateCreatedTextField;

    @FXML
    private TextField introTextField;

    @FXML
    private TextField epliogueTextField;

    @FXML
    private TextField commentTextField;

    @FXML
    private TextField timeTextField;

    @FXML
    private TableView<QuestionOfTestTableView> questionsTableView;

    @FXML
    private TableColumn<QuestionOfTestTableView, String> columnId;

    @FXML
    private TableColumn<QuestionOfTestTableView, String> columnQuestion;

    @FXML
    private TableColumn<QuestionOfTestTableView, String> columnAnswer1;

    @FXML
    private TableColumn<QuestionOfTestTableView, String> columnAnswer2;

    @FXML
    private TableColumn<QuestionOfTestTableView, String> columnAnswer3;

    @FXML
    private TableColumn<QuestionOfTestTableView, String> columnAnswer4;

    @FXML
    private TableColumn<QuestionOfTestTableView, String> columnCorrect;

    @FXML
    private TableColumn<QuestionOfTestTableView, String> columnPoints;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bundle = Bundle.getInstance();
        client = (HSTSClient)bundle.get("client");
        //client.getHstsClientInterface().getGuiControllers().clear();
        client.getHstsClientInterface().addGUIController(this);
        user = (User) bundle.get("user");
        //System.out.println((boolean)bundle.get("readyTestFlag"));
        //System.out.println(bundle.get("readyTest"));
        if (bundle.get("readyTestFlag") != null && (boolean)bundle.get("readyTestFlag")) {
            initializeReadyTestDetails();
        }
        else{
            test = (Test) bundle.get("test");
            initializeTestDetails();
        }
    }

    public void initializeReadyTestDetails(){
        TimeExtensionRequestTableView tempTest = (TimeExtensionRequestTableView) bundle.get("readyTest");
        CustomProgressIndicator progressIndicator = new CustomProgressIndicator(anchorPane);
        progressIndicator.start();
        Task<Response> task = new Task<Response>() {
            @Override
            protected Response call() throws Exception {
                responseFromServer = null;
                System.out.println("my test is: " + tempTest.getTestId());
                CommandInterface command = new ReadyTestReadByIdCommand(tempTest.getTestId());
                client.getHstsClientInterface().sendCommandToServer(command);

                while (responseFromServer == null)
                    Thread.sleep(10);

                return responseFromServer;
            }
        };
        task.setOnSucceeded(e -> {
            responseFromServer = task.getValue();
            progressIndicator.stop();
            System.out.println("hello");
            readyTest = (ReadyTest) responseFromServer.getReturnedObject();
            authorLabel.setText("Executer");
            authorTextField.setText(readyTest.getModifierWriter().getUsername());
            idTextField.setText(String.valueOf(readyTest.getId()));
            subjectOrCourseLabel.setText("Course");
            subjectTextField.setText(readyTest.getCourse().getCourseName());
            dateCreatedTextField.setText(readyTest.getTest().getDateCreated().toString());
            introTextField.setText(readyTest.getTest().getIntroduction());
            epliogueTextField.setText(readyTest.getTest().getEpilogue());
            commentTextField.setText(readyTest.getTest().getCommentForTeachers());
            codeHbox.setVisible(true);
            codeTextField.setText(readyTest.getCode());
            timeTextField.setText(readyTest.getTest().getTime().toString());

            columnId.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("id"));
            columnQuestion.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("question"));
            columnAnswer1.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("answer1"));
            columnAnswer2.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("answer2"));
            columnAnswer3.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("answer3"));
            columnAnswer4.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("answer4"));
            columnCorrect.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("correctAnswer"));
            columnPoints.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("points"));

            questionsOL = FXCollections.observableArrayList();
            for (Question question : readyTest.getTest().getQuestionSet()) {
                questionsOL.add(new QuestionOfTestTableView(question.getId(), question.getQuestion(), question.getAnswer(1),
                        question.getAnswer(2), question.getAnswer(3), question.getAnswer(4),
                        question.getCorrectAnswer(), readyTest.getTest().getPoints().get(question).toString()));
            }
            questionsTableView.setItems(questionsOL);
            bundle.remove("readyTestFlag");
            bundle.remove("readyTest");
        });
        new Thread(task).start();
    }

    public void receivedResponseFromServer(Response response){
        responseFromServer = response;
        System.out.println("Command received in controller " + response);
    }

    public void initializeTestDetails()
    {
        authorTextField.setText(test.getWriter().getUsername());
        idTextField.setText(String.valueOf(test.getId()));
        authorLabel.setText("Author");
        subjectOrCourseLabel.setText("Subject");
        subjectTextField.setText(test.getSubject().getSubjectName());
        dateCreatedTextField.setText(test.getDateCreated().toString());
        introTextField.setText(test.getIntroduction());
        epliogueTextField.setText(test.getEpilogue());
        commentTextField.setText(test.getCommentForTeachers());
        timeTextField.setText(test.getTime().toString());
        codeHbox.setVisible(false);

        columnId.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("id"));
        columnQuestion.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("question"));
        columnAnswer1.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("answer1"));
        columnAnswer2.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("answer2"));
        columnAnswer3.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("answer3"));
        columnAnswer4.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("answer4"));
        columnCorrect.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("correctAnswer"));
        columnPoints.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("points"));

        questionsOL = FXCollections.observableArrayList();
        for (Question question : test.getQuestionSet()) {
            questionsOL.add(new QuestionOfTestTableView(question.getId(), question.getQuestion(), question.getAnswer(1),
                    question.getAnswer(2), question.getAnswer(3), question.getAnswer(4),
                    question.getCorrectAnswer(), test.getPoints().get(question).toString()));
        }
        questionsTableView.setItems(questionsOL);
    }

}
