/**
 * Sample Skeleton for 'Menu.fxml' Controller Class
 */

package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClient;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Bundle;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.CustomProgressIndicator;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Events;
import il.ac.haifa.cs.HSTS.server.CommandInterface.*;
import il.ac.haifa.cs.HSTS.server.Entities.*;
import il.ac.haifa.cs.HSTS.server.Facade.ReadyTestExtendedFacade;
import il.ac.haifa.cs.HSTS.server.Facade.ReadyTestFacade;
import il.ac.haifa.cs.HSTS.server.Status.Status;
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
    private TableColumn<TimeExtensionRequestTableView, String> columnActive;

    @FXML
    private TableView<TimeExtensionRequestTableView> timeExtensionRequestForPrincipleTV;

    @FXML
    private TableColumn<TimeExtensionRequestTableView, Integer> courseNamePrincipleTV;

    @FXML
    private TableColumn<TimeExtensionRequestTableView, String> teacherNamePrincipleTV;

    @FXML
    private TableColumn<TimeExtensionRequestTableView, String> timeExtensionPrincipleTV;

    @FXML
    private TableColumn<TimeExtensionRequestTableView, String> descriptionPrincipleTV;

    @FXML
    private Button acceptTimeExtensionButton;

    @FXML
    private Button rejectTimeExtensionButton;

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
            InitPrincipleMenu(principle);
            principlePane.setVisible(true);
        }
    }

    /* ---------------------- Teacher ---------------- */

    private void InitTeacherMenu(Teacher teacher) {

        // update the active tests table
        responseFromServer = null;
        Task<Response> task = new Task<Response>() {
            @Override
            protected Response call() throws Exception {
                CommandInterface command = new ReadyTestExtendedFacadeReadByTeacherCommand(teacher.getId());
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
            columnActive.setCellValueFactory(new PropertyValueFactory<TimeExtensionRequestTableView, String>("active"));

            columnTimeExtensionReason.setCellFactory(TextFieldTableCell.forTableColumn());
            columnTimeExtension.setCellFactory(TextFieldTableCell.forTableColumn());

            List<Object> listOfFacadTests = (List<Object>) responseFromServer.getReturnedObject();

            List<ReadyTestExtendedFacade> readyTestFacadeExtendedList = (List<ReadyTestExtendedFacade>)listOfFacadTests.get(0);
            List<ReadyTestFacade> reasyTestFacadeList = (List<ReadyTestFacade>) listOfFacadTests.get(0);;

            questionsOL = FXCollections.observableArrayList();

            for (ReadyTestExtendedFacade readyTestExtendedFacade : readyTestFacadeExtendedList) {
                questionsOL.add(new TimeExtensionRequestTableView(readyTestExtendedFacade.getId(), readyTestExtendedFacade.getCourseName(), teacher,
                        String.valueOf(readyTestExtendedFacade.getTimeToAdd()) , readyTestExtendedFacade.getTimeExtensionReason(), readyTestExtendedFacade.getTimeExtensionRequestStatus(),  readyTestExtendedFacade.getActive()));
            }
            activeTestsTebleView.getItems().addAll(questionsOL);

            boolean foundedInFacadTestsList = false;
            questionsOL = FXCollections.observableArrayList();
            foundedInFacadTestsList = false;
            for (ReadyTestFacade readyTestFacade : reasyTestFacadeList) {
                for (ReadyTestExtendedFacade readyTestExtendedFacade : readyTestFacadeExtendedList) {
                    if (readyTestExtendedFacade.getId() == readyTestFacade.getId()) {
                        foundedInFacadTestsList = true;
                        break;
                    }
                }
            if (!foundedInFacadTestsList)
                questionsOL.add(new TimeExtensionRequestTableView(readyTestFacade.getId(), readyTestFacade.getCourseName(), teacher,
                        "", "" , null,  readyTestFacade.getActive()));
            }
            activeTestsTebleView.getItems().addAll(questionsOL);
        });
        new Thread(task).start();
    }


    public void changeActivityRequest(ActionEvent actionEvent) {
        TimeExtensionRequestTableView chosenCell = activeTestsTebleView.getSelectionModel().getSelectedItem();

        responseFromServer = null;
        currentReadyTest = null;

        Task<Response> task = new Task<Response>() {
            @Override
            protected Response call() throws Exception {

                CommandInterface command = new ReadyTestReadByIdCommand(chosenCell.getTestId());
                client.getHstsClientInterface().sendCommandToServer(command);
                System.out.println(chosenCell.getTestId());

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
            updateActivityOfReadyTest(chosenCell);

        });
        new Thread(task).start();
    }

    private void updateActivityOfReadyTest(TimeExtensionRequestTableView chosenCell) {
        responseFromServer = null;
        Task<Response> task = new Task<Response>() {
            @Override
            protected Response call() throws Exception {

                System.out.println(chosenCell.getActive());
                boolean updatedActivity = false;
                if (chosenCell.getActive().equals("NO")) {
                    updatedActivity = true;
                    chosenCell.setActive(Boolean.TRUE);
                }
                else {
                    updatedActivity = false;
                    chosenCell.setActive(Boolean.FALSE);
                }

                activeTestsTebleView.refresh();

                CommandInterface command = new ReadyTestUpdateActivityCommand(currentReadyTest.getId(), updatedActivity);
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

                    CommandInterface command = new RequestTimeExtensionCommand(new TimeExtensionRequest(teacher, currentReadyTest,
                            timeExtensionRequest.getTimeExtensionReason(), Integer.parseInt(timeExtensionRequest.getTimeExtension())));
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

                timeExtensionRequest.setStatus(Status.TestActive);
                activeTestsTebleView.refresh();

                progressIndicator.stop();
            });
            new Thread(task).start();
        }

    }

    public void getReadyTest(int id)
    {
        responseFromServer = null;
        currentReadyTest = null;

        Task<Response> task = new Task<Response>() {
            @Override
            protected Response call() throws Exception {

                CommandInterface command = new ReadyTestReadByIdCommand(id);
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

    /* ---------------------- end Teacher ------------- */



    /* ---------------------- Principle --------------- */

    private void InitPrincipleMenu(Principle principle) {

            // update time extension request table
            responseFromServer = null;
            Task<Response> task = new Task<Response>() {
                @Override
                protected Response call() throws Exception {
                    CommandInterface command = new TimeExtensionReadAllCommand();
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

                courseNamePrincipleTV.setCellValueFactory(new PropertyValueFactory<TimeExtensionRequestTableView, Integer>("courseName"));
                timeExtensionPrincipleTV.setCellValueFactory(new PropertyValueFactory<TimeExtensionRequestTableView, String>("timeExtension"));
                descriptionPrincipleTV.setCellValueFactory(new PropertyValueFactory<TimeExtensionRequestTableView, String>("timeExtensionReason"));
                teacherNamePrincipleTV.setCellValueFactory(new PropertyValueFactory<TimeExtensionRequestTableView, String>("teacherUserName"));

                List<TimeExtensionRequest> timeExtensionRequestList = (List<TimeExtensionRequest>) responseFromServer.getReturnedObject();
                questionsOL = FXCollections.observableArrayList();

                for (TimeExtensionRequest timeExtensionRequest : timeExtensionRequestList) {
                    questionsOL.add(new TimeExtensionRequestTableView(timeExtensionRequest.getTest().getCourse().getCourseName(),
                            timeExtensionRequest.getInitiator(), String.valueOf(timeExtensionRequest.getTimeToAdd()),
                            timeExtensionRequest.getDescription()));
                }
                timeExtensionRequestForPrincipleTV.getItems().addAll(questionsOL);
            });
            new Thread(task).start();
    }

    public void AcceptTimeExtension(ActionEvent actionEvent) {

        TimeExtensionRequestTableView chosenRow = timeExtensionRequestForPrincipleTV.getSelectionModel().getSelectedItem();
        if (chosenRow == null)
        {
            Alert missingDetailsAlert = new Alert(Alert.AlertType.ERROR);
            missingDetailsAlert.setHeaderText("For accept time extension request you need to choose test and then press \"Accept\" button");
            missingDetailsAlert.showAndWait();
        }
        else {
            CustomProgressIndicator progressIndicator = new CustomProgressIndicator(anchorPane);
            progressIndicator.start();

            responseFromServer = null;
            Task<Response> task = new Task<Response>() {
                @Override
                protected Response call() throws Exception {
                    TimeExtensionRequest timeExtensionRequest = new TimeExtensionRequest(teacher, currentReadyTest,
                            chosenRow.getTimeExtensionReason(), Integer.parseInt(chosenRow.getTimeExtension()));
                    timeExtensionRequest.setStatus(Status.TimeExtensionRequestApproved);

                    CommandInterface command = new TimeExtensionRequestUpdateCommand(timeExtensionRequest);
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

                timeExtensionRequestForPrincipleTV.getItems().remove(chosenRow);

                progressIndicator.stop();
            });
            new Thread(task).start();
        }
    }

    public void RejectTimeExtension(ActionEvent actionEvent) {
        TimeExtensionRequestTableView chosenRow = timeExtensionRequestForPrincipleTV.getSelectionModel().getSelectedItem();
        if (chosenRow == null) {
            Alert missingDetailsAlert = new Alert(Alert.AlertType.ERROR);
            missingDetailsAlert.setHeaderText("For accept time extension request you need to choose test and then press \"Accept\" button");
            missingDetailsAlert.showAndWait();
        } else {
            CustomProgressIndicator progressIndicator = new CustomProgressIndicator(anchorPane);
            progressIndicator.start();

            responseFromServer = null;
            Task<Response> task = new Task<Response>() {
                @Override
                protected Response call() throws Exception {
                    TimeExtensionRequest timeExtensionRequest = new TimeExtensionRequest(teacher, currentReadyTest,
                            chosenRow.getTimeExtensionReason(), Integer.parseInt(chosenRow.getTimeExtension()));
                    timeExtensionRequest.setStatus(Status.TimeExtensionRequestDenied);

                    CommandInterface command = new TimeExtensionRequestUpdateCommand(timeExtensionRequest);
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

                timeExtensionRequestForPrincipleTV.getItems().remove(chosenRow);

                progressIndicator.stop();
            });
            new Thread(task).start();
        }
    }



    /* ---------------------- end Principle ------------- */



    /* ---------------------- Student ------------------- */


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

    /* ---------------------- end Student ------------- */

}
