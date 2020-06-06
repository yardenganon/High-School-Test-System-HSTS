package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.server.Entities.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
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
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TestInProgressController implements Initializable {

    @FXML
    private Label questionTextLabel, questionNumberLabel, courseLabel, testLable;
    @FXML
    private Label hoursLabel;

    @FXML
    private Label minutesLabel;

    @FXML
    private Label secondsLabel;

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
    
    private Map<Question,Integer> answers;

    private AnswerableTest answerableTest;
    private int numberOfQuestionsAnswered;
    private int numberOfQuestions;

    // Timer
    private Timer timer;
    private int hours, minutes, seconds;

    // ExtraTime in minutes
    private int extraTime;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Dummy init
        initDummyData();
        initQuestionsFromAnswerableTest();
        initRadioButtons();
        initHBox();
        loadQuestion(1);
        initTimer(answerableTest.getTest().getModifiedTime());
        initNumberOfQuestions();
    }
    public void endTest(){
        // endTestLogic
        System.out.println("Time is up....");
    }
    public void initQuestionsFromAnswerableTest() {
        Set<Question> questionsSet = this.answerableTest.getQuestionsSet();
        questionList = new ArrayList<>();
        for (Question question : questionsSet)
            questionList.add(question);
        answers = this.answerableTest.getAnswers();
    }
    public void initTimer(int timeInMinutes) {
        // init Hours, Minutes, Seconds
        hours = timeInMinutes / 60;
        minutes = timeInMinutes % 60;
        seconds = 0;
        hoursLabel.setText(String.valueOf(hours));
        minutesLabel.setText(String.valueOf(minutes));
        secondsLabel.setText(String.valueOf(seconds));
        timer = new Timer();
        timer.schedule(new updateTimeLabel(), 0, 1000);
    }
    class updateTimeLabel extends TimerTask{
        @Override
        public void run() {
            Platform.runLater(() ->{
                seconds--;
                System.out.println("Hours: "+hours+ " Minutes: "+minutes+ " Seconds: "+seconds);
                if (hours == (minutes) && minutes == (seconds) && seconds == (0)) {
                    this.cancel();
                    endTest();
                }
                else if (minutes == (0) && seconds == (-1)) {
                    hours--;
                    minutes = 59;
                    seconds = 59;
                }
                else if (seconds == -1) {
                    minutes--;
                    seconds = 59;
                }
                secondsLabel.setText(String.valueOf(seconds));
                minutesLabel.setText(String.valueOf(minutes));
                hoursLabel.setText(String.valueOf(hours));
            });
        }
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

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                if (group.getSelectedToggle() != null) {
                    if (group.getSelectedToggle() == answer1RadioBtn)
                        answers.put(questionSelected,1);
                    else if (group.getSelectedToggle() == answer2RadioBtn)
                        answers.put(questionSelected,2);
                    else if (group.getSelectedToggle() == answer3RadioBtn)
                        answers.put(questionSelected,3);
                    else if (group.getSelectedToggle() == answer4RadioBtn)
                        answers.put(questionSelected,4);
                }
                numberOfQuestionsAnswered = answers.size();
                questionsAnsweredLabel.setText(numberOfQuestionsAnswered + "/" + numberOfQuestions);
            }
        });

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
        loadQuestionAnswer();
    }
    public void loadQuestionAnswer() {
        if (answers.get(questionSelected) == null) {
            group.selectToggle(null);
        }
        else {
            switch (answers.get(questionSelected)) {
                case 1:
                    group.selectToggle(answer1RadioBtn); break;
                case 2:
                    group.selectToggle(answer2RadioBtn); break;
                case 3:
                    group.selectToggle(answer3RadioBtn); break;
                case 4:
                    group.selectToggle(answer4RadioBtn); break;
            }
        }
    }
    public void initNumberOfQuestions() {
        numberOfQuestionsAnswered = 0;
        numberOfQuestions = questionList.size();
        questionsAnsweredLabel.setText(numberOfQuestionsAnswered + "/" + numberOfQuestions);
    }

    public void addExtraTime(int extraTime){
        this.extraTime = extraTime;
    }

    public void initDummyData() {
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

