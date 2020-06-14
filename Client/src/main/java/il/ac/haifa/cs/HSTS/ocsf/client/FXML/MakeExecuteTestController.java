package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClient;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Bundle;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.CustomProgressIndicator;
import il.ac.haifa.cs.HSTS.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.server.CommandInterface.CreateReadyTestCommand;
import il.ac.haifa.cs.HSTS.server.CommandInterface.Response;
import il.ac.haifa.cs.HSTS.server.CommandInterface.TestReadByIdCommand;
import il.ac.haifa.cs.HSTS.server.Entities.*;
import il.ac.haifa.cs.HSTS.server.Facade.TestFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class MakeExecuteTestController implements Initializable {

    User user;
    private Test test;
    private int testId;
    TestFacade selectedTest = null;
    private Response responseFromServer = null;
    private static List<Question> questionList = null;
    private ObservableList<QuestionTableView> questionsOL = null;
    private static boolean thereIsAnError = false;
    Bundle bundle;
    private HSTSClient client;
    private int sumOfPoints = 0;
    private String codeErrorMessage = "Execution code should consist of two letters and then two digits";
    private String pointsErrorMessage = "Questions points should sum to 100 or more";
    ReadyTest readyTest;
    Map<Question, Integer> updatedHashMap;
    Teacher teacher;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField authorTextField;

    @FXML
    private ComboBox<String> coursesComboBox;

    @FXML
    private TextField executionCodeTextField;

    @FXML
    private TextField testTimeTextField;

    @FXML
    private Button confirmTestButton1;

    @FXML
    private Label pointsLabel;

    @FXML
    private TableView<QuestionTableView> questionTableView;

    @FXML
    private TableColumn<QuestionTableView, String> columnQuestion;

    @FXML
    private TableColumn<QuestionTableView, String> columnPoints;

    @FXML
    private CheckBox activeCheckBox;

    @FXML
    private CheckBox manualCheckBox;

    @FXML
    private Button acceptTmeExtentionButton;

    @FXML
    private Button rejectTmeExtentionButton;

    public void receivedResponseFromServer(Response response) {
        responseFromServer = response;
        System.out.println("Command received in controller " + response);
    }

    public void initializeQuestionDetails() {
        Teacher teacher;
        teacher = ((Teacher) user);
        coursesComboBox.getItems().clear();

        Course courseForReadyTest = null;
        boolean firstLoop = true;
        for (Course course : teacher.getCourses()) {
            coursesComboBox.getItems().add(course.getCourseName());
            if (firstLoop) {
                courseForReadyTest = course;
                firstLoop = false;
            }
        }

        coursesComboBox.getSelectionModel().selectFirst();
        authorTextField.setText(selectedTest.getTeacherWriter());
        readyTest = new ReadyTest(test, executionCodeTextField.getText(), courseForReadyTest, teacher);
        readyTest.setActive(true);
        readyTest.setManual(false);
        readyTest.setModifiedTime(test.getTime());
        testTimeTextField.setText(String.valueOf(selectedTest.getTime()));
    }

    public void ShowQuestionList() {
        Task<Response> task = new Task<Response>() {
            @Override
            protected Response call() throws Exception {

                // Asking the test from server for showing question test
                CommandInterface command = new TestReadByIdCommand(testId);
                client.getHstsClientInterface().sendCommandToServer(command);

                while (responseFromServer == null) {
                    Thread.sleep(10);
                }

                return responseFromServer;
            }
        };
        task.setOnSucceeded(e -> {
            test = (Test) responseFromServer.getReturnedObject();

            columnQuestion.setCellValueFactory(new PropertyValueFactory<QuestionTableView, String>("question"));
            columnPoints.setCellValueFactory(new PropertyValueFactory<QuestionTableView, String>("points"));
            columnPoints.setCellFactory(TextFieldTableCell.forTableColumn());

            questionsOL = FXCollections.observableArrayList();
            Set<Question> questionList = test.getQuestionSet();

            for (Question quest : questionList) {
                int points = test.getPoints().get(quest);
                questionsOL.add(new QuestionTableView(quest.getQuestion(), String.valueOf(points)));
                sumOfPoints += points;
            }
            questionTableView.setItems(questionsOL);
            pointsLabel.setText(String.valueOf(sumOfPoints));

            initializeQuestionDetails();
        });
        new Thread(task).start();
    }

    public void ResetRedColorAndText(TextField textField) {
        textField.setStyle("-fx-text-inner-color: #000000;");
        textField.setText("");
    }

    public void inputErrorTextField(TextField textField) {
        textField.setText("Invalid input");
        textField.setStyle("-fx-text-inner-color: #ff0000;");
        thereIsAnError = true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bundle = Bundle.getInstance();
        testId = (int) bundle.get("id");
        client = (HSTSClient) bundle.get("client");
        selectedTest = (TestFacade) bundle.get("test");
        user = (User) bundle.get("user");
        teacher = (Teacher) user;
        client.getHstsClientInterface().addGUIController(this);
        ShowQuestionList();
    }

    public void confirmTest(javafx.event.ActionEvent actionEvent) {

        boolean executionCodeError = false;
        boolean pointsSumError = false;

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setHeaderText("Are you sure you want to make that ready test?");
        Optional<ButtonType> result = confirmAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            // Input checking
            thereIsAnError = false;

            if (testTimeTextField.getText().isEmpty())
                inputErrorTextField(testTimeTextField);
            if (executionCodeTextField.getText().isEmpty()) {
                inputErrorTextField(executionCodeTextField);
                executionCodeError = true;
            }
            else executionCodeError = checkExecutionCode();

            if (executionCodeError)
                thereIsAnError = true;

            if (sumOfPoints < 100) {
                thereIsAnError = true;
                pointsSumError = true;
            }
        }

        if (thereIsAnError) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            boolean needToShowAlert = true;

            if (executionCodeError && pointsSumError)
                errorAlert.setHeaderText("1. " + codeErrorMessage + "\n" + "2. " + pointsErrorMessage);
            else if (executionCodeError)
                errorAlert.setHeaderText(codeErrorMessage);
            else if (pointsSumError)
                errorAlert.setHeaderText(pointsErrorMessage);
            else
                needToShowAlert = false;

            if (needToShowAlert)
                errorAlert.showAndWait();
        } else {

            CustomProgressIndicator progressIndicator = new CustomProgressIndicator(anchorPane);
            progressIndicator.start();

            readyTest.setModifiedTime(Integer.parseInt(testTimeTextField.getText()));
            readyTest.setCode(executionCodeTextField.getText());
            readyTest.setActive(activeCheckBox.isSelected());
            readyTest.setManual(manualCheckBox.isSelected());

            Course selectedCourse = null;
            for (Course course : teacher.getCourses())
            {
                if (coursesComboBox.getSelectionModel().getSelectedItem() == course.getCourseName()) {
                    selectedCourse = course;
                    break;
                }
            }
            readyTest.setCourse(selectedCourse);

            Task<Response> task = new Task<Response>() {
                @Override
                protected Response call() throws Exception {
                    responseFromServer = null;

                    CommandInterface command = new CreateReadyTestCommand(readyTest);
                    client.getHstsClientInterface().sendCommandToServer(command);

                    // Waiting for server confirmation
                    while (responseFromServer == null) {
                        Thread.sleep(10);
                    }

                    return responseFromServer;
                }
            };
            task.setOnSucceeded(e -> {
                responseFromServer = task.getValue();
                progressIndicator.stop();

                Alert executeTestCreatedAlert = new Alert(Alert.AlertType.INFORMATION);
                executeTestCreatedAlert.setHeaderText("Ready test was successfully created");

                Optional<ButtonType> resultButton = executeTestCreatedAlert.showAndWait();
                if (resultButton.isPresent() && (resultButton.get() == ButtonType.OK ||
                        resultButton.get() == ButtonType.CANCEL || resultButton.get() == ButtonType.CLOSE ||
                        resultButton.get() == ButtonType.FINISH || resultButton.get() == ButtonType.APPLY ||
                        resultButton.get() == ButtonType.NO))
                {
                    final Stage stage = (Stage) confirmTestButton1.getScene().getWindow();
                    stage.close();
                }

            });
            new Thread(task).start();


        }
    }

    public boolean checkExecutionCode() {
        // Execution code checking
        String executeCode = executionCodeTextField.getText().toUpperCase();
        if (executeCode.length() == 4) {
            for (int i = 0; i < 4; i++) {
                if (i < 2) {
                    if (executeCode.charAt(i) < 'A' || executeCode.charAt(i) > 'Z')
                        return true;
                } else {
                    if (executeCode.charAt(i) < '0' || executeCode.charAt(i) > '9')
                        return true;
                }
            }
            return false;
        }
        else
            return true;
    }


    public void generateExecutionCode(javafx.event.ActionEvent actionEvent) {

        ResetRedColorAndText(executionCodeTextField);
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        String firstChar = String.valueOf(alphabet.charAt((int) (Math.random() * 52)));
        String secondChar = String.valueOf(alphabet.charAt((int) (Math.random() * 52)));
        String firstRandomNum = String.valueOf((int) (Math.random() * (10)));
        String secondRandom = String.valueOf((int) (Math.random() * (10)));

        executionCodeTextField.setText(firstChar + secondChar + firstRandomNum + secondRandom);
    }

    @FXML
    void testTimeOnClick(MouseEvent event) {
        if (testTimeTextField.getText().equals("Invalid input"))
            ResetRedColorAndText(testTimeTextField);
    }

    @FXML
    void executionCodeOnClick(MouseEvent event) {
        if (executionCodeTextField.getText().equals("Invalid input"))
            ResetRedColorAndText(executionCodeTextField);
    }

    public void changePoints(TableColumn.CellEditEvent<QuestionTableView, String> questionTableViewStringCellEditEvent) {
        QuestionTableView changedColumn = questionTableView.getSelectionModel().getSelectedItem();

        sumOfPoints = 0;
        for (Question quest : readyTest.getTest().getQuestionSet())
        {
            if (quest.getQuestion() == changedColumn.getQuestion())
            {
                readyTest.getTest().getPoints().remove(quest);
                readyTest.getTest().getPoints().put(quest, Integer.parseInt(questionTableViewStringCellEditEvent.getNewValue()));
            }
            sumOfPoints += readyTest.getTest().getPoints().get(quest);
        }
        changedColumn.setPoints(questionTableViewStringCellEditEvent.getNewValue());
        pointsLabel.setText(String.valueOf(sumOfPoints));
    }
}
