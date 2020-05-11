package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.Command;
import il.ac.haifa.cs.HSTS.HSTSClientInterface;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Question;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Subject;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Teacher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditInterface implements Initializable {

    private static Question question;
    private static Command commandFromServer = null;
    private static boolean thereIsAnError = false;

    @FXML
    private AnchorPane anchorPane;

    @FXML // fx:id="returnBtn"
    private Button returnBtn; // Value injected by FXMLLoader

    @FXML // fx:id="Logoutbtn"
    private Button logoutBtn; // Value injected by FXMLLoader

    @FXML // fx:id="QuestionTF"
    private TextField questionTF; // Value injected by FXMLLoader

    @FXML // fx:id="IdTF"
    private TextField idTF; // Value injected by FXMLLoader

    @FXML // fx:id="AuthorTF"
    private TextField authorTF; // Value injected by FXMLLoader

    @FXML
    private ComboBox<String> subjectComboBox;

    @FXML // fx:id="Answer1TF"
    private TextField answer1TF; // Value injected by FXMLLoader

    @FXML // fx:id="Answer2TF"
    private TextField answer2TF; // Value injected by FXMLLoader

    @FXML // fx:id="Answer3TF"
    private TextField answer3TF; // Value injected by FXMLLoader

    @FXML // fx:id="Answer4TF"
    private TextField answer4TF; // Value injected by FXMLLoader


    @FXML
    private ComboBox<String> correctAnswerComboBox;


    @FXML // fx:id="Resetbtn"
    private Button resetBtn; // Value injected by FXMLLoader

    @FXML // fx:id="Editbtn"
    private Button editBtn; // Value injected by FXMLLoader

    @FXML
    private Label helloLB;

    @FXML
    void EditQuestion(ActionEvent event) {
    	// If button text is "Edit Question"
        if(editBtn.getText().equals("Edit Question"))
    	{
            initializeQuestionDetails();
    		// Enable changing the relevant text fields
    		setDisableAndVisible(true);
            // Change button text to "Confirm Changes"
            ((Button)event.getSource()).setText("Confirm Changes");
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
                if (questionTF.getText().isEmpty())
                    inputError(questionTF);
                if (answer1TF.getText().isEmpty())
                    inputError(answer1TF);
                if (answer2TF.getText().isEmpty())
                    inputError(answer2TF);
                if (answer3TF.getText().isEmpty())
                    inputError(answer3TF);
                if (answer4TF.getText().isEmpty())
                    inputError(answer4TF);

                if (!thereIsAnError) {
                    // Showing waiting alert
                    Alert waitingAlert = new Alert(Alert.AlertType.INFORMATION);
                    waitingAlert.setHeaderText("");
                    waitingAlert.setContentText("Please wait while the question is updating");

                    // Creating a waiting indicator
                    ProgressIndicator waitingMessage = new ProgressIndicator();
                    VBox vbox = new VBox(waitingMessage);
                    vbox.setAlignment(Pos.CENTER);
                    waitingAlert.setGraphic(vbox);
                    anchorPane.setDisable(true);
                    waitingAlert.showAndWait();
                    anchorPane.setDisable(false);

                    question.setQuestion(questionTF.getText());
                    question.setCorrectAnswer(Integer.parseInt(correctAnswerComboBox.getSelectionModel().getSelectedItem()));
                    question.setAnswer(1, answer1TF.getText());
                    question.setAnswer(2, answer2TF.getText());
                    question.setAnswer(3, answer3TF.getText());
                    question.setAnswer(4, answer4TF.getText());

                    Command command = new Command("update", "questions", question);
                    HSTSClientInterface.sendCommandToServer(command);

                    //Waiting for server confirmation
                    while (commandFromServer == null) {
                        System.out.print("");
                    }

                    // After server confirmation we show the message "The question was successfully changed"
                    Alert updateSuccessAlert = new Alert(Alert.AlertType.INFORMATION);
                    updateSuccessAlert.setHeaderText("The question was successfully changed");
                    updateSuccessAlert.showAndWait();
                }
                else
                    thereIsAnError = false;
            }
            // Return button text to "Edit"
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
        if (editBtn.getText().equals("Confirm Changes"))
            editBtn.setText("Edit Question");
        setDisableAndVisible(false);
    }

    @FXML
    void ReturnToMenu(ActionEvent event) throws IOException {
        Scene scene = new Scene(loadFXML("menuInterface"));
        Stage stage = (Stage) returnBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Menu");
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        Scene scene = new Scene(loadFXML("loginInterface"));
        Stage stage = (Stage) logoutBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login");
    }

    public Question getQuestion() {
        return question;
    }

    public static void setQuestion(Question question) {
        EditInterface.question = question;
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainClass.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void receivedCommandFromServer(Command command){
        commandFromServer = command;
        System.out.println("Command received in controller " + command);
    }

    public void initializeQuestionDetails()
    {
        Teacher teacher = ((Teacher)menuInterface.getUser());
        subjectComboBox.getItems().clear();
        for (Subject subject : teacher.getSubjects())
            subjectComboBox.getItems().add(subject.getSubjectName());
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

        questionTF.setStyle("-fx-text-inner-color: #000000;");
        answer1TF.setStyle("-fx-text-inner-color: #000000;");
        answer2TF.setStyle("-fx-text-inner-color: #000000;");
        answer3TF.setStyle("-fx-text-inner-color: #000000;");
        answer4TF.setStyle("-fx-text-inner-color: #000000;");
        correctAnswerComboBox.setStyle("-fx-text-inner-color: #000000;");

        questionTF.setText(question.getQuestion());
        authorTF.setText(question.getWriter().getUsername());
        idTF.setText(String.valueOf(question.getId()));
        answer1TF.setText(question.getAnswer(1));
        answer2TF.setText(question.getAnswer(2));
        answer3TF.setText(question.getAnswer(3));
        answer4TF.setText(question.getAnswer(4));
        correctAnswerComboBox.getSelectionModel().select(String.valueOf(question.getCorrectAnswer()));
        helloLB.setText("Hello " + question.getWriter().getFirst_name());
    }

    private void setDisableAndVisible(boolean changeToDisableAndVisible)
    {
        questionTF.setEditable(changeToDisableAndVisible);
        subjectComboBox.setDisable(true);
        answer1TF.setEditable(changeToDisableAndVisible);
        answer2TF.setEditable(changeToDisableAndVisible);
        answer3TF.setEditable(changeToDisableAndVisible);
        answer4TF.setEditable(changeToDisableAndVisible);
        correctAnswerComboBox.setDisable(!changeToDisableAndVisible);
    }

    private void inputError(TextField textField)
    {
        textField.setText("Invalid input");
        textField.setStyle("-fx-text-inner-color: #ff0000;");
        thereIsAnError = true;
    }
}
