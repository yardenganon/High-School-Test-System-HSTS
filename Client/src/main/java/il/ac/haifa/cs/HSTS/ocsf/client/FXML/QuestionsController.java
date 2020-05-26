/**
 * Sample Skeleton for 'Questions.fxml' Controller Class
 */

package il.ac.haifa.cs.HSTS.ocsf.client.FXML;


import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClient;
import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClientInterface;
import il.ac.haifa.cs.HSTS.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.server.CommandInterface.QuestionReadAllCommand;
import il.ac.haifa.cs.HSTS.server.CommandInterface.QuestionReadBySubjectCommand;
import il.ac.haifa.cs.HSTS.server.CommandInterface.Response;
import il.ac.haifa.cs.HSTS.server.Entities.*;
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

    public User user;
    private Response responseFromServer = null;
    private static List<Question> questionList = null;
    private ObservableList<QuestionTableView> questionsOL = null;
    Question selected = null;

    private HSTSClient client;

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
        FXMLLoader fxmlLoader = new FXMLLoader(MainClass.class.getResource("Menu.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) goToMenuButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Menu");

        MenuController menuController = fxmlLoader.<MenuController>getController();
        menuController.setUser(user);
        menuController.passHSTSClientReference(client);
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
        FXMLLoader fxmlLoader = new FXMLLoader(MainClass.class.getResource("Login.fxml"));
        Parent root = fxmlLoader.load();
        LoginController loginController = fxmlLoader.<LoginController>getController();
        loginController.setUser(null);
        loginController.passHSTSClientReference(client);
        Scene scene = new Scene(root);
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login");
    }


    @FXML
    void initializeQuestionsTable() {
        refreshList();

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
                        for (Question q : questionList)
                        {
                            if (q.getId() == Integer.parseInt(questionSelected.getId())) {
                                selected = q;
                                openQuestionEditWindow();
                                break;
                            }
                        }
                }
            }
        });

    }

    public void openQuestionEditWindow() {
        System.out.println(selected + " Is selected");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditQuestion.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage stage = (Stage) tableView.getScene().getWindow();
        stage.setScene(scene);
        EditQuestionController eqc = fxmlLoader.<EditQuestionController>getController();
        eqc.setUser(user);
        eqc.passHSTSClientReference(client);
        eqc.setQuestion(selected);
        stage.setTitle("Edit Question");
    }

    private Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainClass.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public void receivedRespondFromServer(Response response){
        responseFromServer = response;
        System.out.println("Command received in controller " + response);
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
        initialize(null,null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (user !=null) {
            initializeUser();
            initializeQuestionsTable();
        }
    }

    public void initializeUser(){
        helloLabel.setText("Hello " + user.getFirst_name());
    }

    public void refreshList() {
        questionList = new ArrayList<Question>();
        if (user instanceof Teacher) {
            List<Subject> subjects = ((Teacher) user).getSubjects();

            responseFromServer = null;
            CommandInterface command = new QuestionReadBySubjectCommand(subjects);
            client.getHstsClientInterface().sendCommandToServer(command);

            while (responseFromServer == null)
                System.out.print("");

            questionList = (List<Question>) responseFromServer.getReturnedObject();

//            for (Subject subject : subjects)
//                questionList.addAll(subject.getQuestions());

        } else if (user instanceof Principle){
            responseFromServer = null;
            CommandInterface command = new QuestionReadAllCommand();
            client.getHstsClientInterface().sendCommandToServer(command);

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
    public void passHSTSClientReference(HSTSClient ref) {
        this.client = ref;
        ref.getHstsClientInterface().addGUIController(this);
    }
}
