package il.ac.haifa.cs.HSTS.ocsf.client.Services;

import il.ac.haifa.cs.HSTS.ocsf.client.FXML.MainClass;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class Events {

    public static void navigateLogoutEvent(Button button) {
        Bundle.getInstance().remove("user");
        Scene scene = null;
        try {
            scene = new Scene(MainClass.loadFXML("Login"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) button.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login");
    }

    public static void aboutWindowEvent(){
        Alert editInformation = new Alert(Alert.AlertType.INFORMATION);
        editInformation.setTitle("Information");
        editInformation.setHeaderText("Editing Details");
        editInformation.setContentText("In order to edit a question:\n1. Click on Questions button\n" +
                "2. Double click on a spesific row");
        editInformation.setResizable(true);
        editInformation.getDialogPane().setPrefSize(300, 200);
        Optional<ButtonType> result = editInformation.showAndWait();
    }

    public static void navigateQuestionsEvent(Button button){
        Scene scene = null;
        try {
            scene = new Scene(MainClass.loadFXML("Questions"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) button.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Questions");
    }
    public static void navigateMenuEvent(Button button){
        Scene scene = null;
        try {
            scene = new Scene(MainClass.loadFXML("Menu"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        Stage stage = (Stage) button.getScene().getWindow();
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.setTitle("Menu");
    }

    public static void navigateCreateQuestionEvent(Button button){
        Scene scene = null;
        try {
            scene = new Scene(MainClass.loadFXML("CreateQuestion"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        Stage stage = (Stage) button.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Create Question");
    }

    public static void navigateCreateTestEvent(Button button){
        Scene scene = null;
        try {
            scene = new Scene(MainClass.loadFXML("AddTest"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        Stage stage = (Stage) button.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Create Test");
    }

    public static void navigateTestsEvent(Button button){
        Scene scene = null;
        try {
            scene = new Scene(MainClass.loadFXML("Tests"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        Stage stage = (Stage) button.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Tests");
    }

    public static void navigateCheckingTestsEvent(Button button)
    {
        Scene scene = null;
        try {
            scene = new Scene(MainClass.loadFXML("TestChecking"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        Stage stage = (Stage) button.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Test Checking");
    }

    public static void navigateAnswerableTestsEvent(Button button) {
        Scene scene = null;
        try {
            scene = new Scene(MainClass.loadFXML("TeacherAndPrincipleAnswerableTests"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        Stage stage = (Stage) button.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Teacher And Principle Answerable Tests");
    }

    public static void navigateReadyTestsToPrincipleEvent(Button button) {
        Scene scene = null;
        try {
            scene = new Scene(MainClass.loadFXML("ReadyTestsToPrinciple"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        Stage stage = (Stage) button.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Ready Tests To Principle");
    }
}
