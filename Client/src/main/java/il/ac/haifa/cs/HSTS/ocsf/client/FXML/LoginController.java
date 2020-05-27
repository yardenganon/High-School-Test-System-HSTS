package il.ac.haifa.cs.HSTS.ocsf.client.FXML;
import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClient;
import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClientInterface;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Bundle;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Events;
import il.ac.haifa.cs.HSTS.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.server.CommandInterface.LoginCommand;
import il.ac.haifa.cs.HSTS.server.CommandInterface.Response;
import il.ac.haifa.cs.HSTS.server.Entities.User;
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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
	/**
	 * Sample Skeleton for 'Login.fxml' Controller Class
	 */
	private Response responseFromServer = null;
	private HSTSClient client;
	public User user;
	private Bundle bundle;

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

        Task<Response> task = new Task<Response>() {

            @Override
            protected Response call() throws Exception {
                CommandInterface command = new LoginCommand(username, password);
                client.getHstsClientInterface().sendCommandToServer(command);
                while (responseFromServer == null)
                    System.out.print("");
                return responseFromServer;
            }
        };
        task.setOnFailed(e-> {
            System.out.println(e);
            errorLB.setText("Error - Connection Problem");
            errorLB.setVisible(true);
        });
        task.setOnSucceeded(e -> {
            responseFromServer = task.getValue();
            user = (User) responseFromServer.getReturnedObject();
            if (user == null) {
                errorLB.setText("Username and/or password are incorrect");
                errorLB.setVisible(true);
            }
            else {
                bundle.put("user",user);
                System.out.println("User: "+user.getUsername() + " is logged in");
                Events.navigateMenuEvent(loginBtn);
            }
            responseFromServer = null;
        });
        new Thread(task).start();
    }

    public Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainClass.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public void receivedRespondFromServer(Response response){
        responseFromServer = response;
        System.out.println("Command received in controller " + response);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bundle = Bundle.getInstance();
        client = (HSTSClient)bundle.get("client");
        client.getHstsClientInterface().addGUIController(this);
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

    public void setUser(User user) {
        this.user = user;
    }
}



