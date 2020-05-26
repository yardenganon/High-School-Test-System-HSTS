/**
 * Sample Skeleton for 'Menu.fxml' Controller Class
 */

package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClient;
import il.ac.haifa.cs.HSTS.server.Entities.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    private HSTSClient client;
    public User user;

    @FXML
    private Label helloLabel;

    @FXML
    private Button coursesButton;

    @FXML
    private Button showQuestionButton;

    @FXML
    private Button goToTestsButton;

    @FXML
    private Button aboutButton;

    @FXML
    private Button logoutButton;

    @FXML
    void goToTests(ActionEvent event) {

    }

    @FXML
    void about(ActionEvent event) {
        Alert editInformation = new Alert(Alert.AlertType.INFORMATION);
        editInformation.setTitle("Information");
        editInformation.setHeaderText("Editing Details");
        editInformation.setContentText("In order to edit a question:\n1. Click on Questions button\n" +
                "2. Double click on a spesific row");
        editInformation.setResizable(true);
        editInformation.getDialogPane().setPrefSize(300, 200);
        Optional<ButtonType> result = editInformation.showAndWait();
    }

    @FXML
    void showQuestions(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Questions.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Questions");
        QuestionsController qc = loader.<QuestionsController>getController();
        qc.passHSTSClientReference(client);
        qc.setUser(user);
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login");

        LoginController qc = loader.<LoginController>getController();
        qc.passHSTSClientReference(client);
        qc.setUser(user);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainClass.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        helloLabel.setText("Hello " + user.getFirst_name());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void passHSTSClientReference(HSTSClient ref) {
        this.client = ref;
        ref.getHstsClientInterface().addGUIController(this);
    }
}
