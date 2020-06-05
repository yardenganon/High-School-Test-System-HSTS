package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.server.Entities.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TestInProgressController implements Initializable {

    @FXML
    private Label questionTextLabel, questionNumberLabel, timerLabel, courseLabel, testLable;

    @FXML
    private RadioButton answer1RadioBtn, answer2RadioBtn, answer3RadioBtn, answer4RadioBtn;

    @FXML
    private Pane leftArrowPane, rightArrowPane;

    @FXML
    private HBox questionsHbox;

    @FXML
    private Button questionsAnsweredLabel;

    @FXML
    private Button submitTestBtn;

    @FXML
    private AnchorPane mainQuestionRubric;

    final private ToggleGroup group = new ToggleGroup();

    private List<Label> questionsLabelsList;
    private List<Question> questionList;
    private Question questionSelected;
    private int questionNumber;

    private AnswerableTest answerableTest;

    // Timer
    private Timer timer;
    private TimerTask timerTask;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Dummy init
        initDummyData();
        initQuestionsFromAnswerableTest();

        initRadioButtons();
        initHBox();
        loadQuestion(1);
        initTimer();


    }
    public void initQuestionsFromAnswerableTest() {
        Set<Question> questionsSet = this.answerableTest.getQuestionsSet();
        questionList = new ArrayList<>();
        for (Question question : questionsSet)
            questionList.add(question);
    }
    public void initTimer() {
        String input = "10:10:10";
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = null;
//        String dateString = dateFormat.format(date);
        timerTask = new TimerTask() {
            @Override
            public void run() {
//                timerLabel.setText(dateString);
//                dateString = dateString
            }
        };

    }
    public void initHBox() {
        questionsLabelsList = new ArrayList<>();
        for (int i = 0 ; i < questionList.size() ; i++) {
            Label label = new Label(String.valueOf(i+1));
            label.setFont(Font.font("Calibri Light", FontPosture.ITALIC,45));
            label.setTextFill(Color.web("#c4c4c4"));
            questionsHbox.getChildren().add(label);
            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    for ( int i = 0 ; i < questionList.size() ; i++) {
                        if (i + 1 != questionNumber)
                            questionsLabelsList.get(questionNumber - 1).setTextFill(Color.web("#c4c4c4"));
                    }
                    loadQuestion(Integer.parseInt(label.getText()));
                }
            });
            questionsLabelsList.add(label);
        }
    }

    public void initRadioButtons() {
        answer1RadioBtn.setToggleGroup(group);
        answer2RadioBtn.setToggleGroup(group);
        answer3RadioBtn.setToggleGroup(group);
        answer4RadioBtn.setToggleGroup(group);

        rightArrowPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                questionsLabelsList.get(questionNumber-1).setTextFill(Color.web("#c4c4c4"));
                loadQuestion(++questionNumber);
            }
        });
        leftArrowPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                questionsLabelsList.get(questionNumber-1).setTextFill(Color.web("#c4c4c4"));
                loadQuestion(--questionNumber);
            }
        });

    }
    public void loadQuestion(int questionNumber) {
        if (questionNumber == 1)
            this.leftArrowPane.setDisable(true);
        else if (questionNumber == questionList.size())
            this.rightArrowPane.setDisable(true);
        else {
            this.leftArrowPane.setDisable(false);
            this.rightArrowPane.setDisable(false);
        }
        this.questionNumber = questionNumber;
        this.questionsLabelsList.get(questionNumber-1).setTextFill(Color.web("#143452"));
        questionNumberLabel.setText(String.valueOf(this.questionNumber));
        questionSelected = questionList.get(questionNumber-1);
        questionTextLabel.setText(questionSelected.getQuestion());
        answer1RadioBtn.setText(questionSelected.getAnswer(1));
        answer2RadioBtn.setText(questionSelected.getAnswer(2));
        answer3RadioBtn.setText(questionSelected.getAnswer(3));
        answer4RadioBtn.setText(questionSelected.getAnswer(4));
    }



    public void initDummyData() {
//        Question question = new Question("Solve that equation: X+5=10","3","2","5","4.5",3,
//                new Teacher(("iodot_com"),"1234","k@gmil.com","simon","koli","male"),
//                new Subject("Math"));
//        Question question2 = new Question("Solve that equation: X+7=17","3","2","10","4.5",3,
//                new Teacher(("iodot_com"),"1234","k@gmil.com","simon","koli","male"),
//                new Subject("Math"));
//        Question question3 = new Question("The value of x + x(x^x) when x = 2 is:","10","16","19","36",1,
//                new Teacher(("io23dot_com"),"12234","k@gmil22.com","s22imon","ko2li","male"),
//                new Subject("Math"));
//        questionList = new ArrayList<>();
//        questionList.add(question);
//        questionList.add(question2);
//        questionList.add(question3);


        Teacher teacher2 = new Teacher("Joel_Nakaka","1234","ynak@gmail.com","Joel","Nakaka","male");

        Subject subject2 = new Subject("Science");
        subject2.addTeacher(teacher2);

        Question question6 = new Question("What is the speed of light?","~300000 KM/SEC","~200000 KM/SEC","~1080000000 KM/SEC","~400000 KM/SEC",1,teacher2,subject2);
        Question question7 = new Question("What is the name of the smallest body bone?","wrist","knee","tail","stapes",3,teacher2,subject2);
        Question question8 = new Question("Simplify: 12a + 26b -4b – 16a","4a+22b","-4a+22b","-9a+30b","2a+3b",2,teacher2,subject2);
        Question question9 = new Question("What is |-26|?","-26","26","0","-1",1,teacher2,subject2);
        Question question10 = new Question("Which is bigger planet?","Earth","Sun","Earth-moon","Venus",2,teacher2,subject2);
        Question question16 = new Question("Humans and chimpanzees share roughly how much DNA?","90%","98%","85%","88%",2,teacher2,subject2);
        Question question17 = new Question("What is the most abundant gas in the Earth’s atmosphere?","Nitrogen","CO2","Hainkenotrydomo","Oxygen",1,teacher2,subject2);
        Question question18 = new Question("Which famous British physicist wrote A Brief History of Time?","Sir Arthur Stanley Eddington","Oliver Heaviside","Edward Victor Appleton","Stephen Hawking",4,teacher2,subject2);
        Question question19 = new Question("Find the value of 3 + 2 • (8 – 3):","25","13","17","24",2,teacher2,subject2);
        Question question20 = new Question("Factor: 16w^3 – u^4 * w^3:","w^3(4 + u^2)(2 + u)(2 - u)","w^3(4 + u^2)(2 - u)","w^3(4 + u^2)(2 + u)","w^3(4 + u^2)(4 + u)",1,teacher2,subject2);


        Student student = new Student("yoav_ben_haim","1234","yovavi@gmail.com","Yoav","Ben Haim","Male");
        Student student1 = new Student("yarden_ganon","1234","yardenganon@gmail.com","Yarden","Ganon","Male");
        Student student2 = new Student("daniel_levi","1234","levidaniel@gmail.com","Daniel","Levi","Female");
        Student student3 = new Student("ohad_fridman","1234","ohadfridman@gmail.com","Ohad","Fridman","Male");


        Course scienceADV = new Course("ScienceADV", subject2,teacher2);
        scienceADV.addStudent(student);

        Test test = new Test(teacher2,subject2);
        test.addQuestion(question6,10);
        test.addQuestion(question7,10);
        test.addQuestion(question8,10);
        test.addQuestion(question9,10);
        test.addQuestion(question10,10);
        test.addQuestion(question16,10);
        test.addQuestion(question17,10);
        test.addQuestion(question18,10);
        test.addQuestion(question19,10);
        test.addQuestion(question20,10);
        test.setCommentForTeachers("Test for idiots, very EZ");
        test.setEpilogue("Good Luck");
        test.setIntroduction("Answer all questions please, every question = 10 points");
        test.setTime(20);


        ReadyTest readyTest = new ReadyTest(test, "1234",scienceADV,teacher2);

        AnswerableTest answerableTest = new AnswerableTest(readyTest, student);
        this.answerableTest = answerableTest;
    }
}

