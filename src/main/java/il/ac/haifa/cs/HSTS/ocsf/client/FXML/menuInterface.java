/**
 * Sample Skeleton for 'menuInterface.fxml' Controller Class
 */

package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//import com.gluonhq.charm.glisten.control.Icon;

import il.ac.haifa.cs.HSTS.ocsf.server.ConnectionToClient;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.User;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class menuInterface{
    /**
     * Sample Skeleton for 'menuInterface.fxml' Controller Class
     */

    private static User user = null;

    @FXML // fx:id="helloLB"
    private Label helloLB; // Value injected by FXMLLoader

    @FXML // fx:id="logoutbtn"
    private Button logoutBtn; // Value injected by FXMLLoader

    @FXML // fx:id="Showbtn"
    private Button Showbtn; // Value injected by FXMLLoader

    @FXML // fx:id="tablev"
    private TableView<?> tablev; // Value injected by FXMLLoader

    @FXML // fx:id="searchTF"
    private TextField searchTF; // Value injected by FXMLLoader

    @FXML // fx:id="searchbtn"
    private Button searchbtn; // Value injected by FXMLLoader

    //@FXML // fx:id="searchicon"
    //private Icon searchicon; // Value injected by FXMLLoader

    @FXML
    void Show(ActionEvent event) {
    	searchbtn.setVisible(true);
    	searchTF.setVisible(true);
    	tablev.setVisible(true);

        // Creating command here and send it to client
       /* Command command = new Command("login","users",
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
        }*/


    }
    
    @FXML
    void logout(ActionEvent event) throws IOException{
        Scene scene = new Scene(loadFXML("loginInterface"));
        Stage stage = (Stage) logoutBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login");
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainClass.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public User getUser() {
        return user;
    }

    public static void setUser(User user) {
        menuInterface.user = user;
    }


}
