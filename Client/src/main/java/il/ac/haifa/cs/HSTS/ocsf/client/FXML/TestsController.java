package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClient;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Bundle;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.CustomProgressIndicator;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Events;
import il.ac.haifa.cs.HSTS.server.CommandInterface.*;
import il.ac.haifa.cs.HSTS.server.Entities.*;
import il.ac.haifa.cs.HSTS.server.Facade.TestFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private List<Subject> subjectList = null;
    public boolean flagOfDetails = false;
    private TestFacade testSelected = null;
    public Button watchTestButton;
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
    private Button editTestButton;

    @FXML
    private ComboBox<String> subjectsComboBox;

    @FXML
    private Button MakeExecuteTestButton;

    @FXML
    void goToAddTest(ActionEvent event) throws IOException {
        if(user instanceof Teacher)
            Events.navigateCreateTestEvent(addTestButton);
        else{
            Alert permissionsErrorAlert = new Alert(Alert.AlertType.ERROR);
            permissionsErrorAlert.setHeaderText("You don't have the permissions to create a test");
            permissionsErrorAlert.showAndWait();
        }
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
        //refreshList();
    }

    public void receivedResponseFromServer(Response response) {
        responseFromServer = response;
        System.out.println("Command received in controller " + response);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = Bundle.getInstance();
        client = (HSTSClient) bundle.get("client");
        user = (User) bundle.get("user");
        System.out.println(user);
        client.getHstsClientInterface().getGuiControllers().clear();
        client.getHstsClientInterface().addGUIController(this);
        initializeUser();
        initializeSubjectsComboBox();
        initializeTestsTable();
        if (user instanceof Teacher){
            editTestButton.setVisible(true);
            addTestButton.setVisible(true);
            MakeExecuteTestButton.setVisible(true);
        }
        if (user instanceof Principle){
            editTestButton.setVisible(false);
            addTestButton.setVisible(false);
            MakeExecuteTestButton.setVisible(false); }
    }

    public void initializeUser() {
        helloLabel.setText("Hello " + user.getFirst_name());
    }

    public void initializeSubjectsComboBox() {
        subjectsComboBox.getItems().add("Select subject");
        if (user instanceof Teacher) {
            Teacher teacher = ((Teacher) user);
            for (Subject subject : teacher.getSubjects())
                subjectsComboBox.getItems().add(subject.getSubjectName());
            subjectsComboBox.getSelectionModel().select(subjectsComboBox.getItems().get(0));
        }
        if (user instanceof Principle){
            getAllSubjects();
        }
    }

    public void openTestDetailsWindow() {
        if (selectedTest != null) {
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
                selectedTest = null;
            });
        }
    }

    public void getAllSubjects(){
        CustomProgressIndicator progressIndicator = new CustomProgressIndicator(anchorPane);
        progressIndicator.start();
        Task<Response> task = new Task<Response>() {
            @Override
            protected Response call() throws Exception {
                responseFromServer = null;
                CommandInterface command = new SubjectReadAllCommand();
                client.getHstsClientInterface().sendCommandToServer(command);

                while (responseFromServer == null)
                    Thread.sleep(10);
                return responseFromServer;
            }
        };
        task.setOnSucceeded(e -> {
            responseFromServer = task.getValue();
            progressIndicator.stop();
            System.out.println("hello");
            subjectList = (List<Subject>) responseFromServer.getReturnedObject();
            for (Subject sub : subjectList)
                subjectsComboBox.getItems().add(sub.getSubjectName());
            subjectsComboBox.getSelectionModel().select(subjectsComboBox.getItems().get(0));
        });
        new Thread(task).start();
    }

    public void refreshList() {
        CustomProgressIndicator progressIndicator = new CustomProgressIndicator(anchorPane);
        progressIndicator.start();
        Task<Response> task = new Task<Response>() {
            @Override
            protected Response call() throws Exception {
                subjectSelected = subjectsComboBox.getSelectionModel().getSelectedItem();
                responseFromServer = null;
                CommandInterface command = new TestsFacadeReadBySubjectCommand(subjectSelected);
                client.getHstsClientInterface().sendCommandToServer(command);

                while (responseFromServer == null)
                    Thread.sleep(10);

                return responseFromServer;
            }
        };
        task.setOnSucceeded(e -> {
            responseFromServer = task.getValue();
            progressIndicator.stop();
            System.out.println("hello");
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
            secondaryStage.setOnCloseRequest((WindowEvent event1) -> {
                refreshList();
            });
        }
        else
        {
            Alert needChooseTestAlert = new Alert(Alert.AlertType.ERROR);
            needChooseTestAlert.setHeaderText("In order to execute a test you need to select a test and then press \"Make Execute Test\" button" );
            Optional<ButtonType> result = needChooseTestAlert.showAndWait();
        }
    }

    public void editTestRequest(ActionEvent actionEvent) throws IOException {
        testSelected = TestsTableView.getSelectionModel().getSelectedItem();
        if (testSelected != null) {
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
                                responseFromServer = null;

                                CommandInterface command = new TestReadAllCommand();
                                client.getHstsClientInterface().sendCommandToServer(command);

                                while (responseFromServer == null)
                                    Thread.sleep(10);

                                testList = (List<TestFacade>) responseFromServer.getReturnedObject();
                            }
                            return responseFromServer;
                        }
                    };
                    task.setOnSucceeded(e -> {
                        responseFromServer = task.getValue();
                        selectedTest = (Test) responseFromServer.getReturnedObject();
                        System.out.println(selectedTest + " Is selected");
                        bundle.put("test", selectedTest);
                        bundle.put("client", client);
                        bundle.put("user", user);
                        bundle.put("update", true);

                        if (user instanceof Teacher) {
                            Events.navigateCreateTestEvent(editTestButton);
                        }
                        else
                        {
                            Alert needChooseTestAlert = new Alert(Alert.AlertType.ERROR);
                            needChooseTestAlert.setHeaderText("You doesn't have the permissions to edit a test" );
                            Optional<ButtonType> result = needChooseTestAlert.showAndWait();
                        }
                        selectedTest = null;
                    });
                    new Thread(task).start();
                }
            }
        }
        else
        {
            Alert needChooseTestAlert = new Alert(Alert.AlertType.ERROR);
            needChooseTestAlert.setHeaderText("In order to edit a test you need to select a test and then press \"Edit Test\" button" );
            Optional<ButtonType> result = needChooseTestAlert.showAndWait();
        }
    }

    @FXML
    void watchTest(ActionEvent event) {
        testSelected = TestsTableView.getSelectionModel().getSelectedItem();
        if (testSelected != null) {
            for (TestFacade test : testList) {
                if (test.getId() == testSelected.getId()) {
                    testList = new ArrayList<TestFacade>();
                    Task<Response> task = new Task<Response>() {
                        @Override
                        protected Response call() throws Exception {
                            responseFromServer = null;
                            CommandInterface command = new TestReadByIdCommand(test.getId());
                            client.getHstsClientInterface().sendCommandToServer(command);

                            while (responseFromServer == null)
                                Thread.sleep(10);
                            return responseFromServer;
                        }
                    };
                    task.setOnSucceeded(e -> {
                        responseFromServer = task.getValue();
                        selectedTest = (Test) responseFromServer.getReturnedObject();
                        openTestDetailsWindow();
                    });
                    new Thread(task).start();
                }
            }
        }
        else
        {
            Alert needChooseTestAlert = new Alert(Alert.AlertType.ERROR);
            needChooseTestAlert.setHeaderText("In order to watch a test you need to select a test and then press \"Watch Test\" button" );
            Optional<ButtonType> result = needChooseTestAlert.showAndWait();
        }
    }
}