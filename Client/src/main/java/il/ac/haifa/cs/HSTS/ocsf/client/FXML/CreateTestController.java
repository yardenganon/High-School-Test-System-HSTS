package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClient;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Bundle;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.CustomProgressIndicator;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Events;
import il.ac.haifa.cs.HSTS.server.CommandInterface.*;
import il.ac.haifa.cs.HSTS.server.Entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.converter.IntegerStringConverter;
import org.greenrobot.eventbus.MainThreadSupport;

import javax.crypto.spec.PSource;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.*;

public class CreateTestController implements Initializable {

    public User user;
    public Test test;
    public Test testForEdit = null;
    private List<Question> questionList = null;
    private Response responseFromServer = null;
    private ObservableList<String> questionsOL = null;
    private static boolean thereIsAnError = false;
    private Integer sumOfPoints = 0;
    private Subject subjectSelected = null;
    private Question questionChosenToAdd = null;
    private Question questionChosenToRemove = null;
    Bundle bundle;
    private HSTSClient client;
    private Teacher teacher;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField authorTextField;

    @FXML
    private ComboBox<String> subjectComboBox;

    @FXML
    private TextField commentTextField;

    @FXML
    private TextField epilogueTextField;

    @FXML
    private TextField introductionTextField;

    @FXML
    private TextField timeTextField;

    @FXML
    private ListView<String> questionsListView;

    @FXML
    private ImageView addQuestionImageView;

    @FXML
    private ImageView removeQuestionImageView;

    @FXML
    private TableView<QuestionOfTestTableView> questionsTableView;

    @FXML
    private TableColumn<QuestionOfTestTableView, String> idColumn;

    @FXML
    private TableColumn<QuestionOfTestTableView, String> questionColumn;

    @FXML
    private TableColumn<QuestionOfTestTableView, String> answer1Column;

    @FXML
    private TableColumn<QuestionOfTestTableView, String> answer2Column;

    @FXML
    private TableColumn<QuestionOfTestTableView, String> answer3Column;

    @FXML
    private TableColumn<QuestionOfTestTableView, String> answer4Column;

    @FXML
    private TableColumn<QuestionOfTestTableView, String> correctAnswerColumn;

    @FXML
    private TableColumn<QuestionOfTestTableView, String> pointsColumn;


    @FXML
    private Text errorLabel;

    @FXML
    private Label helloLB;

    @FXML
    private Label helloLabel;

    @FXML
    private Button goToMenuButton;

    @FXML
    private Button goToCoursesButton;

    @FXML
    private Button goToQuestionsButton;

    @FXML
    private Button goToTestsButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button confirmTestButton;

    @FXML
    private Label SumOfPointsLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bundle = Bundle.getInstance();
        client = (HSTSClient)bundle.get("client");
        client.getHstsClientInterface().getGuiControllers().clear();
        client.getHstsClientInterface().addGUIController(this);
        user = (User) bundle.get("user");
        testForEdit = (Test) bundle.get("test");
        teacher = (Teacher) user;

        Subject subjectOfTest;
        initializeTestDetails();
        if (testForEdit != null) {
            subjectOfTest = testForEdit.getSubject();
            subjectComboBox.getSelectionModel().select(subjectOfTest.getSubjectName());
        }
        else
            subjectOfTest = teacher.getSubjects().get(0);
        System.out.println(subjectOfTest);
        System.out.println("print " + subjectOfTest);
        test = new Test(teacher, subjectOfTest);

