/**
 * Sample Skeleton for 'EditInterface.fxml' Controller Class
 */

package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import java.awt.TextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class EditInterface {
	
    //private static Boolean confirmButton = false;

    @FXML // fx:id="returnBtn"
    private Button returnBtn; // Value injected by FXMLLoader

    @FXML // fx:id="Logoutbtn"
    private Button Logoutbtn; // Value injected by FXMLLoader

    @FXML // fx:id="QuestionTF"
    private TextField QuestionTF; // Value injected by FXMLLoader

    @FXML // fx:id="IdTF"
    private TextField IdTF; // Value injected by FXMLLoader

    @FXML // fx:id="AuthorTF"
    private TextField AuthorTF; // Value injected by FXMLLoader

    @FXML // fx:id="SubjectTF"
    private TextField SubjectTF; // Value injected by FXMLLoader

    @FXML // fx:id="Answer1TF"
    private TextField Answer1TF; // Value injected by FXMLLoader

    @FXML // fx:id="Answer2TF"
    private TextField Answer2TF; // Value injected by FXMLLoader

    @FXML // fx:id="Answer3TF"
    private TextField Answer3TF; // Value injected by FXMLLoader

    @FXML // fx:id="Answer4TF"
    private TextField Answer4TF; // Value injected by FXMLLoader

    @FXML // fx:id="CorrectAnswerTF"
    private TextField CorrectAnswerTF; // Value injected by FXMLLoader

    @FXML // fx:id="Resetbtn"
    private Button Resetbtn; // Value injected by FXMLLoader

    @FXML // fx:id="Editbtn"
    private Button Editbtn; // Value injected by FXMLLoader
    
    @FXML
    void EditQuestion(ActionEvent event) {
    	// If the button is "Edit Question"
    	//if (!confirmButton)
    	//{
    		//Editbtn.setText("Confirm Changes");
    		// Enable changing the relevant text fields
    		//QuestionTF.setEditable(true);
    		//AuthorTF.setEditable(true);
    		//SubjectTF.setEditable(true);
    		//Answer1TF.setEditable(true);
    		//Answer2TF.setEditable(true);
    		//Answer3TF.setEditable(true);
    		//Answer4TF.setEditable(true);
    		//CorrectAnswerTF.setEditable(true);
    		//confirmButton = true;
    	//}
    	// if the button is "Confirm Changes"
    	//else
    	//{
    		// Need to add here a waiting message for the user while the question is updating
    		// and then to update the text fields to contain the new data and lock the access for them
    		// For each text field: QuestionTF.setText() with the new data
    		
        //	QuestionTF.setEditable(false);
        	//AuthorTF.setEditable(false);
        	//SubjectTF.setEditable(false);
        	//Answer1TF.setEditable(false);
        	//Answer2TF.setEditable(false);
        	//Answer3TF.setEditable(false);
        	//Answer4TF.setEditable(false);
        	//CorrectAnswerTF.setEditable(false);
        	//confirmButton = false;
    	//}
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
