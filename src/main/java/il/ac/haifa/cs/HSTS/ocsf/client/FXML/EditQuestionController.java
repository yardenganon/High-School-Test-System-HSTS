package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.HSTSClientInterface;
import il.ac.haifa.cs.HSTS.ocsf.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.ocsf.server.CommandInterface.QuestionUpdateCommand;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Question;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Subject;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Teacher;
import il.ac.haifa.cs.HSTS.ocsf.server.Services.Response;
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

    private static Question question;
    private static Response responseFromServer = null;
    private static boolean thereIsAnError = false;


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
    	    if (!question.getWriter().equals(MenuController.getUser().getUsername()))
            {
                // Show permission error
                Alert updateQuestionAlert = new Alert(Alert.AlertType.ERROR);
                updateQuestionAlert.setHeaderText("You don't have the permissions to change that question");
            }
    	    else {
                initializeQuestionDetails();
                setDisableAndVisible(true);
                ((Button) event.getSource()).setText("Confirm Changes");
            }
    	}
    	// If button text is "Confirm Changes"
    	else {
            // Ask the user to confirm the changes
            Alert updateQuestionAlert = new Alert(Alert.AlertType.CONFIRMATION);
            updateQuestionAlert.setHeaderText("Are you sure you want to update the question?");
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
                    // Showing waiting alert
                    Alert waitingAlert = new Alert(Alert.AlertType.INFORMATION);
                    waitingAlert.setHeaderText("");
                    waitingAlert.setContentText("Please wait while the question is updating");

                    /*
                    // Creating a waiting indicator
                    ProgressIndicator waitingMessage = new ProgressIndicator();
                    VBox vbox = new VBox(waitingMessage);
                    vbox.setAlignment(Pos.CENTER);
                    waitingAlert.setGraphic(vbox);
                    anchorPane.setDisable(true);
                    waitingAlert.showAndWait();
                    anchorPane.setDisable(false);
                     */

                    question.setQuestion(questionTextField.getText());
                    question.setCorrectAnswer(Integer.parseInt(correctAnswerComboBox.getSelectionModel().getSelectedItem()));
                    question.setAnswer(1, answer1TextField.getText());
                    question.setAnswer(2, answer2TextField.getText());
                    question.setAnswer(3, answer3TextField.getText());
                    question.setAnswer(4, answer4TextField.getText());

                    responseFromServer =null;
                    CommandInterface command = new QuestionUpdateCommand(question);
                    HSTSClientInterface.sendCommandToServer(command);

                    //Waiting for server confirmation
                    while (responseFromServer == null) {
                        System.out.print("");
                    }
                    question = (Question) responseFromServer.getReturnedObject();
                    initializeQuestionDetails();

                    Alert updateSuccessAlert = new Alert(Alert.AlertType.INFORMATION);
                    updateSuccessAlert.setHeaderText("The question was successfully changed");
                    updateSuccessAlert.showAndWait();
                }
                else
                    thereIsAnError = false;
            }
            ((Button) event.getSource()).setText("Edit Question");
            setDisableAndVisible(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        Scene scene = new Scene(loadFXML("Menu"));
        Stage stage = (Stage) goToMenuButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Menu");
    }

    @FXML
    void goToQuestions(ActionEvent event) throws IOException {
        Scene scene = new Scene(loadFXML("Questions"));
        Stage stage = (Stage) goToQuestionsButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Questions");
    }

    @FXML
    void goToTests(ActionEvent event) {

    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        Scene scene = new Scene(loadFXML("Login"));
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login");
    }

    public Question getQuestion() {
        return question;
    }

    public static void setQuestion(Question question) {
        EditQuestionController.question = question;
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainClass.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void receivedResponseFromServer(Response response){
        responseFromServer = response;
        System.out.println("Command received in controller " + response);
    }

    public void initializeQuestionDetails()
    {
        if (MenuController.getUser() instanceof Teacher) {
            Teacher teacher = ((Teacher) MenuController.getUser());
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
        helloLabel.setText("Hello " + MenuController.getUser().getFirst_name());
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
