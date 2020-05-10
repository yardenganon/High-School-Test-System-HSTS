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
    	//if (((Button)event.getSource()).getText().equals("Edit Question"))
        if(editBtn.getText().equals("Edit Question"))
    	{
    	    initializeQuestionDetails();
            questionTF.setStyle("-fx-text-inner-color: #000000;");
            answer1TF.setStyle("-fx-text-inner-color: #000000;");
            answer2TF.setStyle("-fx-text-inner-color: #000000;");
            answer3TF.setStyle("-fx-text-inner-color: #000000;");
            answer4TF.setStyle("-fx-text-inner-color: #000000;");
            correctAnswerComboBox.setStyle("-fx-text-inner-color: #000000;");

    		// Enable changing the relevant text fields
    		questionTF.setEditable(true);
    		//subjectTF.setEditable(true);
    		answer1TF.setEditable(true);
    		answer2TF.setEditable(true);
    		answer3TF.setEditable(true);
    		answer4TF.setEditable(true);
    		correctAnswerComboBox.setEditable(true);

            questionTF.setDisable(false);
            //subjectTF.setDisable(false);
            answer1TF.setDisable(false);
            answer2TF.setDisable(false);
            answer3TF.setDisable(false);
            answer4TF.setDisable(false);
            correctAnswerComboBox.setDisable(false);

            // Change button text to "Confirm Changes"
            ((Button)event.getSource()).setText("Confirm Changes");
    	}
    	// If button text is "Confirm Changes"
    	else {
            // Ask the user to confirm the changes
            Alert updateQuestionAlert = new Alert(Alert.AlertType.CONFIRMATION);
            updateQuestionAlert.setHeaderText("Are you sure you want to update the question?");
            Optional<ButtonType> result = updateQuestionAlert.showAndWait();
            //if (result.isPresent() && result.get() != ButtonType.OK) {
            // need to do the same action as when Reset Button
            //}

            boolean thereIsAnError = false;
            // Input checking
            if (questionTF.getText().isEmpty()) {
                questionTF.setText("Invalid Input");
                questionTF.setStyle("-fx-text-inner-color: #ff0000;");
                thereIsAnError = true;
            }
            //if (subjectTF.getText().isEmpty()) {
            //    subjectTF.setText("Invalid input");
            //    thereIsAnError = true;
            //}
            if (answer1TF.getText().isEmpty()) {
                answer1TF.setText("Invalid input");
                answer1TF.setStyle("-fx-text-inner-color: #ff0000;");
                thereIsAnError = true;
            }
            if (answer2TF.getText().isEmpty()) {
                answer2TF.setText("Invalid input");
                answer2TF.setStyle("-fx-text-inner-color: #ff0000;");
                thereIsAnError = true;
            }
            if (answer3TF.getText().isEmpty()) {
                answer3TF.setText("Invalid input");
                answer3TF.setStyle("-fx-text-inner-color: #ff0000;");
                thereIsAnError = true;
            }
            if (answer4TF.getText().isEmpty()) {
                answer4TF.setText("Invalid input");
                answer4TF.setStyle("-fx-text-inner-color: #ff0000;");
                thereIsAnError = true;
            }
            String selectedAnswer = correctAnswerComboBox.getSelectionModel().getSelectedItem();
            if (selectedAnswer.equals("The correct answer must be one of the four options"))
                correctAnswerComboBox.getSelectionModel().select(String.valueOf(question.getCorrectAnswer()));
            if (!(selectedAnswer.equals(answer1TF.getText()) ||
                    selectedAnswer.equals(answer2TF.getText()) ||
                    selectedAnswer.equals(answer3TF.getText()) ||
                    selectedAnswer.equals(answer4TF.getText()))) {
                correctAnswerComboBox.getSelectionModel().select("The correct answer must be one of the four options");
                correctAnswerComboBox.setStyle("-fx-text-inner-color: #ff0000;");
                thereIsAnError = true;
            }
            if (!thereIsAnError)
            {
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

                //question.setSubject(subjectTF.getText());
                question.setCorrectAnswer(Integer.parseInt(selectedAnswer.toString()));
                question.setAnswer(1, answer1TF.getText());
                question.setAnswer(2, answer2TF.getText());
                question.setAnswer(3, answer3TF.getText());
                question.setAnswer(4, answer4TF.getText());

                Command command = new Command("update", "questions", question);
                HSTSClientInterface.sendCommandToServer(command);

                // Waiting for server confirmation
            /*
            while (commandFromServer == null){
                System.out.print("");
            }
            */

                System.out.println(commandFromServer);
                // After server confirmation we show the message "The question was successfully changed"
                Alert updateSuccessAlert = new Alert(Alert.AlertType.INFORMATION);
                updateSuccessAlert.setHeaderText("The question was successfully changed");
                updateSuccessAlert.showAndWait();
            }

            // Return button text to "Edit"
            ((Button)event.getSource()).setText("Edit Question");

            // Again lock the access for the text fields
        	questionTF.setEditable(false);
        	//subjectTF.setEditable(false);
            subjectComboBox.setEditable(false);
        	answer1TF.setEditable(false);
        	answer2TF.setEditable(false);
        	answer3TF.setEditable(false);
        	answer4TF.setEditable(false);
        	correctAnswerComboBox.setEditable(false);

            questionTF.setDisable(true);
            //subjectTF.setDisable(false);

            answer1TF.setDisable(true);
            answer2TF.setDisable(true);
            answer3TF.setDisable(true);
            answer4TF.setDisable(true);
            correctAnswerComboBox.setDisable(true);
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
        questionTF.setDisable(true);
        //subjectTF.setDisable(false);
        answer1TF.setDisable(true);
        answer2TF.setDisable(true);
        answer3TF.setDisable(true);
        answer4TF.setDisable(true);
        correctAnswerComboBox.setDisable(true);

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
        System.out.println(teacher.getSubjects());
        for (Subject subject : teacher.getSubjects())
            subjectComboBox.getItems().add(subject.getSubjectName());
        subjectComboBox.getSelectionModel().select(question.getSubject().getSubjectName());
        subjectComboBox.setEditable(false);
        subjectComboBox.setDisable(true);

        correctAnswerComboBox.getItems().clear();
        correctAnswerComboBox.getItems().add("1");
        correctAnswerComboBox.getItems().add("2");
        correctAnswerComboBox.getItems().add("3");
        correctAnswerComboBox.getItems().add("4");
        correctAnswerComboBox.getSelectionModel().select(String.valueOf(question.getCorrectAnswer()));
        correctAnswerComboBox.setEditable(false);
        correctAnswerComboBox.setDisable(true);

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
}
