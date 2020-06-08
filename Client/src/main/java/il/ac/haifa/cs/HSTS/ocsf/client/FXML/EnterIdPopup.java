package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClient;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Bundle;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.CustomProgressIndicator;
import il.ac.haifa.cs.HSTS.server.CommandInterface.AnswerableTestUpdateCommand;
import il.ac.haifa.cs.HSTS.server.Entities.AnswerableTest;
import il.ac.haifa.cs.HSTS.server.Entities.Student;
import il.ac.haifa.cs.HSTS.server.Entities.User;
import il.ac.haifa.cs.HSTS.server.Status.Status;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EnterIdPopup implements Initializable {

    @FXML
    private TextField idNumberTextField;

    @FXML
    private Label statusLabel;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button enterIdNumberButton;

    Bundle bundle = null;
    HSTSClient client = null;
    Student user = null;
    AnswerableTest answerableTest = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bundle = Bundle.getInstance();
        client = (HSTSClient) bundle.get("client");
        user = (Student) bundle.get("user");
        System.out.println(user);
        client.getHstsClientInterface().addGUIController(this);
        // CodeExecutePopup put answerableTest in Bundle
        answerableTest = (AnswerableTest) bundle.get("answerableTest");

        EventHandler<KeyEvent> nextEvent = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER)
                    startTestEvent();
            }
        };
        idNumberTextField.setOnKeyPressed(nextEvent);
    }

    public void startTestEvent() {
        Status status = checkIdNumber();
        if (status.equals(Status.Success)){
            Scene scene = null;
            try {
                scene = new Scene(MainClass.loadFXML("TestInProgress"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) statusLabel.getScene().getWindow();
            stage.setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Test is closed, creating a backup...");
                            TestInProgressController.TestTimerTask testTimerTask =
                                    ((TestInProgressController.TestTimerTask) bundle.get("testTimerTask"));
                            if (testTimerTask !=null)
                                testTimerTask.cancel();
                            bundle.remove("testTimerTask");
                            // backup\sent update answerableTest to server
                            AnswerableTest answerableTest = (AnswerableTest) bundle.get("answerableTest");
                            AnswerableTestUpdateCommand command = new AnswerableTestUpdateCommand(answerableTest);
                            client.getHstsClientInterface().sendCommandToServer(command);
                        }
                    });
                }
            });
            if (scene != null) {
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
                Stage menuStage = (Stage) bundle.get("menuStage");
                menuStage.close();
                bundle.remove("menuStage");
            }
        }
        else
            statusLabel.setText("Wrong ID number");

    }
    public Status checkIdNumber(){
        return (idNumberTextField.getText().equals(String.valueOf(user.getIdNumber())) ?
                Status.Success : Status.InvalidCode);
    }
}
