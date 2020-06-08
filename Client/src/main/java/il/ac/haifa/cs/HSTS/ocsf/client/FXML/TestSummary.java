package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.ocsf.client.Services.Bundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class TestSummary {

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

}
