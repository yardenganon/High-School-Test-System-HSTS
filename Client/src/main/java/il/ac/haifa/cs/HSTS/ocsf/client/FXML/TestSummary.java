package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.ocsf.client.Services.Bundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TestSummary implements Initializable {

    @FXML
    private Button returnToMenuBtn;

    @FXML
    void returnToMenuEvent(ActionEvent event) {
        Stage stage = (Stage) returnToMenuBtn.getScene().getWindow();
        Scene scene = null;
        try {
            scene = new Scene(MainClass.loadFXML("Menu"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.centerOnScreen();
        Bundle bundle = Bundle.getInstance();
        Stage testInProgStage = (Stage) bundle.get("testInProgressStage");
        bundle.remove("testInProgressStage");
        testInProgStage.close();
        stage.show();


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
