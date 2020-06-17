package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClient;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Bundle;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.CustomProgressIndicator;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Events;
import il.ac.haifa.cs.HSTS.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.server.CommandInterface.QuestionPushCommand;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditQuestionController implements Initializable {

    public User user;
    private Teacher teacher;
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
    private Button editQuestionButton;

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
    void EditQuestion(ActionEvent event) throws InterruptedException {
        // If button text is "Edit Question"
        if (editQuestionButton.getText().equals("Edit Question")) {
            initializeQuestionDetails();
            idTextField.setText("");
            authorTextField.setText(teacher.getUsername());

            setDisableAndVisible(true);
            ((Button) event.getSource()).setText("Confirm Changes");
        }
        // If button text is "Confirm Changes"
        else {
            Alert updateQuestionAlert = new Alert(Alert.AlertType.CONFIRMATION);
            updateQuestionAlert.setHeaderText("Are you sure you want to create that question?");
            Optional<ButtonType> result = updateQuestionAlert.showAndWait();
            {
                if (result.isPresent() && result.get() == ButtonType.OK)
                {

                    // Input checking
                    if (questionTextField.getText().isEmpty())
                        inputError(questionTextField);
                    if (answer1TextField.getText().isEmpty())
                        inputError(answer1TextField);
                    if (answer2TextField.getText().isEmpty())
                        inputError(answer2TextField);
                    if (answer3TextField.getText().isEmpty())
                        inputError(answer3TextField);
                    if (answer4TextField.getText().isEmpty())
                        inputError(answer4TextField);

                    if (!thereIsAnError) {
                        // Updating question details if there are no input errors
                        question.setQuestion(questionTextField.getText());
                        question.setCorrectAnswer(Integer.parseInt(correctAnswerComboBox.getSelectionModel().getSelectedItem()));
                        question.setAnswer(1, answer1TextField.getText());
                        question.setAnswer(2, answer2TextField.getText());
                        question.setAnswer(3, answer3TextField.getText());
                        question.setAnswer(4, answer4TextField.getText());

                        progressIndicator = new CustomProgressIndicator(anchorPane);
                        progressIndicator.start();

                        responseFromServer = null;
                        Task<Response> task = new Task<Response>() {
                            @Override
                            protected Response call() throws Exception {

                                Subject selectedSubject = null;

                                if (user instanceof Teacher) {
                                    List<Subject> listOfSubject = teacher.getSubjects();
                                    for (Subject subject : teacher.getSubjects()) {
                                        if (subject.getSubjectName() == subjectComboBox.getSelectionModel().getSelectedItem()) {
                                            selectedSubject = subject;
                                            break;
                                        }
                                    }
                                }

                                CommandInterface command = new QuestionPushCommand(new Question(questionTextField.getText(),
                                        answer1TextField.getText(), answer2TextField.getText(), answer3TextField.getText(),
                                        answer4TextField.getText(), Integer.parseInt(correctAnswerComboBox.getSelectionModel().getSelectedItem()),
                                        teacher, selectedSubject));

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
                            updateSuccessAlert.setHeaderText("The new question was successfully saved");
                            updateSuccessAlert.showAndWait();

                            ((Button) event.getSource()).setText("Edit Question");
                            setDisableAndVisible(false);
                        });
                        new Thread(task).start();
                    }
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bundle = Bundle.getInstance();
        question = (Question) bundle.get("question");
        client = (HSTSClient)bundle.get("client");
        client.getHstsClientInterface().getGuiControllers().clear();
        client.getHstsClientInterface().addGUIController(this);
        user = (User) bundle.get("user");
        if (user instanceof Teacher)
            teacher = (Teacher) user;
        initializeQuestionDetails();
    }
    @FXML
    void ResetQuestion(ActionEvent event) {
        initializeQuestionDetails();
        if (editQuestionButton.getText().equals("Confirm Changes"))
            editQuestionButton.setText("Edit Question");
        setDisableAndVisible(false);
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
        CustomProgressIndicator progressIndicator = new CustomProgressIndicator(anchorPane);
        progressIndicator.start();

        if (user instanceof Teacher) {
            Teacher teacher = ((Teacher) user);
            subjectComboBox.getItems().clear();
            for (Subject subject : teacher.getSubjects())
                subjectComboBox.getItems().add(subject.getSubjectName());
        }

        subjectComboBox.getSelectionModel().select(question.getSubject().getSubjectName());
        setDisableAndVisible(false);

        correctAnswerComboBox.getItems().clear();
        correctAnswerComboBox.getItems().add("1");
        correctAnswerComboBox.getItems().add("2");
        correctAnswerComboBox.getItems().add("3");
        correctAnswerComboBox.getItems().add("4");
        correctAnswerComboBox.getSelectionModel().select(String.valueOf(question.getCorrectAnswer()));
        correctAnswerComboBox.setEditable(false);
        correctAnswerComboBox.setDisable(true);

        questionTextField.setStyle("-fx-text-inner-color: #000000;");
        answer1TextField.setStyle("-fx-text-inner-color: #000000;");
        answer2TextField.setStyle("-fx-text-inner-color: #000000;");
        answer3TextField.setStyle("-fx-text-inner-color: #000000;");
        answer4TextField.setStyle("-fx-text-inner-color: #000000;");
        correctAnswerComboBox.setStyle("-fx-text-inner-color: #000000;");

        questionTextField.setText(question.getQuestion());
        authorTextField.setText(question.getWriter().getUsername());
        idTextField.setText(String.valueOf(question.getId()));
        answer1TextField.setText(question.getAnswer(1));
        answer2TextField.setText(question.getAnswer(2));
        answer3TextField.setText(question.getAnswer(3));
        answer4TextField.setText(question.getAnswer(4));
        correctAnswerComboBox.getSelectionModel().select(String.valueOf(question.getCorrectAnswer()));
        helloLabel.setText("Hello " + user.getFirst_name());

        progressIndicator.stop();
    }

    private void setDisableAndVisible(boolean changeToDisableAndVisible)
    {
        questionTextField.setEditable(changeToDisableAndVisible);
        subjectComboBox.setEditable(changeToDisableAndVisible);
        answer1TextField.setEditable(changeToDisableAndVisible);
        answer2TextField.setEditable(changeToDisableAndVisible);
        answer3TextField.setEditable(changeToDisableAndVisible);
        answer4TextField.setEditable(changeToDisableAndVisible);
        correctAnswerComboBox.setDisable(!changeToDisableAndVisible);
    }

    private void inputError(TextField textField)
    {
        textField.setText("Invalid input");
        textField.setStyle("-fx-text-inner-color: #ff0000;");
        thereIsAnError = true;
    }

    public void questionOnMouseClicked(MouseEvent mouseEvent) {
        if (questionTextField.getText().equals("Invalid input"))
            ResetField(questionTextField);
    }
    public void answer1OnMouseClicked(MouseEvent mouseEvent) {
        if (answer1TextField.getText().equals("Invalid input"))
            ResetField(answer1TextField);
    }
    public void answer2OnMouseClicked(MouseEvent mouseEvent) {
        if (answer2TextField.getText().equals("Invalid input"))
            ResetField(answer2TextField);
    }
    public void answer3OnMouseClicked(MouseEvent mouseEvent) {
        if (answer3TextField.getText().equals("Invalid input"))
            ResetField(answer3TextField);
    }
    public void answer4OnMouseClicked(MouseEvent mouseEvent) {
        if (answer4TextField.getText().equals("Invalid input"))
            ResetField(answer4TextField);
    }

    public void ResetField(TextField textField)
    {
        textField.setText("");
        textField.setStyle("-fx-text-inner-color: #000000;");
    }

    public void ResetRedColor()
    {
        questionTextField.setStyle("-fx-text-inner-color: #000000;");
        answer1TextField.setStyle("-fx-text-inner-color: #000000;");
        answer2TextField.setStyle("-fx-text-inner-color: #000000;");
        answer3TextField.setStyle("-fx-text-inner-color: #000000;");
        answer4TextField.setStyle("-fx-text-inner-color: #000000;");
    }
}
