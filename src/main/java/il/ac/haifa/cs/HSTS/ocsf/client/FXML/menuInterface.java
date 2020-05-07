/**
 * Sample Skeleton for 'menuInterface.fxml' Controller Class
 */

package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import java.io.IOException;

//import com.gluonhq.charm.glisten.control.Icon;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class menuInterface extends Application {

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



    }
    
    @FXML
    void logout(ActionEvent event) throws IOException{
    	MainClass.setRoot("loginInterface");
    }


    @Override
    public void start(Stage stage) throws Exception {

    }
}
