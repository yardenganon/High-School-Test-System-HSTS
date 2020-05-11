/**
 * Sample Skeleton for 'menuInterface.fxml' Controller Class
 */

package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import il.ac.haifa.cs.HSTS.Command;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Question;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Subject;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Teacher;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.User;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class menuInterface implements Initializable {
    /**
     * Sample Skeleton for 'menuInterface.fxml' Controller Class
     */

    private static User user;
    private static Command commandFromServer = null;
    private static List<Question> questsOfTeacher = null;
    private ObservableList<QuestionTeacher> questionsOL = null;

    @FXML // fx:id="helloLB"
    private Label helloLB; // Value injected by FXMLLoader

    @FXML // fx:id="Showbtn"
    private Button Showbtn; // Value injected by FXMLLoader

    @FXML // fx:id="logoutBtn"
    private Button logoutBtn; // Value injected by FXMLLoader

    @FXML // fx:id="tableVB"
    private VBox tableVB; // Value injected by FXMLLoader

    @FXML // fx:id="tableV"
    private TableView<QuestionTeacher> tableV; // Value injected by FXMLLoader

    @FXML // fx:id="id"
    private TableColumn<QuestionTeacher, String> columnId; // Value injected by FXMLLoader

    @FXML // fx:id="subject"
    private TableColumn<QuestionTeacher, String> columnSubject; // Value injected by FXMLLoader

    @FXML // fx:id="question"
    private TableColumn<QuestionTeacher, String> columnQuestion; // Value injected by FXMLLoader

    @FXML // fx:id="author"
    private TableColumn<QuestionTeacher, String> columnAuthor; // Value injected by FXMLLoader

    @FXML // fx:id="searchTF"
    private TextField searchTF; // Value injected by FXMLLoader

    @FXML // fx:id="searchIV"
    private ImageView searchIV; // Value injected by FXMLLoader

    @FXML // fx:id="editBtn"
    private Button editBtn; // Value injected by FXMLLoader

    @FXML
    void edit(ActionEvent event) {
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
    void Show(ActionEvent event) {
    	searchTF.setVisible(true);
    	tableVB.setVisible(true);
    	tableV.setVisible(true);
    	searchTF.setVisible(true);
    	searchIV.setVisible(true);

        refreshList();
        //tableV.setItems(questionsOL);

       FilteredList<QuestionTeacher> filteredQuests = new FilteredList<>(questionsOL, b -> true);

        searchTF.textProperty().addListener((observable, oldValue, newValue) -> {
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

        tableV.setItems(filteredQuests);

        tableV.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                QuestionTeacher questionSelected = tableV.getSelectionModel().getSelectedItem();
                if (questionSelected != null && event.getClickCount() == 2) {
                    Scene scene = null;
                    try {
                        for (Question q : questsOfTeacher)
                        {
                            if (q.getId() == Integer.parseInt(questionSelected.getId())) {
                                EditInterface.setQuestion(q);
                                break;
                            }
                        }
                        scene = new Scene(menuInterface.loadFXML("editInterface"));
                    } catch (IOException e) {
                        System.out.println("not found");
                        e.printStackTrace();
                    }
                    Stage stage = (Stage) tableV.getScene().getWindow();
                    stage.setScene(scene);
                    stage.setTitle("Edit Question");
                }
            }
        });
    }

    @FXML
    void logout(ActionEvent event) throws IOException{
        Scene scene = new Scene(loadFXML("loginInterface"));
        Stage stage = (Stage) logoutBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login");
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainClass.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void receivedCommandFromServer(Command command){
        commandFromServer = command;
        System.out.println("Command received in controller " + command);
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        menuInterface.user = user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeUser();
    }

    public void initializeUser(){
        helloLB.setText("Hello " + user.getFirst_name());
    }
    public void refreshList() {
        questsOfTeacher = new ArrayList<Question>();
        List<Subject> subjects = ((Teacher) user).getSubjects();
        for (Subject subject : subjects)
            questsOfTeacher.addAll(subject.getQuestions());

        columnId.setCellValueFactory(new PropertyValueFactory<QuestionTeacher, String>("id"));
        columnQuestion.setCellValueFactory(new PropertyValueFactory<QuestionTeacher, String>("question"));
        columnAuthor.setCellValueFactory(new PropertyValueFactory<QuestionTeacher, String>("author"));
        columnSubject.setCellValueFactory(new PropertyValueFactory<QuestionTeacher, String>("subject"));

        questionsOL= FXCollections.observableArrayList();
        for (Question quest : questsOfTeacher){
            questionsOL.add(new QuestionTeacher(String.valueOf(quest.getId()), quest.getQuestion(),
                    quest.getWriter().getUsername(),
                    quest.getSubject().getSubjectName()));
        }
    }
}
