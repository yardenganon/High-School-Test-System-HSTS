package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.Command;
import il.ac.haifa.cs.HSTS.HSTSClientInterface;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class loginInterface {
	/**
	 * Sample Skeleton for 'GUIInterface.fxml' Controller Class
	 */
	static private Command commandFromServer = null;

    @FXML // fx:id="loginBtn"
    private Button loginBtn; // Value injected by FXMLLoader
    
    @FXML // fx:id="usernameTF"
    private TextField usernameTF; // Value injected by FXMLLoader

    @FXML // fx:id="passwordTF"
    private TextField passwordTF; // Value injected by FXMLLoader

    @FXML
    void login(ActionEvent event) throws IOException{
        // Creating command here and send it to client
        Command command = new Command("login","users",
                usernameTF.getCharacters().toString(),
                passwordTF.getCharacters().toString());
        HSTSClientInterface.sendCommandToServer(command);

        while (commandFromServer == null){
            System.out.print("");
        }
        System.out.println(commandFromServer);
        User userLoggedIn;
        if (commandFromServer.getReturnedObject() != null) {
            userLoggedIn = (User) commandFromServer.getReturnedObject();
            System.out.println("User: "+userLoggedIn.getUsername() + " is logged in");
            MainClass.setRoot("menuInterface");
            commandFromServer = null;
        }
        else
        {
            // Label up
            System.out.println("sad");
        }

    }

    public static void receivedCommandFromServer(Command command){
        commandFromServer = command;
        System.out.println("Command received in controller " + command);
    }


}



