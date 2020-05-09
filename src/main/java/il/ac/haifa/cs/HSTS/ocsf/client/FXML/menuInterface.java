/**
 * Sample Skeleton for 'menuInterface.fxml' Controller Class
 */

package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import il.ac.haifa.cs.HSTS.Command;
import il.ac.haifa.cs.HSTS.HSTSClientInterface;
import il.ac.haifa.cs.HSTS.ocsf.server.ConnectionToClient;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Question;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Subject;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Teacher;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class menuInterface{
    /**
     * Sample Skeleton for 'menuInterface.fxml' Controller Class
     */

    private static User user = null;
    private static Command commandFromServer = null;

    @FXML // fx:id="helloLB"
    private Label helloLB; // Value injected by FXMLLoader

    @FXML // fx:id="Showbtn"
    private Button Showbtn; // Value injected by FXMLLoader

    @FXML // fx:id="logoutBtn"
    private Button logoutBtn; // Value injected by FXMLLoader

    @FXML // fx:id="tableVB"
    private VBox tableVB; // Value injected by FXMLLoader

    @FXML // fx:id="tableGP"
    private GridPane tableGP; // Value injected by FXMLLoader

    @FXML // fx:id="tableV"
    private TableView<?> tableV; // Value injected by FXMLLoader

    @FXML // fx:id="searchTF"
    private TextField searchTF; // Value injected by FXMLLoader

    @FXML // fx:id="searchbtn"
    private Button searchbtn; // Value injected by FXMLLoader

    @FXML // fx:id="searchIV"
    private ImageView searchIV; // Value injected by FXMLLoader

    @FXML
    void search(ActionEvent event) {
        System.out.println("search is being");
    }

    @FXML
    void Show(ActionEvent event) {
    	searchbtn.setVisible(true);
    	searchTF.setVisible(true);
    	tableGP.setVisible(true);
    	tableVB.setVisible(true);
    	tableV.setVisible(true);
    	searchbtn.setVisible(true);
    	searchTF.setVisible(true);
    	searchIV.setVisible(true);


        // Creating command here and send it to client
        System.out.println("hello menu" + user);
        List<Question> questsOfTeacher = new ArrayList<Question>();
        Teacher teacher = (Teacher) user;
        List<Subject> subjects= new ArrayList<Subject>();
        subjects = teacher.getSubjects();
        for (Subject subject : subjects)
            questsOfTeacher.addAll(subject.getQuestions());
        System.out.println("hello menu" + questsOfTeacher);
       // Command command = new Command("readBySubject","questions", subjectsOfTeacher);
        System.out.println("hello menu" + ((Teacher) user).getSubjects());
        //HSTSClientInterface.sendCommandToServer(command);

        while (commandFromServer == null){
            System.out.print("");
        }

        System.out.println(commandFromServer);
        if (commandFromServer.getReturnedObject() != null) {
            commandFromServer = null;
        }
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

    public static void receivedCommandFromServer(Command command){
        commandFromServer = command;
        System.out.println("Command received in controller " + command);
    }

    public User getUser() {
        return user;
    }

    public static void setUser(User user) {
        menuInterface.user = user;
    }


}
