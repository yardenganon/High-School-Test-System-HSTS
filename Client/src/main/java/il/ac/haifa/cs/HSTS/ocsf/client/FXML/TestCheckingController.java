package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClient;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Bundle;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Events;
import il.ac.haifa.cs.HSTS.server.CommandInterface.Response;
import il.ac.haifa.cs.HSTS.server.Entities.User;
import il.ac.haifa.cs.HSTS.server.Facade.TestFacade;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TestCheckingController implements Initializable {

    public User user;

    private Response responseFromServer = null;
    private static List<TestFacade> testList = null;
    private ObservableList<TestFacade> testsOL = null;
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
    private TableView<?> TestsTableView;

    @FXML
    private TableColumn<?, ?> columnId;

    @FXML
    private TableColumn<?, ?> columnSubject;

    @FXML
    private TableColumn<?, ?> columnCreationDate;

    @FXML
    private TableColumn<?, ?> columnQuestionsNumber;

    @FXML
    private Button editTestButton;

    @FXML
    private Button confirmTestButton;

    @FXML
    private Label numberOfTestsToCheckButton;

    @FXML
    void confirmTestRequest(ActionEvent event) {

    }

    @FXML
    void editTestRequest(ActionEvent event) {
        /*
    }
        //TestFacade selectedTest = TestsTableView.getSelectionModel().getSelectedItem();
        if (selectedTest != null) {
            System.out.println(selectedTest + " Is selected");
            bundle.put("id", selectedTest.getId());
            bundle.put("test", selectedTest);
            bundle.put("client", client);
            bundle.put("user", user);
            Scene scene = new Scene(MainClass.loadFXML("Check "));
            Stage stage = (Stage) MakeExecuteTestButton.getScene().getWindow();
            Stage secondaryStage = new Stage();
            secondaryStage.setScene(scene);
            secondaryStage.setTitle("Make Execute Test");
            secondaryStage.initModality(Modality.APPLICATION_MODAL);
            secondaryStage.show();
            */

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = Bundle.getInstance();
        user = (User) bundle.get("user");
        client = (HSTSClient) bundle.get("client");
        client.getHstsClientInterface().addGUIController(this);
        helloLabel.setText("Hello " + user.getFirst_name());
        initializeTestsTable();
    }


    public void receivedRespondFromServer(Response response) {
        responseFromServer = response;
        System.out.println("Command received in controller " + response);
    }

    @FXML
    void initializeTestsTable() {

    }

    /*
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
                    CommandInterface command = new TestReadBySubjectCommand(subjectSelected);
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
    /*
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
    */

    @FXML
    void about(ActionEvent event) {
        Events.aboutWindowEvent();
    }

    @FXML
    void goToCourses(ActionEvent event) throws IOException {

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
}
