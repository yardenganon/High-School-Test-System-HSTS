package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import com.mysql.cj.xdevapi.Client;
import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClient;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Bundle;
import il.ac.haifa.cs.HSTS.server.CommandInterface.*;
import il.ac.haifa.cs.HSTS.server.Entities.AnswerableTest;
import il.ac.haifa.cs.HSTS.server.Entities.Question;
import il.ac.haifa.cs.HSTS.server.Entities.Student;
import il.ac.haifa.cs.HSTS.server.Entities.User;
import il.ac.haifa.cs.HSTS.server.Status.Status;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class EnterExecutionCodePopup implements Initializable {
    Bundle bundle;
    HSTSClient client;
    User user;
    Response responseFromServer;

    @FXML
    private TextField codeTextField;

    @FXML
    private Label statusLabel;

    @FXML
    private Button enterButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bundle = Bundle.getInstance();
        client = (HSTSClient)bundle.get("client");
        client.getHstsClientInterface().addGUIController(this);
        user = (User) bundle.get("user");
        enterButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("Clicked");
                answerableTestRequest();
            }
        });

    }

    public void answerableTestRequest() {
        Task<Response> task = new Task() {
            @Override
            protected Object call() throws Exception {
                responseFromServer = null;
//                CommandInterface command = new RequestAnswerableTestCommand(codeTextField.getText(),(Student) user);
                CommandInterface command = new AnswerableTestReadCommand(1);
                client.getHstsClientInterface().sendCommandToServer(command);
                System.out.println("Requesting AnswerableTest: "+command);

                while (responseFromServer == null)
                    Thread.onSpinWait();
                System.out.println("loop ended");

                return responseFromServer;
            }
        };
        task.setOnSucceeded(e -> {
            responseFromServer = task.getValue();
            System.out.println(responseFromServer);
            Status status = responseFromServer.getStatus();
            if (status == Status.Success) {
                Scene scene = null;
                AnswerableTest answerableTest =
                        (AnswerableTest) responseFromServer.getReturnedObject();
                bundle.put("answerableTest",answerableTest);
                try {
                    scene = new Scene(MainClass.loadFXML("EnterIdPopup"));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            }


        });
        new Thread(task).start();
    }

    public void receivedResponseFromServer(Response response){
        responseFromServer = response;
        System.out.println("Command received in controller " + response);
    }
}