package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.Command;
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


    @FXML
    private ComboBox<String> subjectComboBox;

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


    @FXML // fx:id="Answer1TF"
    private TextField answer1TF; // Value injected by FXMLLoader

    @FXML // fx:id="Answer2TF"
    private TextField answer2TF; // Value injected by FXMLLoader

    @FXML // fx:id="Answer3TF"
    private TextField answer3TF; // Value injected by FXMLLoader

    @FXML // fx:id="Answer4TF"
    private TextField answer4TF; // Value injected by FXMLLoader

    @FXML // fx:id="CorrectAnswerTF"
    private TextField correctAnswerTF; // Value injected by FXMLLoader

    @FXML // fx:id="Resetbtn"
    private Button resetBtn; // Value injected by FXMLLoader

    @FXML // fx:id="Editbtn"
    private Button editBtn; // Value injected by FXMLLoader

    @FXML
    void EditQuestion(ActionEvent event) {
    	// If button text is "Edit Question"
    	if (((Button)event.getSource()).getText().equals("Edit Question"))
    	{
    		// Enable changing the relevant text fields
    		questionTF.setEditable(true);
    		authorTF.setEditable(true);
    		subjectComboBox.setEditable(true);

    		answer1TF.setEditable(true);
    		answer2TF.setEditable(true);
    		answer3TF.setEditable(true);
    		answer4TF.setEditable(true);
    		correctAnswerTF.setEditable(true);

            // Change button text to "Confirm Changes"
            ((Button)event.getSource()).setText("Confirm Changes");
    	}
    	// If button text is "Confirm Changes"
    	else
    	{
    	    // Ask the user to confirm the changes
    	    Alert updateQuestionAlert = new Alert(Alert.AlertType.CONFIRMATION);
    	    updateQuestionAlert.setHeaderText("Are you sure you want to update the question?");
            Optional<ButtonType> result = updateQuestionAlert.showAndWait();
            //if (result.isPresent() && result.get() != ButtonType.OK) {
                // need to do the same action as when Reset Button
            //}

            // Showing waiting alert
            Alert waitingAlert = new Alert(Alert.AlertType.INFORMATION);
            waitingAlert.setHeaderText("");
            waitingAlert.setContentText("Please wait while the question is updating");
            //waitingAlert.getDialogPane().lookupButton(ButtonType.OK).setVisible(false);

            // Creating a waiting indicator
            ProgressIndicator waitingMessage = new ProgressIndicator();
            VBox vbox = new VBox(waitingMessage);
            vbox.setAlignment(Pos.CENTER);
            waitingAlert.setGraphic(vbox);
            anchorPane.setDisable(true);
            waitingAlert.showAndWait();
            anchorPane.setDisable(false);
            //Stage stage = (Stage)anchorPane.getScene().getWindow();
            //stage.show();

            /*
            // This section is for closing an alert who doesn't have OK button
            Button cancelButton = (Button)waitingAlert.getDialogPane().lookupButton(ButtonType.CANCEL);
            if (cancelButton != null)
                cancelButton.fire();
            */

            //After server confirmation we show the message "The question was successfully changed"
            Alert updateSuccessAlert = new Alert(Alert.AlertType.INFORMATION);
            updateSuccessAlert.setHeaderText("The question was successfully changed");
            updateSuccessAlert.showAndWait();

            // Return button text to "Edit"
            ((Button)event.getSource()).setText("Edit");

    		// Here need to update the text fields to contain the new data
    		// For each text field: QuestionTF.setText() with the new data

            // Again lock the access for the text fields
        	questionTF.setEditable(false);
        	authorTF.setEditable(false);
        	subjectComboBox.setEditable(false);
        	answer1TF.setEditable(false);
        	answer2TF.setEditable(false);
        	answer3TF.setEditable(false);
        	answer4TF.setEditable(false);
        	correctAnswerTF.setEditable(false);
    	}
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeQuestionDetails();
    }
    @FXML
    void ResetQuestion(ActionEvent event) {
        initializeQuestionDetails();
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
        questionTF.setText(question.getQuestion());
        authorTF.setText(question.getWriter().getUsername());
        idTF.setText(String.valueOf(question.getId()));
        answer1TF.setText(question.getAnswer(1));
        answer2TF.setText(question.getAnswer(2));
        answer3TF.setText(question.getAnswer(3));
        answer4TF.setText(question.getAnswer(4));
        correctAnswerTF.setText(String.valueOf(question.getCorrectAnswer()));
    }
}