        if (bundle.get("update") != null && (boolean) bundle.get("update")) {
            setIfEdit();
            bundle.remove("update");
        }
        questionsListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("listener add question code ");
                selectQuestionToAdd();
            }
        });
        questionsTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectQuestionToRemove();
                System.out.println("listener remove question code");
            }
        });
    }

    @FXML
    void confirmTest(ActionEvent event) throws InterruptedException {
        Alert createTestAlert = new Alert(Alert.AlertType.CONFIRMATION);
        createTestAlert.setHeaderText("Are you sure you want to create that test?");
        Optional<ButtonType> result = createTestAlert.showAndWait();

        /* If the teacher clicks OK, question details will be sent to the server after input checking */
        if (result.isPresent() && result.get() == ButtonType.OK)
        {
            thereIsAnError = false;
            errorLabel.setText("");
            // Input checking of TextField
            if (timeTextField.getText().isEmpty())
                inputErrorTextField(timeTextField);
            try {
                Integer.parseInt(timeTextField.getText());
            }
            catch (NumberFormatException e) {
                //Not an integer
                inputErrorTextField(timeTextField);
            }
            if (Integer.parseInt(SumOfPointsLabel.getText()) < 100)
            {
                errorLabel.setFill(Color.RED);
                errorLabel.setText("No subject/questions were selected and/or sum of test points is not 100");
                thereIsAnError = true;
            }


            // If there are no input errors, request for creating test will be sent to the server
            if (!thereIsAnError) {
                // setting the test data
                test.setCommentForTeachers(commentTextField.getText());
                test.setEpilogue(epilogueTextField.getText());
                test.setIntroduction(introductionTextField.getText());
                test.setTime(Integer.parseInt(timeTextField.getText()));
                System.out.println("test after update: "+ test);
                CustomProgressIndicator progressIndicator = new CustomProgressIndicator(anchorPane);
                progressIndicator.start();

                responseFromServer = null;
                Task<Response> task = new Task<Response>() {
                    @Override
                    protected Response call() throws Exception {
                        System.out.println(test);
                        CommandInterface command = new TestPushCommand(test);
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
                    progressIndicator.stop();
                    test = (Test) responseFromServer.getReturnedObject();
                    //initializeTestDetails();
                    Events.navigateTestsEvent(confirmTestButton);
                    anchorPane.setDisable(false);
                    Alert updateSuccessAlert = new Alert(Alert.AlertType.INFORMATION);
                    updateSuccessAlert.setHeaderText("The test was successfully created");
                    updateSuccessAlert.showAndWait();
                });
                new Thread(task).start();
            }
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
    void goToTests(ActionEvent event) throws  IOException{
        Events.navigateTestsEvent(goToTestsButton);
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        Events.navigateLogoutEvent(logoutButton);
    }

    public void receivedResponseFromServer(Response response){
        responseFromServer = response;
        System.out.println("Command received in controller " + response);
    }

    public void initializeTestDetails()
    {
            System.out.println("user: " + user);
            System.out.println("teacher: " + teacher);
            subjectComboBox.getItems().clear();
            for (Subject subject : teacher.getSubjects())
                subjectComboBox.getItems().add(subject.getSubjectName());
            authorTextField.setText(teacher.getFirst_name() + " " + teacher.getLast_name());
            if (bundle.get("update") == null){
                subjectSelect(new ActionEvent());
                subjectComboBox.setDisable(false);
            }
            else
                subjectComboBox.getSelectionModel().selectFirst();
            timeTextField.setText("");
            epilogueTextField.setText("");
            introductionTextField.setText("");
            commentTextField.setText("");
            questionsTableView.getItems().clear();
        helloLabel.setText("Hello " + user.getFirst_name());
    }

    public void refreshList() {
        CustomProgressIndicator progressIndicator = new CustomProgressIndicator(anchorPane);
        progressIndicator.start();
        questionsListView.getItems().clear();
        questionList = new ArrayList<Question>();
        Task<Response> task = new Task<Response>() {
            @Override
            protected Response call() throws Exception {
                if (user instanceof Teacher) {
                    List<Subject> subjects = new ArrayList<Subject>();
                    subjects.add(subjectSelected);
                    responseFromServer = null;
                    CommandInterface command = new QuestionReadBySubjectCommand(subjects);
                    client.getHstsClientInterface().sendCommandToServer(command);

                    while (responseFromServer == null)
                        Thread.sleep(10);
                } else if (user instanceof Principle) {/*
                    responseFromServer = null;

                    CommandInterface command = new QuestionReadAllCommand();
                    client.getHstsClientInterface().sendCommandToServer(command);

                    while (responseFromServer == null)
                        Thread.sleep(10);

                    questionList = (List<Question>) responseFromServer.getReturnedObject();*/
                }
                return responseFromServer;
            }
        };
        task.setOnSucceeded(e-> {
            responseFromServer = task.getValue();
            progressIndicator.stop();
            questionList = (List<Question>) responseFromServer.getReturnedObject();
            /*columnId.setCellValueFactory(new PropertyValueFactory<QuestionTableView, String>("id"));
            columnQuestion.setCellValueFactory(new PropertyValueFactory<QuestionTableView, String>("question"));
            columnAuthor.setCellValueFactory(new PropertyValueFactory<QuestionTableView, String>("author"));
            columnSubject.setCellValueFactory(new PropertyValueFactory<QuestionTableView, String>("subject"));*/

            questionsOL = FXCollections.observableArrayList();
            questionsOL.removeAll();
            for (Question quest : questionList) {
                questionsOL.add(quest.getQuestion());
            }
            FXCollections.sort(questionsOL);
            questionsListView.getItems().addAll(questionsOL);
        });
        new Thread(task).start();
    }

    public void inputErrorTextField(TextField textField)
    {
        textField.setText("Invalid input");
        textField.setStyle("-fx-text-inner-color: #ff0000;");
        thereIsAnError = true;
    }

    // That event sets textfield font color to black and remove the text only if he has an input error

    @FXML
    void timeOnMouseClicked(MouseEvent event) {
        if (timeTextField.getText().equals("Invalid input")) {
            timeTextField.setStyle("-fx-text-inner-color: #000000;");
            timeTextField.setText("");
        }
    }

    @FXML
    void subjectSelect(ActionEvent event) {
        String subjectSelectedType = subjectComboBox.getSelectionModel().getSelectedItem();
        for (Subject subj : teacher.getSubjects())
            if (subj.getSubjectName().equals(subjectSelectedType)) {
                subjectSelected = subj;
                break;
            }
        refreshList();
        questionsTableView.getItems().clear();
    }

    void selectQuestionToAdd(){
        String questionSelected = questionsListView.getSelectionModel().getSelectedItem();
        if (questionSelected != null) {
            for (Question quest : questionList) {
                if (quest.getQuestion().equals(questionSelected)) {
                    questionChosenToAdd = quest;
                    System.out.println(quest);
                    break;
                }
            }
        }
        else
            questionChosenToAdd = null;
    }

    void selectQuestionToRemove(){
        QuestionOfTestTableView questionSelected = questionsTableView.getSelectionModel().getSelectedItem();
        if (questionSelected != null) {
            for (Question quest : questionList) {
                if (quest.getId() == questionSelected.getId()) {
                    questionChosenToRemove = quest;
                    System.out.println("question to remove: " + quest);
                    break;
                }
            }
        }
        else
            questionChosenToRemove = null;
    }

    @FXML
    void addQuestion(MouseEvent event) {
        if (questionChosenToAdd != null) {
            System.out.println("test before add: " +test.getQuestionSet());
            for (Question q : test.getQuestionSet())
                if (questionChosenToAdd.getId() == q.getId()) {
                    questionChosenToAdd = q;
                    System.out.println("GOTCHA");
                }
            test.getQuestionSet().add(questionChosenToAdd);
            test.setPointsToQuestion(questionChosenToAdd, 1);

            questionsListView.getItems().remove(questionsListView.getSelectionModel().getSelectedItem());
            idColumn.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("id"));
            questionColumn.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("question"));
            answer1Column.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("answer1"));
            answer2Column.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("answer2"));
            answer3Column.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("answer3"));
            answer4Column.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("answer4"));
            correctAnswerColumn.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("correctAnswer"));
            pointsColumn.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("points"));
            pointsColumn.setCellFactory(TextFieldTableCell.forTableColumn());

            QuestionOfTestTableView questionAdded = new QuestionOfTestTableView(questionChosenToAdd.getId(),
                    questionChosenToAdd.getQuestion(), questionChosenToAdd.getAnswer(1),
                    questionChosenToAdd.getAnswer(2), questionChosenToAdd.getAnswer(3),
                    questionChosenToAdd.getAnswer(4), questionChosenToAdd.getCorrectAnswer(),
                    test.getPoints().get(questionChosenToAdd).toString());

            questionsTableView.getItems().add(questionAdded);

            System.out.println("all questions in set: " + test.getQuestionSet());
            System.out.println("all questions in points map: " + test.getPoints());
            //mapQuestions.put(questionAdded, questionChosenToAdd);

            //questionChosenToAdd = null;
            selectQuestionToAdd();
        }
    }

    @FXML
    void removeQuestion(MouseEvent event) {
        if (questionChosenToRemove != null) {
            System.out.println("test before remove: " +test.getQuestionSet());
            System.out.println("question to remove now: " + questionChosenToRemove);
            for (Question q : test.getQuestionSet())
                if (questionChosenToRemove.getId() == q.getId()) {
                    questionChosenToRemove = q;
                    System.out.println("GOTCHA");
                }
            System.out.println("set before removal: " + test.getQuestionSet());
            test.getQuestionSet().remove(questionChosenToRemove);
            test.getPoints().remove(questionChosenToRemove);
            System.out.println("set after removal: " + test.getQuestionSet());
            System.out.println("set after remove now: " + test.getQuestionSet());
            QuestionOfTestTableView currentQuestion = questionsTableView.getSelectionModel().getSelectedItem();
            questionsTableView.getItems().remove(currentQuestion);
            questionsListView.getItems().add(questionChosenToRemove.getQuestion());
            ObservableList<String> questionsRemovalObs = FXCollections.observableArrayList();
            for (String quest : questionsListView.getItems()) {
                questionsRemovalObs.add(quest);
            }
            FXCollections.sort(questionsRemovalObs);
            questionsListView.getItems().clear();
            questionsListView.getItems().addAll(questionsRemovalObs);
            sumOfPoints -= Integer.parseInt(currentQuestion.getPoints());
            SumOfPointsLabel.setText(String.valueOf(sumOfPoints));

            System.out.println("all questions in set: " + test.getQuestionSet());
            System.out.println("all questions in points map: " + test.getPoints());
            questionsRemovalObs.removeAll();

            //questionChosenToRemove = null;
            selectQuestionToRemove();
        }
    }

    @FXML
    public void editPoints(TableColumn.CellEditEvent<QuestionOfTestTableView, String> questionsTableViewIntegerCellEditEvent) {
        QuestionOfTestTableView changedColumn = questionsTableView.getSelectionModel().getSelectedItem();

        if (Integer.parseInt(questionsTableViewIntegerCellEditEvent.getNewValue()) > 0) {
            changedColumn.setPoints(questionsTableViewIntegerCellEditEvent.getNewValue());
            for (Question quest : questionList) {
                if (quest.getId() == changedColumn.getId()) {
                    test.setPointsToQuestion(quest, Integer.parseInt(changedColumn.getPoints()));
                    break;
                }
            }

            sumOfPoints = 0;
            for (QuestionOfTestTableView quest : questionsTableView.getItems()) {
                sumOfPoints += Integer.parseInt(quest.getPoints());
            }
            SumOfPointsLabel.setText(String.valueOf(sumOfPoints));
        }
        else
            changedColumn.setPoints(questionsTableViewIntegerCellEditEvent.getOldValue());
        questionsTableView.refresh();

    }

    public void setIfEdit() {
        authorTextField.setText(teacher.getFirst_name() + " " + teacher.getLast_name());
        if (testForEdit != null)
        {
            subjectSelected = testForEdit.getSubject();
            System.out.println("here I want to see: " + testForEdit.getSubject());
            subjectComboBox.setDisable(true);
            commentTextField.setText(testForEdit.getCommentForTeachers());
            epilogueTextField.setText(testForEdit.getEpilogue());
            introductionTextField.setText(testForEdit.getIntroduction());
            timeTextField.setText(testForEdit.getTime().toString());

            CustomProgressIndicator progressIndicator = new CustomProgressIndicator(anchorPane);
            progressIndicator.start();
            questionsListView.getItems().clear();
            questionsTableView.getItems().clear();
            Task<Response> task = new Task<Response>() {
                @Override
                protected Response call() throws Exception {
                    if (user instanceof Teacher) {
                        List<Subject> subjects = new ArrayList<Subject>();
                        System.out.println("subject of test: " + testForEdit.getSubject());
                        subjects.add(testForEdit.getSubject());
                        responseFromServer = null;
                        CommandInterface command = new QuestionReadBySubjectCommand(subjects);
                        client.getHstsClientInterface().sendCommandToServer(command);

                        while (responseFromServer == null)
                            Thread.sleep(10);
                    } else if (user instanceof Principle) {/*
                    responseFromServer = null;

                    CommandInterface command = new QuestionReadAllCommand();
                    client.getHstsClientInterface().sendCommandToServer(command);

                    while (responseFromServer == null)
                        Thread.sleep(10);

                    questionList = (List<Question>) responseFromServer.getReturnedObject();*/
                    }
                    return responseFromServer;
                }
            };
            task.setOnSucceeded(e-> {
                responseFromServer = task.getValue();
                progressIndicator.stop();
                questionList = (List<Question>) responseFromServer.getReturnedObject();
                System.out.println("response from server: " + questionList);
                questionsOL = FXCollections.observableArrayList();
                questionsOL.removeAll();
                for (Question quest : questionList) {
                    boolean flag = false;
                    for (Question q: testForEdit.getQuestionSet())
                        if(quest.getId() == q.getId()) {
                            flag = true;
                            test.getQuestionSet().add(quest);
                            test.setPointsToQuestion(quest, testForEdit.getPoints().get(q));
                        }
                    if (flag == false)
                        questionsOL.add(quest.getQuestion());
                }
                System.out.println("set first " + test.getQuestionSet());
                FXCollections.sort(questionsOL);
                questionsListView.getItems().addAll(questionsOL);

                System.out.println("set of question " + testForEdit.getQuestionSet());
                for (Question q : testForEdit.getQuestionSet()) {
                    System.out.println("question inside: " + q);
                    idColumn.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("id"));
                    questionColumn.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("question"));
                    answer1Column.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("answer1"));
                    answer2Column.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("answer2"));
                    answer3Column.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("answer3"));
                    answer4Column.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("answer4"));
                    correctAnswerColumn.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("correctAnswer"));
                    pointsColumn.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("points"));
                    pointsColumn.setCellFactory(TextFieldTableCell.forTableColumn());

                    QuestionOfTestTableView questionAdded = new QuestionOfTestTableView(q.getId(),
                            q.getQuestion(), q.getAnswer(1),
                            q.getAnswer(2), q.getAnswer(3),
                            q.getAnswer(4), q.getCorrectAnswer(),
                            testForEdit.getPoints().get(q).toString());

                    System.out.println(test.getQuestionSet());
                    questionsTableView.getItems().add(questionAdded);
                    sumOfPoints = 0;
                    for (QuestionOfTestTableView quest : questionsTableView.getItems()){
                        sumOfPoints += Integer.parseInt(quest.getPoints());
                    }
                    SumOfPointsLabel.setText(String.valueOf(sumOfPoints));
                }
            });
            new Thread(task).start();
        }
    }
}