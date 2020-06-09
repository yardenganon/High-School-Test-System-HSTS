/**
 * Sample Skeleton for 'Menu.fxml' Controller Class
 */

package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClient;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Bundle;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.CustomProgressIndicator;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Events;
import il.ac.haifa.cs.HSTS.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.server.CommandInterface.ReadyTestFacadeReadByTeacherCommand;
import il.ac.haifa.cs.HSTS.server.CommandInterface.Response;
import il.ac.haifa.cs.HSTS.server.Entities.*;
import il.ac.haifa.cs.HSTS.server.Facade.ReadyTestFacade;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    private HSTSClient client;
    public User user;
    private Teacher teacher;
    private Principle principle;
    Bundle bundle;
    private Response responseFromServer = null;
    private ObservableList<TimeExtensionRequestTableView> questionsOL = null;
    List<ReadyTestFacade> readyTestFacadeList;
    private ReadyTest currentReadyTest;

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
    private AnchorPane studentMenu;

    @FXML
    private Button enterCodeButton;

    @FXML
    private Button logoutButton;
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button checkingTestsButton;

    @FXML
    private Pane teacherPane;

    @FXML
    private Pane principlePane;

    @FXML
    private Button submitExtensionTimeRequestButton;

    @FXML
    private TableView<TimeExtensionRequestTableView> activeTestsTebleView;

    @FXML
    private TableColumn<TimeExtensionRequestTableView, Integer> columnId;

    @FXML
    private TableColumn<TimeExtensionRequestTableView, String> columnCourse;

    @FXML
    private TableColumn<TimeExtensionRequestTableView, String> columnTimeExtension;

    @FXML
    private TableColumn<TimeExtensionRequestTableView, String> columnTimeExtensionReason;

    @FXML
    private TableColumn<TimeExtensionRequestTableView, String> columnStatus;

    @FXML
    private TableView<TimeExtensionRequestTableView> timeExtensionRequstForPrincipleTV;

    @FXML
    private TableColumn<TimeExtensionRequestTableView, Integer> courseNamePrincipleTV;

    @FXML
    private TableColumn<TimeExtensionRequestTableView, String> teacherNamePrincipleTV;

    @FXML
    private TableColumn<TimeExtensionRequestTableView, String> timeExtensionPrincipleTV;

    @FXML
    private TableColumn<TimeExtensionRequestTableView, String> descriptionPrincipleTV;

    @FXML
    private Button acceptTmeExtensionButton;

    @FXML
    private Button rejectTmeExtensionButton;

    @FXML
    void goToTests(ActionEvent event) throws IOException {
        Events.navigateTestsEvent(goToTestsButton);
    }

    @FXML
    void about(ActionEvent event) {
        Events.aboutWindowEvent();
    }

    @FXML
    void showQuestions(ActionEvent event) throws IOException {
        Events.navigateQuestionsEvent(showQuestionButton);
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        Events.navigateLogoutEvent(logoutButton);
    }


    @FXML
    void goToCheckingTests(ActionEvent event) {
        bundle.put("user", user);
        bundle.put("client", client);
        Events.navigateCheckingTestsEvent(checkingTestsButton);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = Bundle.getInstance();
        user = (User) bundle.get("user");
        client = (HSTSClient) bundle.get("client");
        client.getHstsClientInterface().getGuiControllers().clear();
        client.getHstsClientInterface().addGUIController(this);
        helloLabel.setText("Hello " + user.getFirst_name());

        if (user instanceof Teacher) {
            teacher = (Teacher) user;
            InitTeacherMenu(teacher);
            teacherPane.setVisible(true);
        }
        else if (user instanceof Student)
            initStudentMenu();
        else if (user instanceof Principle)
        {
            principle = (Principle) user;
            InitPrincipleMenu(teacher);
            principlePane.setVisible(true);
        }
    }

    private void InitPrincipleMenu(Teacher teacher) {

            // update time extension request table
            responseFromServer = null;
            Task<Response> task = new Task<Response>() {
                @Override
                protected Response call() throws Exception {
                    //CommandInterface command = new ReadyTestFacadeReadByTeacherCommand(teacher.getId());
                    //client.getHstsClientInterface().sendCommandToServer(command);

                    // Waiting for server confirmation
                    while (responseFromServer == null) {
                        Thread.onSpinWait();
                    }
                    return responseFromServer;
                }
            };
            task.setOnSucceeded(e -> {
                responseFromServer = task.getValue();

                courseNamePrincipleTV.setCellValueFactory(new PropertyValueFactory<TimeExtensionRequestTableView, Integer>("courseName"));
                teacherNamePrincipleTV.setCellValueFactory(new PropertyValueFactory<TimeExtensionRequestTableView, String>("teacherUserName"));
                timeExtensionPrincipleTV.setCellValueFactory(new PropertyValueFactory<TimeExtensionRequestTableView, String>("timeExtension"));
                descriptionPrincipleTV.setCellValueFactory(new PropertyValueFactory<TimeExtensionRequestTableView, String>("timeExtensionReason"));

                //List<ReadyTestFacade> readyTestFacadeList = (List<ReadyTestFacade>) responseFromServer.getReturnedObject();

                questionsOL = FXCollections.observableArrayList();

                //for (ReadyTestFacade readyTestFacade : readyTestFacadeList) {
                //    if (readyTestFacade.getActive() == true)
                //        questionsOL.add(new TimeExtensionRequestTableView(readyTestFacade.getId(), readyTestFacade.getCourseName()));
                //}
                activeTestsTebleView.getItems().addAll(questionsOL);
            });
            new Thread(task).start();
    }

    private void InitTeacherMenu(Teacher teacher) {

        // update the active tests table
        responseFromServer = null;
        Task<Response> task = new Task<Response>() {
            @Override
            protected Response call() throws Exception {
                CommandInterface command = new ReadyTestFacadeReadByTeacherCommand(teacher.getId());
                client.getHstsClientInterface().sendCommandToServer(command);

                // Waiting for server confirmation
                while (responseFromServer == null) {
                    Thread.onSpinWait();
                }
                return responseFromServer;
            }
        };
        task.setOnSucceeded(e -> {
            responseFromServer = task.getValue();

            columnId.setCellValueFactory(new PropertyValueFactory<TimeExtensionRequestTableView, Integer>("testId"));
            columnCourse.setCellValueFactory(new PropertyValueFactory<TimeExtensionRequestTableView, String>("courseName"));
            columnTimeExtension.setCellValueFactory(new PropertyValueFactory<TimeExtensionRequestTableView, String>("timeExtension"));
            columnTimeExtensionReason.setCellValueFactory(new PropertyValueFactory<TimeExtensionRequestTableView, String>("timeExtensionReason"));
            columnStatus.setCellValueFactory(new PropertyValueFactory<TimeExtensionRequestTableView, String>("status"));
            columnTimeExtensionReason.setCellFactory(TextFieldTableCell.forTableColumn());
            columnTimeExtension.setCellFactory(TextFieldTableCell.forTableColumn());

            readyTestFacadeList = (List<ReadyTestFacade>) responseFromServer.getReturnedObject();
            questionsOL = FXCollections.observableArrayList();

            for (ReadyTestFacade readyTestFacade : readyTestFacadeList) {
                if (readyTestFacade.getActive() == true)
                    questionsOL.add(new TimeExtensionRequestTableView(readyTestFacade.getId(), readyTestFacade.getCourseName()));
            }
            activeTestsTebleView.getItems().addAll(questionsOL);
        });
        new Thread(task).start();
    }

    public void initStudentMenu() {
        studentMenu.setVisible(true);
        enterCodeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            Scene scene = null;
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    scene = new Scene(MainClass.loadFXML("EnterExecutionCodePopup"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage menuStage = (Stage) helloLabel.getScene().getWindow();
                bundle.put("menuStage",menuStage);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.show();
            }
        });
    }

    public void submitExtensionTimeRequestOfTeacher(ActionEvent actionEvent) {
        TimeExtensionRequestTableView timeExtensionRequest = activeTestsTebleView.getSelectionModel().getSelectedItem();

        if (timeExtensionRequest == null)
        {
            Alert missingDetailsAlert = new Alert(Alert.AlertType.ERROR);
            missingDetailsAlert.setHeaderText("For submit time extension request you need to choose test and then press \"Submit Request\" button");
            missingDetailsAlert.showAndWait();
        }
        else if (timeExtensionRequest.getTimeExtension().isEmpty() || timeExtensionRequest.getTimeExtensionReason().isEmpty())
        {
            Alert missingDetailsAlert = new Alert(Alert.AlertType.ERROR);
            missingDetailsAlert.setHeaderText("Time extension or the reason is missing");
            missingDetailsAlert.showAndWait();
        }
        else
        {

            // Request for ready test
            getReadyTest(timeExtensionRequest.getTestId());

            CustomProgressIndicator progressIndicator = new CustomProgressIndicator(anchorPane);
            progressIndicator.start();

            responseFromServer = null;
            Task<Response> task = new Task<Response>() {
                @Override
                protected Response call() throws Exception {

                    CommandInterface command = new ReadyTestFacadeReadByTeacherCommand(new TimeExtensionRequest(teacher, currentReadyTest, timeExtensionRequest.getTimeExtensionReason());
                    client.getHstsClientInterface().sendCommandToServer(command);

                    // Waiting for server confirmation
                    while (responseFromServer == null) {
                        Thread.onSpinWait();
                    }
                    return responseFromServer;
                }
            };
            task.setOnSucceeded(e -> {
                responseFromServer = task.getValue();

                timeExtensionRequest.setStatus("submitted");
                progressIndicator.stop();
            });
            new Thread(task).start();
        }

    }

    public void getReadyTest(int id)
    {
        responseFromServer = null;
        Task<Response> task = new Task<Response>() {
            @Override
            protected Response call() throws Exception {

                CommandInterface command = new ReadyTestByID(id);
                client.getHstsClientInterface().sendCommandToServer(command);

                // Waiting for server confirmation
                while (responseFromServer == null) {
                    Thread.onSpinWait();
                }
                return responseFromServer;
            }
        };
        task.setOnSucceeded(e -> {
            responseFromServer = task.getValue();
            currentReadyTest = (ReadyTest) responseFromServer.getReturnedObject();
        });
        new Thread(task).start();
    }

    public void receivedResponseFromServer(Response response) {
        responseFromServer = response;
        System.out.println("Command received in controller " + response);
    }


    public void onTimeExtensionWriting(TableColumn.CellEditEvent<TimeExtensionRequestTableView, String> timeExtensionRequestTableViewStringCellEditEvent) {
        TimeExtensionRequestTableView changedColumn = activeTestsTebleView.getSelectionModel().getSelectedItem();
        changedColumn.setTimeExtension(timeExtensionRequestTableViewStringCellEditEvent.getNewValue());
    }

    public void onTimeExtensionReasonWriting(TableColumn.CellEditEvent<TimeExtensionRequestTableView, String> timeExtensionRequestTableViewStringCellEditEvent) {
        TimeExtensionRequestTableView changedColumn = activeTestsTebleView.getSelectionModel().getSelectedItem();
        changedColumn.setTimeExtensionReason(timeExtensionRequestTableViewStringCellEditEvent.getNewValue());
    }

    public void AcceptTmeExtension(ActionEvent actionEvent) {
        CustomProgressIndicator progressIndicator = new CustomProgressIndicator(anchorPane);
        progressIndicator.start();

        responseFromServer = null;
        Task<Response> task = new Task<Response>() {
            @Override
            protected Response call() throws Exception {

                //CommandInterface command = new ReadyTestFacadeReadByTeacherCommand(teacher.getId());
                //client.getHstsClientInterface().sendCommandToServer(command);

                // Waiting for server confirmation
                while (responseFromServer == null) {
                    Thread.onSpinWait();
                }
                return responseFromServer;
            }
        };
        task.setOnSucceeded(e -> {
            responseFromServer = task.getValue();

            // להוריד מהטבלה

            progressIndicator.stop();
        });
        new Thread(task).start();
    }

    public void RejectTmeExtension(ActionEvent actionEvent) {
        CustomProgressIndicator progressIndicator = new CustomProgressIndicator(anchorPane);
        progressIndicator.start();

        responseFromServer = null;
        Task<Response> task = new Task<Response>() {
            @Override
            protected Response call() throws Exception {

                //CommandInterface command = new ReadyTestFacadeReadByTeacherCommand(teacher.getId());
                //client.getHstsClientInterface().sendCommandToServer(command);

                // Waiting for server confirmation
                while (responseFromServer == null) {
                    Thread.onSpinWait();
                }
                return responseFromServer;
            }
        };
        task.setOnSucceeded(e -> {
            responseFromServer = task.getValue();

            // להוריד מהטבלה

            progressIndicator.stop();
        });
        new Thread(task).start();
    }
}
