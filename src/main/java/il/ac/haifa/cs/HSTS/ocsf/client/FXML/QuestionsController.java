/**
 * Sample Skeleton for 'Questions.fxml' Controller Class
 */

package il.ac.haifa.cs.HSTS.ocsf.client.FXML;


import il.ac.haifa.cs.HSTS.HSTSClientInterface;
import il.ac.haifa.cs.HSTS.ocsf.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.ocsf.server.CommandInterface.QuestionReadAllCommand;
import il.ac.haifa.cs.HSTS.ocsf.server.CommandInterface.QuestionReadBySubjectCommand;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.*;
import il.ac.haifa.cs.HSTS.ocsf.server.Services.Response;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;



public class QuestionsController implements Initializable {

    private static User user;
    private static Response responseFromServer = null;
    private static List<Question> questionList = null;
    private ObservableList<QuestionTableView> questionsOL = null;

    @FXML
    private Label helloLabel;

    @FXML
    private Button goToMenuButton;

    @FXML
    private Button goToCoursesButton;

    @FXML
    private Button goToTestsButton;

    @FXML
    private Button aboutButton;

    @FXML
    private Button logoutButton;

    @FXML
    private VBox tableViewVbox;

    @FXML
    private TableView<QuestionTableView> tableView;

    @FXML // fx:id="id"
    private TableColumn<QuestionTableView, String> columnId; // Value injected by FXMLLoader

    @FXML // fx:id="subject"
    private TableColumn<QuestionTableView, String> columnSubject; // Value injected by FXMLLoader

    @FXML // fx:id="question"
    private TableColumn<QuestionTableView, String> columnQuestion; // Value injected by FXMLLoader

    @FXML // fx:id="author"
    private TableColumn<QuestionTableView, String> columnAuthor; // Value injected by FXMLLoader

    @FXML
    private TextField searchTextField;

    @FXML
    private Button addQuestionButton;

    @FXML
    void goToMenu(ActionEvent event) throws IOException {
        Scene scene = new Scene(loadFXML("Menu"));
        Stage stage = (Stage) goToMenuButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Menu");
    }

    @FXML
    void goToCourses(ActionEvent event) {

    }

    @FXML
    void goToTests(ActionEvent event) {

    }

    @FXML
    void about(ActionEvent event) {
        Alert editInformation = new Alert(Alert.AlertType.INFORMATION);
        editInformation.setTitle("Information");
        editInformation.setHeaderText("Editing Details");
        editInformation.setContentText("In order to edit a question:\n1. Click on Questions button\n" +
                "2. Double click on a spesific row");
        editInformation.setResizable(true);
        editInformation.getDialogPane().setPrefSize(300, 200);
        Optional<ButtonType> result = editInformation.showAndWait();
    }

    @FXML
    void logout(ActionEvent event) throws IOException{
        Scene scene = new Scene(loadFXML("Login"));
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login");
    }


    @FXML
    void showQuestions(ActionEvent event) {
        searchTextField.setVisible(true);
        tableViewVbox.setVisible(true);
        tableView.setVisible(true);
        searchTextField.setVisible(true);
        addQuestionButton.setVisible(true);

        refreshList();
        //tableV.setItems(questionsOL);

        FilteredList<QuestionTableView> filteredQuests = new FilteredList<>(questionsOL, b -> true);

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredQuests.setPredicate(questsOfTeacher ->
            {
                if (newValue == null || newValue.isEmpty())
                    return true;

                String questionLowerCase = newValue.toLowerCase();
                if (questsOfTeacher.getQuestion().toLowerCase().indexOf(questionLowerCase) != -1)
                    return true;
                else
                    return false;
            });
        });

        tableView.setItems(filteredQuests);

        tableView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                QuestionTableView questionSelected = tableView.getSelectionModel().getSelectedItem();
                if (questionSelected != null && event.getClickCount() == 2) {
                    Scene scene = null;
                    try {
                        for (Question q : questionList)
                        {
                            if (q.getId() == Integer.parseInt(questionSelected.getId())) {
                                EditQuestionController.setQuestion(q);
                                break;
                            }
                        }
                        scene = new Scene(QuestionsController.loadFXML("EditQuestion"));
                    } catch (IOException e) {
                        System.out.println("not found");
                        e.printStackTrace();
                    }
                    Stage stage = (Stage) tableView.getScene().getWindow();
                    stage.setScene(scene);
                    stage.setTitle("Edit Question");
                }
            }
        });
    }


    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainClass.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void receivedRespondFromServer(Response response){
        responseFromServer = response;
        System.out.println("Command received in controller " + response);
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        QuestionsController.user = user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeUser();
    }

    public void initializeUser(){
        //helloLabel.setText("Hello " + user.getFirst_name()); - need to return when showQuestions will fix
    }

    public void refreshList() {
        questionList = new ArrayList<Question>();
        if (user instanceof Teacher) {
            List<Subject> subjects = ((Teacher) user).getSubjects();

            responseFromServer = null;
            CommandInterface command = new QuestionReadBySubjectCommand(subjects);
            HSTSClientInterface.sendCommandToServer(command);

            while (responseFromServer == null)
                System.out.print("");

            subjects = (List<Subject>) responseFromServer.getReturnedObject();

            for (Subject subject : subjects)
                questionList.addAll(subject.getQuestions());
        } else if (user instanceof Principle){
            responseFromServer = null;
            CommandInterface command = new QuestionReadAllCommand();
            HSTSClientInterface.sendCommandToServer(command);

            while (responseFromServer == null)
                System.out.print("");

            questionList = (List<Question>) responseFromServer.getReturnedObject();
        }
        columnId.setCellValueFactory(new PropertyValueFactory<QuestionTableView, String>("id"));
        columnQuestion.setCellValueFactory(new PropertyValueFactory<QuestionTableView, String>("question"));
        columnAuthor.setCellValueFactory(new PropertyValueFactory<QuestionTableView, String>("author"));
        columnSubject.setCellValueFactory(new PropertyValueFactory<QuestionTableView, String>("subject"));

        questionsOL= FXCollections.observableArrayList();
        for (Question quest : questionList){
            questionsOL.add(new QuestionTableView(String.valueOf(quest.getId()), quest.getQuestion(),
                    quest.getWriter().getUsername(),
                    quest.getSubject().getSubjectName()));
        }
    }
}
