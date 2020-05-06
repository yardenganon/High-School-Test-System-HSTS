/**
 * Sample Skeleton for 'EditInterface.fxml' Controller Class
 */

package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import java.awt.TextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class EditInterface {
	
    private static Boolean confirmButton = false;

    @FXML // fx:id="returnBtn"
    private Button returnBtn; // Value injected by FXMLLoader

    @FXML // fx:id="Logoutbtn"
    private Button Logoutbtn; // Value injected by FXMLLoader

    @FXML // fx:id="QuestionTF"
    private TextField questionTF; // Value injected by FXMLLoader

    @FXML // fx:id="IdTF"
    private TextField idTF; // Value injected by FXMLLoader

    @FXML // fx:id="AuthorTF"
    private TextField authorTF; // Value injected by FXMLLoader

    @FXML // fx:id="SubjectTF"
    private TextField subjectTF; // Value injected by FXMLLoader

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
    	// If the button is "Edit Question"
    	if (!confirmButton)
    	{
    		editBtn.setText("Confirm Changes");
    		// Enable changing the relevant text fields
    		questionTF.setEditable(true);
    		authorTF.setEditable(true);
    		subjectTF.setEditable(true);
    		answer1TF.setEditable(true);
    		answer2TF.setEditable(true);
    		answer3TF.setEditable(true);
    		answer4TF.setEditable(true);
    		correctAnswerTF.setEditable(true);
    		confirmButton = true;
    	}
    	// if the button is "Confirm Changes"
    	else
    	{
    		// Need to add here a waiting message for the user while the question is updating
    		// and then to update the text fields to contain the new data and lock the access for them
    		// For each text field: QuestionTF.setText() with the new data
    		
        	questionTF.setEditable(false);
        	authorTF.setEditable(false);
        	subjectTF.setEditable(false);
        	answer1TF.setEditable(false);
        	answer2TF.setEditable(false);
        	answer3TF.setEditable(false);
        	answer4TF.setEditable(false);
        	correctAnswerTF.setEditable(false);
        	confirmButton = false;
    	}
    }

    @FXML
    void ResetQuestion(ActionEvent event) {

    }

    @FXML
    void ReturnToMenu(ActionEvent event) {

    }

    @FXML
    void logout(ActionEvent event) {

    }

}
