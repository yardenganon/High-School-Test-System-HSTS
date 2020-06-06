package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClient;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Bundle;
import il.ac.haifa.cs.HSTS.server.CommandInterface.Response;
import il.ac.haifa.cs.HSTS.server.Entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class TestFullDetailsController implements Initializable {

    public User user;
    public Test test;
    private Response responseFromServer = null;
    private ObservableList<QuestionOfTestTableView> questionsOL = null;
    Bundle bundle;

    private HSTSClient client;

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
        test = (Test) bundle.get("test");
        client = (HSTSClient)bundle.get("client");
        client.getHstsClientInterface().getGuiControllers().clear();
        client.getHstsClientInterface().addGUIController(this);
        user = (User) bundle.get("user");
        initializeTestDetails();
    }

    public void receivedResponseFromServer(Response response){
        responseFromServer = response;
        System.out.println("Command received in controller " + response);
    }
    public void initializeTestDetails()
    {
        authorTextField.setText(test.getWriter().getUsername());
        idTextField.setText(String.valueOf(test.getId()));
        subjectTextField.setText(test.getSubject().getSubjectName());
        dateCreatedTextField.setText(test.getDateCreated().toString());
        introTextField.setText(test.getIntroduction());
        epliogueTextField.setText(test.getEpilogue());
        commentTextField.setText(test.getCommentForTeachers());
        timeTextField.setText(test.getTime().toString());

        columnQuestion.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("question"));
        columnAnswer1.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("answer1"));
        columnAnswer2.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("answer2"));
        columnAnswer3.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("answer3"));
        columnAnswer4.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("answer4"));
        columnCorrect.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("correctAnswer"));
        columnPoints.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("points"));

        questionsOL = FXCollections.observableArrayList();
        for (Question question : test.getQuestionSet()) {
            questionsOL.add(new QuestionOfTestTableView(question.getQuestion(), question.getAnswer(1),
                    question.getAnswer(2), question.getAnswer(3), question.getAnswer(4),
                    question.getCorrectAnswer(), test.getPoints().get(question)));
        }
        questionsTableView.setItems(questionsOL);
    }

}
