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
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class CreateTestController implements Initializable {

    public User user;
    public Test test;
    private List<Question> questionList = null;
    private Response responseFromServer = null;
    private ObservableList<String> questionsOL = null;
    //private Map<QuestionOfTestTableView, Question> mapQuestions = new HashMap<QuestionOfTestTableView, Question>();
    private static boolean thereIsAnError = false;
    private String subjectSelected = null;
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
    private TableColumn<QuestionOfTestTableView, Integer> pointsColumn;

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
    private Button aboutButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button confirmTestButton;

    @FXML
    private Label SumOfPointsLabel;

    @FXML
    void goToCourses(ActionEvent event) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bundle = Bundle.getInstance();
        client = (HSTSClient)bundle.get("client");
        client.getHstsClientInterface().getGuiControllers().clear();
        client.getHstsClientInterface().addGUIController(this);
        user = (User) bundle.get("user");
        teacher = (Teacher) user;
        test = new Test(teacher, teacher.getSubjects().get(0));
        initializeTestDetails();
        questionsListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectQuestionToAdd();
            }
        });
        questionsTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectQuestionToRemove();
            }
        });
    }

    @FXML
    void confirmTest(ActionEvent event) throws InterruptedException {
        Alert createTestAlert = new Alert(Alert.AlertType.CONFIRMATION);
        createTestAlert.setHeaderText("Are you sure you want to create that test?");
        Optional<ButtonType> result = createTestAlert.showAndWait();

        /* If the teacher clicks OK, question details will be sent to the server after input checking */
       /* if (result.isPresent() && result.get() == ButtonType.OK)
        {
            // Input checking of TextField
            if (questionTextField.getText().isEmpty())
                inputErrorTextField(questionTextField);
            if (answer1TextField.getText().isEmpty())
                inputErrorTextField(answer1TextField);
            if (answer2TextField.getText().isEmpty())
                inputErrorTextField(answer2TextField);
            if (answer3TextField.getText().isEmpty())
                inputErrorTextField(answer3TextField);
            if (answer4TextField.getText().isEmpty())
                inputErrorTextField(answer4TextField);

            // If there are no input errors, request for creating question will be sent to the server
            if (!thereIsAnError) {

                // Finding the selected subject for sending the object while creating the question
                Subject selectedSubject = null;
                if (user instanceof Teacher) {
                    Teacher teacher = ((Teacher) user);
                    List<Subject> listOfSubject = teacher.getSubjects();
                    for (Subject subject : teacher.getSubjects())
                    {
                        if (subject.getSubjectName() == subjectComboBox.getSelectionModel().getSelectedItem())
                        {
                            selectedSubject = subject;
                            break;
                        }
                    }
                }

                System.out.println(questionTextField.getText());
                System.out.println(answer1TextField.getText());
                System.out.println(answer2TextField.getText());
                System.out.println(answer3TextField.getText());
                System.out.println(answer4TextField.getText());
                System.out.println(Integer.parseInt(correctAnswerComboBox.getSelectionModel().getSelectedItem()));
                System.out.println(teacher.getFirst_name());
                System.out.println(selectedSubject.getSubjectName());

                // Creating the question
                question = new Question(questionTextField.getText(), answer1TextField.getText(), answer2TextField.getText(), answer3TextField.getText(), answer4TextField.getText(),
                        Integer.parseInt(correctAnswerComboBox.getSelectionModel().getSelectedItem()), teacher, selectedSubject);

                progressIndicator = new CustomProgressIndicator(anchorPane2);
                progressIndicator.start();

                responseFromServer = null;
                Task<Response> task = new Task<Response>() {
                    @Override
                    protected Response call() throws Exception {
                        CommandInterface command = new QuestionPushCommand(question);
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
                    question = (Question) responseFromServer.getReturnedObject();
                    initializeQuestionDetails();
                    anchorPane.setDisable(false);
                    Alert updateSuccessAlert = new Alert(Alert.AlertType.INFORMATION);
                    updateSuccessAlert.setHeaderText("The question was successfully created");
                    updateSuccessAlert.showAndWait();
                });
                new Thread(task).start();
            }
        }*/
    }

    @FXML
    void about(ActionEvent event) {
        Events.aboutWindowEvent();
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
        Events.navigateLogoutEvent(goToTestsButton);
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        Events.navigateLogoutEvent(logoutButton);
    }

    public void receivedRespondFromServer(Response response){
        responseFromServer = response;
        System.out.println("Command received in controller " + response);
    }

    public void initializeTestDetails()
    {
        if (user instanceof Teacher) {
            teacher = (Teacher) user;
            subjectComboBox.getItems().clear();
            for (Subject subject : teacher.getSubjects())
                subjectComboBox.getItems().add(subject.getSubjectName());
            authorTextField.setText(teacher.getFirst_name() + " " + teacher.getLast_name());
        }
        resetFields();
        refreshList();
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
                    subjectSelected = subjectComboBox.getSelectionModel().getSelectedItem();
                    Subject subjectSelectedType = null;
                    for (Subject sub : teacher.getSubjects())
                        if (sub.getSubjectName().equals(subjectSelected))
                            subjectSelectedType = sub;
                    subjects.add(subjectSelectedType);
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

    public void resetFields()
    {
        //ResetRedColor();
        subjectComboBox.getSelectionModel().selectFirst();
    }

    /*public void ResetRedColor()
    {
        questionTextField.setStyle("-fx-text-inner-color: #000000;");
        answer1TextField.setStyle("-fx-text-inner-color: #000000;");
        answer2TextField.setStyle("-fx-text-inner-color: #000000;");
        answer3TextField.setStyle("-fx-text-inner-color: #000000;");
        answer4TextField.setStyle("-fx-text-inner-color: #000000;");
    }

    // That event sets textfield font color to black and remove the text only if he has an input error
    public void questionOnMouseClicked(MouseEvent mouseEvent) {
        if (questionTextField.getText().equals("Invalid input"))
            ResetField(questionTextField);
    }
    public void answer1OnMouseClicked(MouseEvent mouseEvent) {
        if (answer1TextField.getText().equals("Invalid input"))
            ResetField(answer1TextField);
    }
    public void answer2OnMouseClicked(MouseEvent mouseEvent) {
        if (answer2TextField.getText().equals("Invalid input"))
            ResetField(answer2TextField);
    }
    public void answer3OnMouseClicked(MouseEvent mouseEvent) {
        if (answer3TextField.getText().equals("Invalid input"))
            ResetField(answer3TextField);
    }
    public void answer4OnMouseClicked(MouseEvent mouseEvent) {
        if (answer4TextField.getText().equals("Invalid input"))
            ResetField(answer4TextField);
    }*/

    @FXML
    void subjectSelect(ActionEvent event) {
        refreshList();
        for (Subject subj : teacher.getSubjects())
            if (subj.getSubjectName().equals(subjectSelected)) {
                test.setSubject(subj);
                break;
            }
        questionsTableView.getItems().clear();
    }

    void selectQuestionToAdd(){
        String questionSelected = questionsListView.getSelectionModel().getSelectedItem();
        if (questionSelected != null) {
            for (Question quest : questionList) {
                if (quest.getQuestion().equals(questionSelected)) {
                    questionChosenToAdd = quest;
                    test.setPointsToQuestion(questionChosenToAdd, 0);
                }
            }
        }
    }

    void selectQuestionToRemove(){
        QuestionOfTestTableView questionSelected = questionsTableView.getSelectionModel().getSelectedItem();
        if (questionSelected != null) {
            for (Question quest : questionList) {
                if (quest.getId() == questionSelected.getId()) {
                    questionChosenToRemove = quest;
                    test.getPoints().remove(questionChosenToRemove);
                }
            }
        }
    }

    @FXML
    void addQuestion(MouseEvent event) {
        if (questionChosenToAdd != null) {
            questionsListView.getItems().remove(questionsListView.getSelectionModel().getSelectedItem());
            idColumn.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("id"));
            questionColumn.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("question"));
            answer1Column.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("answer1"));
            answer2Column.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("answer2"));
            answer3Column.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("answer3"));
            answer4Column.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("answer4"));
            correctAnswerColumn.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, String>("correctAnswer"));
            pointsColumn.setCellValueFactory(new PropertyValueFactory<QuestionOfTestTableView, Integer>("points"));
            pointsColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

            QuestionOfTestTableView questionAdded = new QuestionOfTestTableView(questionChosenToAdd.getId(),
                    questionChosenToAdd.getQuestion(), questionChosenToAdd.getAnswer(1),
                    questionChosenToAdd.getAnswer(2), questionChosenToAdd.getAnswer(3),
                    questionChosenToAdd.getAnswer(4), questionChosenToAdd.getCorrectAnswer(),
                    test.getPoints().get(questionChosenToAdd));

            questionsTableView.getItems().add(questionAdded);

            //mapQuestions.put(questionAdded, questionChosenToAdd);

            selectQuestionToAdd();
        }
    }

    @FXML
    void removeQuestion(MouseEvent event) {
        if (questionChosenToRemove != null) {
            questionsTableView.getItems().remove(questionsTableView.getSelectionModel().getSelectedItem());
            //mapQuestions.remove(questionChosenToRemove);
            questionsListView.getItems().add(questionChosenToRemove.getQuestion());
            selectQuestionToRemove();
        }
    }

    @FXML
    void editPoint(ActionEvent event) {
        //QuestionOfTestTableView currentQuestion = questionsTableView.getSelectionModel().getSelectedItem();
    }

}