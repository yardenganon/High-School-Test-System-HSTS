package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.Command;
import il.ac.haifa.cs.HSTS.HSTSClientInterface;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.User;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class loginInterface implements Initializable {
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

        Task<Command> task = new Task<Command>() {

            @Override
            protected Command call() throws Exception {

                Command command = new Command("login","users", username, password);
                HSTSClientInterface.sendCommandToServer(command);
//                StackPane root = (StackPane) anchorPane.getParent();
//                ProgressIndicator indicator = new ProgressIndicator();
//                VBox vbox = new VBox(indicator);
//                vbox.setAlignment(Pos.CENTER);
//                vbox.setVisible(true);
//                root.getChildren().add(vbox);
//                Scene scene1 = anchorPane.getScene();
//                Stage stage1 = (Stage) anchorPane.getScene().getWindow();
//                stage1.setScene(scene1);
//                stage1.show();
                while (commandFromServer == null)
                    System.out.print("");
                return commandFromServer;
            }
        };
        task.setOnFailed(e-> {
            errorLB.setText("Error - Connection Problem");
            errorLB.setVisible(true);
        });
        task.setOnSucceeded(e -> {
            commandFromServer = task.getValue();
            User user = (User) commandFromServer.getReturnedObject();
            if (user == null) {
                errorLB.setText("Username and/or password are incorrect");
                errorLB.setVisible(true);
            }
            else {
                menuInterface.setUser(user);
                System.out.println("User: "+user.getUsername() + " is logged in");
                Scene scene = null;
                try {
                    scene = new Scene(loadFXML("menuInterface"));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                Stage stage = (Stage) loginBtn.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Menu");
            }
            commandFromServer = null;
        });
        new Thread(task).start();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainClass.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void receivedCommandFromServer(Command command){
        commandFromServer = command;
        System.out.println("Command received in controller " + command);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameTF.setOnKeyPressed(new EventHandler<KeyEvent>() {
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
        });
        passwordTF.setOnKeyPressed(new EventHandler<KeyEvent>() {
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
        });
    }
}



