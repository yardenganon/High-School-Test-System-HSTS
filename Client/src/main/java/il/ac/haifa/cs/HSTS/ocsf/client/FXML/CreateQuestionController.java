package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClient;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Bundle;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.CustomProgressIndicator;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Events;
import il.ac.haifa.cs.HSTS.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.server.CommandInterface.QuestionUpdateCommand;
import il.ac.haifa.cs.HSTS.server.CommandInterface.Response;
import il.ac.haifa.cs.HSTS.server.Entities.Question;
import il.ac.haifa.cs.HSTS.server.Entities.Subject;
import il.ac.haifa.cs.HSTS.server.Entities.Teacher;
import il.ac.haifa.cs.HSTS.server.Entities.User;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CreateQuestionController implements Initializable {

    public User user;
    public Question question;
    private Response responseFromServer = null;
    private static boolean thereIsAnError = false;
    Bundle bundle;
    private HSTSClient client;


    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField questionTextField;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField authorTextField;

    @FXML
    private ComboBox<String> subjectComboBox;

    @FXML
    private TextField answer1TextField;

    @FXML
    private TextField answer2TextField;

    @FXML
    private TextField answer3TextField;

    @FXML
    private TextField answer4TextField;

    @FXML
    private ComboBox<String> correctAnswerComboBox;

    @FXML
    private Button resetQuestionButton;

    @FXML
    private Button createQuestionButton;

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

    private CustomProgressIndicator progressIndicator;

    @FXML
    void CreateQuestion(ActionEvent event) throws InterruptedException {

        Alert updateQuestionAlert = new Alert(Alert.AlertType.CONFIRMATION);
        updateQuestionAlert.setHeaderText("Are you sure you want to create that question?");
        Optional<ButtonType> result = updateQuestionAlert.showAndWait();

        /* If the teacher clicks OK, question details will be sent to the server after input checking */
        if (result.isPresent() && result.get() == ButtonType.OK)
        {
            // Input checking of TextField
            if (questionTextField.getText().isEmpty())
                inputErrorTextField(questionTextField);
            if (answer1TextField.getText().isEmpty())
                inputErrorTextField(answer1TextField);
            if (answer2TextField.getText().isEmpty())
                inputErrorTextField(answer2TextField);
            if (answer3TextField.getText().isEmpty())
                inputErrorTextField(answer3TextField);
            if (answer4TextField.getText().isEmpty())
                inputErrorTextField(answer4TextField);

            // Input checking of combobox
            if (correctAnswerComboBox.getSelectionModel().isEmpty() && subjectComboBox.getSelectionModel().isEmpty())
                inputErrorComboBox(("Correct Answer and subject"));
            else if (correctAnswerComboBox.getSelectionModel().isEmpty())
                inputErrorComboBox(("Correct Answer"));
            else if (subjectComboBox.getSelectionModel().isEmpty())
                inputErrorComboBox(("subject"));

            if (!thereIsAnError) {
                // If there are no input errors, request for creating question will be sent to the server
                question.setQuestion(questionTextField.getText());
                question.setAnswer(1, answer1TextField.getText());
                question.setAnswer(2, answer2TextField.getText());
                question.setAnswer(3, answer3TextField.getText());
                question.setAnswer(4, answer4TextField.getText());
                question.setCorrectAnswer(Integer.parseInt(correctAnswerComboBox.getSelectionModel().getSelectedItem()));

                // Add the selected subject to question details
                if (user instanceof Teacher) {
                    Teacher teacher = ((Teacher) user);
                    List<Subject> listOfSubject = teacher.getSubjects();
                    for (Subject subject : teacher.getSubjects())
                    {
                        if (subject.getSubjectName() == subjectComboBox.getSelectionModel().getSelectedItem())
                        {
                            question.setSubject(subject);
                            break;
                        }
                    }
                }

                progressIndicator = new CustomProgressIndicator(anchorPane);
                progressIndicator.start();

                responseFromServer = null;
                Task<Response> task = new Task<Response>() {
                    @Override
                    protected Response call() throws Exception {
                        CommandInterface command = new QuestionUpdateCommand(question);
                        client.getHstsClientInterface().sendCommandToServer(command);
                        // Waiting for server confirmation
                        while (responseFromServer == null) {
                            Thread.sleep(10);
                        }
                        return responseFromServer;
                    }
                };
                task.setOnSucceeded(e -> {
                    progressIndicator.stop();
                    question = (Question) responseFromServer.getReturnedObject();
                    initializeQuestionDetails();
                    anchorPane.setDisable(false);
                    Alert updateSuccessAlert = new Alert(Alert.AlertType.INFORMATION);
                    updateSuccessAlert.setHeaderText("The question was successfully created");
                    updateSuccessAlert.showAndWait();
                });
                new Thread(task).start();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bundle = Bundle.getInstance();
        question = (Question) bundle.get("question");
        client = (HSTSClient)bundle.get("client");
        client.getHstsClientInterface().addGUIController(this);
        user = (User) bundle.get("user");
        initializeQuestionDetails();
    }

    @FXML
    void ResetQuestion(ActionEvent event) {
        resetFields();
    }

    @FXML
    void about(ActionEvent event) {
        Events.aboutWindowEvent();
    }

    @FXML
    void goToCourses(ActionEvent event) {

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
    void goToTests(ActionEvent event) {

    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        Events.navigateLogoutEvent(logoutButton);
    }

    public void receivedResponseFromServer(Response response){
        responseFromServer = response;
        System.out.println("Command received in controller " + response);
    }

    public void initializeQuestionDetails()
    {
        if (user instanceof Teacher) {
            Teacher teacher = ((Teacher) user);
            subjectComboBox.getItems().clear();
            for (Subject subject : teacher.getSubjects())
                subjectComboBox.getItems().add(subject.getSubjectName());

            authorTextField.setText(teacher.getFirst_name() + " " + teacher.getLast_name());
            //question.setWriter(teacher);
        }

        correctAnswerComboBox.getItems().clear();
        correctAnswerComboBox.getItems().add("1");
        correctAnswerComboBox.getItems().add("2");
        correctAnswerComboBox.getItems().add("3");
        correctAnswerComboBox.getItems().add("4");

        resetFields();
        helloLabel.setText("Hello " + user.getFirst_name());
    }

    public void inputErrorComboBox(String emptyFields) {
        Alert inputErrorAlert = new Alert(Alert.AlertType.ERROR);
        inputErrorAlert.setHeaderText("Input Error");
        inputErrorAlert.setContentText("In " + emptyFields +  " No answer was selected");
        Optional<ButtonType> result = inputErrorAlert.showAndWait();
    }

    public void inputErrorTextField(TextField textField)
    {
        textField.setText("Invalid input");
        textField.setStyle("-fx-border-color: #ff0000;");
        thereIsAnError = true;
    }

    public void resetFields()
    {
        ResetRedColor();

        questionTextField.setText("");
        answer1TextField.setText("");
        answer2TextField.setText("");
        answer3TextField.setText("");
        answer4TextField.setText("");
        correctAnswerComboBox.getSelectionModel().clearSelection();
        subjectComboBox.getSelectionModel().clearSelection();
    }

    public void ResetRedColor()
    {
        questionTextField.setStyle("-fx-border-color: #000000;");
        answer1TextField.setStyle("-fx-border-color: #000000;");
        answer2TextField.setStyle("-fx-border-color: #000000;");
        answer3TextField.setStyle("-fx-border-color: #000000;");
        answer4TextField.setStyle("-fx-border-color: #000000;");
        correctAnswerComboBox.setStyle("-fx-border-color: #000000;");
    }


    // This events for return textfield color to black
    @FXML
    void writeAnswer1(ActionEvent event) {
        ResetRedColor();
    }

    @FXML
    void writeAnswer2(ActionEvent event) {
        ResetRedColor();
    }

    @FXML
    void writeAnswer3(ActionEvent event) {
        ResetRedColor();
    }

    @FXML
    void writeAnswer4(ActionEvent event) {
        ResetRedColor();
    }

    @FXML
    void writeQuestion(ActionEvent event) {
        ResetRedColor();
    }
}
