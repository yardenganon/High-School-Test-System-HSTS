package il.ac.haifa.cs.HSTS.ocsf.client.FXML;
import il.ac.haifa.cs.HSTS.HSTSClientInterface;
import il.ac.haifa.cs.HSTS.ocsf.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.ocsf.server.CommandInterface.LoginCommand;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.User;
import il.ac.haifa.cs.HSTS.ocsf.server.Services.Response;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
	/**
	 * Sample Skeleton for 'Login.fxml' Controller Class
	 */
	private static Response responseFromServer = null;

    @FXML // fx:id="loginBtn"
    private Button loginBtn; // Value injected by FXMLLoader
    
    @FXML // fx:id="usernameTF"
    private TextField usernameTF; // Value injected by FXMLLoader

    @FXML // fx:id="passwordTF"
    private PasswordField passwordTF; // Value injected by FXMLLoader

    @FXML // fx:id="errorLB"
    private Label errorLB; // Value injected by FXMLLoader

    @FXML
    private AnchorPane anchorPane;



    @FXML
    void loginBtnPressed(ActionEvent event) throws IOException{
       executeLogin();
    }
    void executeLogin() throws IOException{
        // Creating command here and send it to client
        errorLB.setVisible(false);
        String username = usernameTF.getCharacters().toString();
        String password = passwordTF.getCharacters().toString();

        if (username.isBlank() || password.isBlank()) {
            errorLB.setVisible(true);
            return;
        }

        User userLoggedIn;

        Task<Response> task = new Task<Response>() {

            @Override
            protected Response call() throws Exception {
                CommandInterface command = new LoginCommand(username, password);
                HSTSClientInterface.sendCommandToServer(command);
                while (responseFromServer == null)
                    System.out.print("");
                return responseFromServer;
            }
        };
        task.setOnFailed(e-> {
            errorLB.setText("Error - Connection Problem");
            errorLB.setVisible(true);
        });
        task.setOnSucceeded(e -> {
            responseFromServer = task.getValue();
            User user = (User) responseFromServer.getReturnedObject();
            if (user == null) {
                errorLB.setText("Username and/or password are incorrect");
                errorLB.setVisible(true);
            }
            else {
                MenuController.setUser(user);
                System.out.println("User: "+user.getUsername() + " is logged in");
                Scene scene = null;
                try {
                    scene = new Scene(loadFXML("Menu"));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                Stage stage = (Stage) loginBtn.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Menu");
            }
            responseFromServer = null;
        });
        new Thread(task).start();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainClass.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void receivedRespondFromServer(Response response){
        responseFromServer = response;
        System.out.println("Command received in controller " + response);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EventHandler<KeyEvent> enterLoginEvent = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    try {
                        executeLogin();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        usernameTF.setOnKeyPressed(enterLoginEvent);
        passwordTF.setOnKeyPressed(enterLoginEvent);
    }
}


