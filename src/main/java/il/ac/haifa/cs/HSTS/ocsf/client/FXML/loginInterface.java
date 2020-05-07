package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.Command;
import il.ac.haifa.cs.HSTS.HSTSClientInterface;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class loginInterface {
	/**
	 * Sample Skeleton for 'loginInterface.fxml' Controller Class
	 */
	private static Command commandFromServer = null;

    @FXML // fx:id="loginBtn"
    private Button loginBtn; // Value injected by FXMLLoader
    
    @FXML // fx:id="usernameTF"
    private TextField usernameTF; // Value injected by FXMLLoader

    @FXML // fx:id="passwordTF"
    private PasswordField passwordTF; // Value injected by FXMLLoader

    @FXML // fx:id="errorLB"
    private Label errorLB; // Value injected by FXMLLoader

    @FXML
    void login(ActionEvent event) throws IOException{
        // Creating command here and send it to client
        errorLB.setVisible(false);
        String username = usernameTF.getCharacters().toString();
        String password = passwordTF.getCharacters().toString();

        if (username.isBlank() || password.isBlank()) {
            errorLB.setVisible(true);
            return;
        }
        Command command = new Command("login","users", username, password);
        HSTSClientInterface.sendCommandToServer(command);

        while (commandFromServer == null){
            System.out.print("");
        }

        System.out.println(commandFromServer);
        User userLoggedIn;
        if (commandFromServer.getReturnedObject() == null) {
            errorLB.setVisible(true);
            commandFromServer = null;
            return;
        }
        else {
            userLoggedIn = (User) commandFromServer.getReturnedObject();
            System.out.println("User: "+userLoggedIn.getUsername() + " is logged in");
            Scene scene = new Scene(loadFXML("menuInterface"));
            Stage stage = (Stage) loginBtn.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Menu");
            menuInterface.setUser(userLoggedIn);
            ((Label) scene.lookup("#helloLB")).setText("Hello " + userLoggedIn.getFirst_name());
            commandFromServer = null;
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainClass.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void receivedCommandFromServer(Command command){
        commandFromServer = command;
        System.out.println("Command received in controller " + command);
    }


}



