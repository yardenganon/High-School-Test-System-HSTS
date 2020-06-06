package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClient;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Bundle;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.CustomProgressIndicator;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Events;
import il.ac.haifa.cs.HSTS.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.server.CommandInterface.Response;
import il.ac.haifa.cs.HSTS.server.CommandInterface.TestReadByIdCommand;
import il.ac.haifa.cs.HSTS.server.CommandInterface.TestsFacadeReadBySubjectCommand;
import il.ac.haifa.cs.HSTS.server.Entities.*;
import il.ac.haifa.cs.HSTS.server.Facade.TestFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TestsController implements Initializable {

    public User user;

    private Response responseFromServer = null;
    private static List<TestFacade> testList = null;
    private ObservableList<TestFacade> testsOL = null;
    private String subjectSelected = null;
    Test selectedTest = null;
    private Bundle bundle;

    private HSTSClient client;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label helloLabel;

    @FXML
    private Button goToMenuButton;

    @FXML
    private Button goToCoursesButton;

    @FXML
    private Button goToQuestionsButton;

    @FXML
    private Button aboutButton;

    @FXML
    private Button logoutButton;

    @FXML
    private VBox tableViewVbox;

    @FXML
    private TableView<TestFacade> TestsTableView;

    @FXML
    private TableColumn<TestFacade, String> columnId;

    @FXML
    private TableColumn<TestFacade, String> columnSubject;

    @FXML
    private TableColumn<TestFacade, String> columnCreationDate;

    @FXML
    private TableColumn<TestFacade, String> columnAuthor;

    @FXML
    private TableColumn<TestFacade, String> columnQuestionsNumber;

    @FXML
    private TableColumn<TestFacade, String> columnTime;

    @FXML
    private Button addTestButton;

    @FXML
    private ComboBox<String> subjectsComboBox;

    @FXML
    private Button MakeExecuteTestButton;

    @FXML
    void about(ActionEvent event) {
        Events.aboutWindowEvent();
    }

    @FXML
    void goToCourses(ActionEvent event) throws IOException {

    }

    @FXML
    void goToAddTest(ActionEvent event) throws IOException {
        Events.navigateCreateTestEvent(addTestButton);
    }

    @FXML
    void goToMenu(ActionEvent event) throws IOException {
        Events.navigateMenuEvent(goToMenuButton);
    }

    @FXML
    void goToQuestions(ActionEvent event) throws IOException {
        Events.navigateQuestionsEvent(goToQuestionsButton);
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        Events.navigateLogoutEvent(logoutButton);
    }

    @FXML
    void initializeTestsTable() {
        refreshList();

        TestsTableView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                CustomProgressIndicator progressIndicator = new CustomProgressIndicator(anchorPane);
                //progressIndicator.start();
                TestFacade testSelected = TestsTableView.getSelectionModel().getSelectedItem();
                if (testSelected != null && event.getClickCount() == 2) {
                    Scene scene = null;
                    for (TestFacade test : testList) {
                        if (test.getId() == testSelected.getId()) {
                            testList = new ArrayList<TestFacade>();
                            Task<Response> task = new Task<Response>() {
                                @Override
                                protected Response call() throws Exception {
                                    if (user instanceof Teacher) {
                                        responseFromServer = null;
                                        CommandInterface command = new TestReadByIdCommand(test.getId());
                                        client.getHstsClientInterface().sendCommandToServer(command);

                                        while (responseFromServer == null)
                                            Thread.sleep(10);

                                    } else if (user instanceof Principle) {
                                   /* responseFromServer = null;

                                    CommandInterface command = new TestReadAllCommand();
                                    client.getHstsClientInterface().sendCommandToServer(command);

                                    while (responseFromServer == null)
                                        Thread.sleep(10);

                                    testList = (List<Test>) responseFromServer.getReturnedObject();*/
                                    }
                                    return responseFromServer;
                                }
                            };
                            task.setOnSucceeded(e -> {
                                responseFromServer = task.getValue();
                                selectedTest = (Test) responseFromServer.getReturnedObject();
                                openTestDetailsWindow();
                                //progressIndicator.stop();
                            });
                            new Thread(task).start();
                        }
                    }
                }
            }
        });
    }

    public void receivedRespondFromServer(Response response) {
        responseFromServer = response;
        System.out.println("Command received in controller " + response);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = Bundle.getInstance();
        client = (HSTSClient) bundle.get("client");
        user = (User) bundle.get("user");
        System.out.println(user);
        client.getHstsClientInterface().addGUIController(this);
        initializeUser();
        initializeSubjectsComboBox();
        initializeTestsTable();
    }

    public void initializeUser() {
        helloLabel.setText("Hello " + user.getFirst_name());
    }

    public void initializeSubjectsComboBox() {
        if (user instanceof Teacher) {
            Teacher teacher = ((Teacher) user);
            subjectsComboBox.getItems().clear();
            for (Subject subject : teacher.getSubjects())
                subjectsComboBox.getItems().add(subject.getSubjectName());
        }
        subjectsComboBox.getSelectionModel().select(subjectsComboBox.getItems().get(0));
    }

    public void openTestDetailsWindow() {
        System.out.println(selectedTest + " Is selected");
        bundle.put("test", selectedTest);
        Scene scene = null;
        try {
            scene = new Scene(MainClass.loadFXML("TestDetails"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) TestsTableView.getScene().getWindow();
        Stage secondaryStage = new Stage();
        secondaryStage.setScene(scene);
        secondaryStage.setTitle("Test Details");
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.show();
        secondaryStage.setOnCloseRequest((WindowEvent event1) -> {
            refreshList();
        });
    }

    public void refreshList() {
        CustomProgressIndicator progressIndicator = new CustomProgressIndicator(anchorPane);
        progressIndicator.start();
        testList = new ArrayList<TestFacade>();
        Task<Response> task = new Task<Response>() {
            @Override
            protected Response call() throws Exception {
                if (user instanceof Teacher) {
                    subjectSelected = subjectsComboBox.getSelectionModel().getSelectedItem();
                    responseFromServer = null;
                    CommandInterface command = new TestsFacadeReadBySubjectCommand(subjectSelected);
                    client.getHstsClientInterface().sendCommandToServer(command);

                    while (responseFromServer == null)
                        Thread.sleep(10);

                } else if (user instanceof Principle) {
                   /* responseFromServer = null;

                    CommandInterface command = new TestReadAllCommand();
                    client.getHstsClientInterface().sendCommandToServer(command);

                    while (responseFromServer == null)
                        Thread.sleep(10);

                    testList = (List<Test>) responseFromServer.getReturnedObject();*/
                }
                return responseFromServer;
            }
        };
        task.setOnSucceeded(e -> {
            responseFromServer = task.getValue();
            progressIndicator.stop();
            testList = (List<TestFacade>) responseFromServer.getReturnedObject();
            columnId.setCellValueFactory(new PropertyValueFactory<TestFacade, String>("id"));
            columnSubject.setCellValueFactory(new PropertyValueFactory<TestFacade, String>("subject"));
            columnCreationDate.setCellValueFactory(new PropertyValueFactory<TestFacade, String>("dateCreated"));
            columnAuthor.setCellValueFactory(new PropertyValueFactory<TestFacade, String>("teacherWriter"));
            columnQuestionsNumber.setCellValueFactory(new PropertyValueFactory<TestFacade, String>("numberOfQuestions"));
            columnTime.setCellValueFactory(new PropertyValueFactory<TestFacade, String>("time"));

            testsOL = FXCollections.observableArrayList();
            for (TestFacade test : testList) {
                testsOL.add(new TestFacade(test.getId(), test.getTeacherWriter(),
                        test.getSubject(), test.getDateCreated(), test.getNumberOfQuestions(),
                        test.getTime()));
            }

            TestsTableView.setItems(testsOL);
        });
        new Thread(task).start();
    }

    @FXML
    void subjectSelect(ActionEvent event) {
        refreshList();
    }

    public void MakeExecuteTest(ActionEvent actionEvent) throws IOException {

        TestFacade selectedTest = TestsTableView.getSelectionModel().getSelectedItem();
        if (selectedTest != null) {
            System.out.println(selectedTest + " Is selected");
            bundle.put("id", selectedTest.getId());
            bundle.put("test", selectedTest);
            bundle.put("client", client);
            bundle.put("user", user);
            Scene scene = new Scene(MainClass.loadFXML("MakeExecuteTest"));
            Stage stage = (Stage) MakeExecuteTestButton.getScene().getWindow();
            Stage secondaryStage = new Stage();
            secondaryStage.setScene(scene);
            secondaryStage.setTitle("Make Execute Test");
            secondaryStage.initModality(Modality.APPLICATION_MODAL);
            secondaryStage.show();
        }
        else
        {
            Alert needChooseTestAlert = new Alert(Alert.AlertType.ERROR);
            needChooseTestAlert.setHeaderText("For making execute test you need to select a test and then push \"Make Execute Text\" button" );
            Optional<ButtonType> result = needChooseTestAlert.showAndWait();
        }
    }

    public void editTestRequest(ActionEvent actionEvent) {
    }

    public void confirmTestRequest(ActionEvent actionEvent) {
    }
}