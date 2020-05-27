package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClient;
import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClientInterface;

import il.ac.haifa.cs.HSTS.ocsf.client.Services.Bundle;
import il.ac.haifa.cs.HSTS.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.server.CommandInterface.QuestionUpdateCommand;
import il.ac.haifa.cs.HSTS.server.CommandInterface.Response;
import il.ac.haifa.cs.HSTS.server.Entities.Question;
import il.ac.haifa.cs.HSTS.server.Entities.Subject;
import il.ac.haifa.cs.HSTS.server.Entities.Teacher;
import il.ac.haifa.cs.HSTS.server.Entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditQuestionController implements Initializable {

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

    @FXML
    void EditQuestion(ActionEvent event) throws InterruptedException {
    	// If button text is "Edit Question"
        if(editQuestionButton.getText().equals("Edit Question"))
    	{
    	    // Checking if there are permissions to edit the question
    	    if (!question.getWriter().getUsername().equals(user.getUsername()))
            {
                Alert permissionsErrorAlert = new Alert(Alert.AlertType.ERROR);
                permissionsErrorAlert.setHeaderText("You don't have the permissions to change that question");
                permissionsErrorAlert.showAndWait();
            }
    	    else {
                initializeQuestionDetails();
                setDisableAndVisible(true);
                ((Button) event.getSource()).setText("Confirm Changes");
            }
    	}
    	// If button text is "Confirm Changes"
    	else {
            Alert updateQuestionAlert = new Alert(Alert.AlertType.CONFIRMATION);
            updateQuestionAlert.setHeaderText("Are you sure you want to update the question?");
            anchorPane.setDisable(true);
            Optional<ButtonType> result = updateQuestionAlert.showAndWait();
            if (result.isPresent() && result.get() != ButtonType.OK)
                initializeQuestionDetails();
            else {
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

                    responseFromServer = null;
                    CommandInterface command = new QuestionUpdateCommand(question);
                    client.getHstsClientInterface().sendCommandToServer(command);
                    // Waiting for server confirmation
                    while (responseFromServer == null) {
                        System.out.print("");
                    }
                    question = (Question) responseFromServer.getReturnedObject();
                    initializeQuestionDetails();
                    anchorPane.setDisable(false);
                    Alert updateSuccessAlert = new Alert(Alert.AlertType.INFORMATION);
                    updateSuccessAlert.setHeaderText("The question was successfully changed");
                    updateSuccessAlert.showAndWait();
                }
                else thereIsAnError = false;
            }

            ((Button) event.getSource()).setText("Edit Question");
            setDisableAndVisible(false);
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
        initializeQuestionDetails();
        if (editQuestionButton.getText().equals("Confirm Changes"))
            editQuestionButton.setText("Edit Question");
        setDisableAndVisible(false);
    }

    @FXML
    void about(ActionEvent event) {

    }

    @FXML
    void goToCourses(ActionEvent event) {

    }

    @FXML
    void goToMenu(ActionEvent event) throws IOException {
        Scene scene = new Scene(MainClass.loadFXML("Questions"));
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Questions");
    }

    @FXML
    void goToQuestions(ActionEvent event) throws IOException {
        Scene scene = new Scene(MainClass.loadFXML("Questions"));
        Stage stage = (Stage) goToQuestionsButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Questions");
    }

    @FXML
    void goToTests(ActionEvent event) {

    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        bundle.remove("user");
        Scene scene = new Scene(MainClass.loadFXML("Login"));
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login");
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
    }

    private void setDisableAndVisible(boolean changeToDisableAndVisible)
    {
        questionTextField.setEditable(changeToDisableAndVisible);
        subjectComboBox.setDisable(true);
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
}
