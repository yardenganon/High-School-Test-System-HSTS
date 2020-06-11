/*package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClient;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Bundle;
import il.ac.haifa.cs.HSTS.server.CommandInterface.Response;
import il.ac.haifa.cs.HSTS.server.Entities.Course;
import il.ac.haifa.cs.HSTS.server.Entities.Question;
import il.ac.haifa.cs.HSTS.server.Entities.Test;
import il.ac.haifa.cs.HSTS.server.Entities.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CoursesController implements Initializable {

    public User user;
    public Bundle bundle;
    public Course course;
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField teacherTextField;

    @FXML
    private TextField subjectTextField;

    @FXML
    private TextField averageTextField;

    @FXML
    private TextField stdTextField;

    @FXML
    private TableView<?> questionsTableView;

    @FXML
    private TableColumn<?, ?> idColumn;

    @FXML
    private TableColumn<?, ?> nameColumn;

    @FXML
    private TableColumn<?, ?> avgColumn;

    @FXML
    private TableColumn<?, ?> stdColumn;

    @FXML
    private BarChart<?, ?> studentsBarChart;

    @FXML
    private CategoryAxis gradesChart;

    @FXML
    private NumberAxis numberOfStudentsChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bundle = Bundle.getInstance();
        course = (Course) bundle.get("course");
        client = (HSTSClient)bundle.get("client");
        //client.getHstsClientInterface().getGuiControllers().clear();
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

}*/
