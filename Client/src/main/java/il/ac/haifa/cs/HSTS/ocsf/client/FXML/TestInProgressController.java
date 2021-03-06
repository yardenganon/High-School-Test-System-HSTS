package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClient;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Bundle;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.CustomProgressIndicator;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Events;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.TestToWordUnit;
import il.ac.haifa.cs.HSTS.server.CommandInterface.AnswerableTestUpdateCommand;
import il.ac.haifa.cs.HSTS.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.server.CommandInterface.Response;
import il.ac.haifa.cs.HSTS.server.CommandInterface.TimeExtensionStatusCommand;
import il.ac.haifa.cs.HSTS.server.Entities.*;
import il.ac.haifa.cs.HSTS.server.Status.Status;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestInProgressController implements Initializable {
    private final String PROJECT_ID = "hsts-278212";
    private final String UNIQUE_BUCKET_NAME = "hsts-278212.appspot.com";

    @FXML
    private Label questionTextLabel, questionNumberLabel, additionalTimeLabel, testLable, bottomTestLable;
    @FXML
    private Label hoursLabel;


    @FXML
    private AnchorPane manualAnchorPane;

    @FXML
    private Label hoursLabel1;

    @FXML
    private Label minutesLabel1;

    @FXML
    private Label secondsLabel1;

    @FXML
    private Label linkLabel;

    @FXML
    private TextField textField;

    @FXML
    private Button browseButton;

    @FXML
    private Button uploadButton;

    @FXML
    private Pane additionalTimePane;
    @FXML
    private Label minutesLabel;

    @FXML
    private Label secondsLabel;

    @FXML
    private RadioButton answer1RadioBtn, answer2RadioBtn, answer3RadioBtn, answer4RadioBtn;

    @FXML
    private Pane leftArrowPane, rightArrowPane, pane1, pane2;

    @FXML
    private HBox questionsHbox;

    @FXML
    private Button questionsAnsweredLabel;

    @FXML
    private Button submitTestBtn, questionAnsweredLabel;

    @FXML
    private AnchorPane mainPane, mainQuestionRubric;

    @FXML
    private VBox mainVbox;


    final private ToggleGroup group = new ToggleGroup();

    private Bundle bundle;
    private Student student;
    private HSTSClient client;

    private List<Label> questionsLabelsList;
    private List<Question> questionList;
    private Question questionSelected;
    private int questionNumber;

    private Map<Question, Integer> answers;

    private AnswerableTest answerableTest;
    private int numberOfQuestionsAnswered;
    private int numberOfQuestions;

    // Timer
    private Timer timer;
    private int hours, minutes, seconds;

    // ExtraTime in minutes
    private int extraTime;

    private TestTimerTask testTimerTask;

    private Response responseFromServer = null;

    private Boolean isManualTest = false;

    private String fullPath = null;

    private String manualTestFileName = null;

    private boolean isExtraTimeAdded = false;

    private boolean uploadFlag = false;

    private Response timeExtensionResponseFromServer = null;

    private TimeExtensionRequest timeExtensionRequest = null;

    private volatile boolean running = true;



    @FXML
    void endTest(ActionEvent event) {
        if (uploadFlag || !isManualTest) {
            endTest();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("ALERT");
            alert.setHeaderText("Upload test please");
            alert.setContentText("Please upload the test before submitting");
            alert.setResizable(true);
            alert.getDialogPane().setPrefSize(300, 200);
            Optional<ButtonType> result = alert.showAndWait();
        }
    }


    public void openFile() {
        // Open file here
        try {
            //constructor of file class having file as argument
            System.out.println(fullPath);
            File file = new File(fullPath);
            System.out.println(file.getAbsolutePath());
            if (!Desktop.isDesktopSupported())//check if Desktop is supported by Platform or not
            {
                System.out.println("not supported");
                return;
            }
            Desktop desktop = Desktop.getDesktop();
            if (file.exists())         //checks file exists or not
                desktop.open(file);              //opens the specified file
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void uploadEvent() {
        uploadFlag = true;

        // Pass authentication credentials
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                CustomProgressIndicator customProgressIndicator = new CustomProgressIndicator(manualAnchorPane);
                customProgressIndicator.start();
                Storage storage = null;
                try {
                    System.out.println(new File("").getAbsolutePath());
                    String path = new File(("")).getAbsolutePath();
                    //String suffixPath = "\\src\\main\\resources\\il\\ac\\haifa\\cs\\HSTS\\ocsf\\client\\FXML\\GoogleCloudCreds\\HSTS-aa9bed24b973.json";
                    String suffixPath = MainClass.class.getResource("GoogleCloudCreds/HSTS-aa9bed24b973.json").getPath();
                    File fileNew = new File(suffixPath);
                    if (fileNew.exists()) {
                        Credentials credentials = GoogleCredentials
                                .fromStream(new FileInputStream(suffixPath));
                        storage = StorageOptions.newBuilder().setCredentials(credentials)
                                .setProjectId("114618954393906762898").build().getService();
                    } else
                    {
                        Credentials credentials = GoogleCredentials
                                .fromStream(new FileInputStream("\\Client" +suffixPath));
                        storage = StorageOptions.newBuilder().setCredentials(credentials)
                                .setProjectId("114618954393906762898").build().getService();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String projectId = PROJECT_ID;
                String bucketName = UNIQUE_BUCKET_NAME;
                String objectName = manualTestFileName;
                String filePath = linkLabel.getText();
                // The ID of your GCP project
                // String projectId = "your-project-id";

                // The ID of your GCS bucket
                // String bucketName = "your-unique-bucket-name";

                // The ID of your GCS object
                // String objectName = "your-object-name";

                // The path to your file to upload
                // String filePath = "path/to/your/file"

                //storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
                BlobId blobId = BlobId.of(bucketName, objectName);
                BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
                URL url = null;
                try {
                    storage.create(blobInfo, Files.readAllBytes(Paths.get(filePath)));
                    System.out.println(storage.get(blobId));
                    String link = "https://storage.cloud.google.com/"+UNIQUE_BUCKET_NAME+"/"+objectName;
                    url = new URL(link);
                    System.out.println(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // save url into db
                answerableTest.setUrl(url);

                customProgressIndicator.stop();
                System.out.println(
                        "File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName);

            }
        });

    }


    public void timeExtensionThread() {
        client.getHstsClientInterface().addGUIController(this);
        Thread thread = new Thread(() ->{
            while (running){
                // Check if there is TimeExtensionRequest every 20 sec
                // command
                System.out.println("On TE Thread");
                CommandInterface command = new TimeExtensionStatusCommand(answerableTest.getTest().getId());
                client.getHstsClientInterface().sendCommandToServer(command);
                // busyWait
                while (timeExtensionResponseFromServer == null) {
                    for (int i = 0 ; i < 2 ;i++) {
                        try {
                            if (timeExtensionResponseFromServer == null) {
                                Thread.sleep(1000*5);
                            }
                            else break;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                System.out.println("Response arrived TE Thread");
                if (timeExtensionResponseFromServer.getReturnedObject() != null)
                    timeExtensionRequest = (TimeExtensionRequest) timeExtensionResponseFromServer.getReturnedObject();
                // get respond returned object into timeExtensionRequest
                System.out.println(timeExtensionResponseFromServer);
                if (timeExtensionResponseFromServer.getReturnedObject() != null &&
                        ((TimeExtensionRequest) timeExtensionResponseFromServer.getReturnedObject())
                                .getStatus() == Status.Approved) {
                    System.out.println("TE Approved, adding time TE Thread");
                    timeExtensionRequest = (TimeExtensionRequest) timeExtensionResponseFromServer.getReturnedObject();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            int timeToAdd = timeExtensionRequest.getTimeToAdd();
                            addExtraTime(timeToAdd);
                        }
                    });
                    System.out.println("TE Approved, additional time has been added TE Thread");
                    break;
                }
                // Check every 1-minute
                try {
                    System.out.println("TE Thread going to sleep for a minute");
                    Thread.sleep(1000*10);
                    System.out.println("TE Thread woke up from a minute");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("TE Thread has been disabled");
        });
        thread.start();
    }

    public void timeExtensionResponseFromServer(Response response){
        timeExtensionResponseFromServer = response;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bundle = Bundle.getInstance();
        student = (Student) bundle.get("student");
        client = (HSTSClient) bundle.get("client");
        client.getHstsClientInterface().getGuiControllers().clear();
        client.getHstsClientInterface().addGUIController(this);
        bundle.put("runningStatus",running);
        System.out.println("In init: " + client.getHstsClientInterface().getGuiControllers().get(TestInProgressController.class.getSimpleName()));
        // Dummy init
        //initDummyData();
        loadAnswerableTest();
        loadIntroAndEpilogueText();
        if (!isManualTest) {
            initQuestionsFromAnswerableTest();
            initRadioButtons();
            initHBox();
            loadQuestion(1);
            initNumberOfQuestions();

        } else {
            changePanes();
            makeManualTest();
        }
        notifyTestIsStarting();
        calculateTimeDiff();
        timeExtensionThread();
        //initTimer(answerableTest.getTest().getModifiedTime());
    }

    public void calculateTimeDiff() {
        long modifiedTime = this.answerableTest.getTest().getModifiedTime();
        long timePassed = getDateDiff(this.answerableTest.getTimeStarted(),new Date(),TimeUnit.MINUTES);
        System.out.println("Modified time: " + modifiedTime + " Time left: "+timePassed);
        if (timePassed > modifiedTime)
              endTest();
        else {
            initTimer((int)(modifiedTime-timePassed));
        }
        if (this.hours < 0)
            endTest();
    }

    public void makeManualTest() {
        TestToWordUnit testToWordUnit = null;
        try {
            testToWordUnit = new TestToWordUnit(answerableTest.getTest(), answerableTest.getStudent());
            fullPath = testToWordUnit.getFilePath();
            manualTestFileName = testToWordUnit.getFileName();
            linkLabel.setText(fullPath);
            textField.setText(fullPath);
        } catch (Exception exception) {
            exception.printStackTrace();
        }


    }

    public void changePanes() {
        mainVbox.setVisible(false);
        mainVbox.setDisable(true);
        pane1.setVisible(false);
        pane1.setDisable(true);
        pane2.setVisible(false);
        pane2.setDisable(true);
        additionalTimePane.setVisible(false);
        additionalTimePane.setDisable(true);
        questionAnsweredLabel.setVisible(false);
        questionAnsweredLabel.setDisable(true);
        questionsAnsweredLabel.setVisible(false);
        questionsAnsweredLabel.setDisable(true);

        manualAnchorPane.setVisible(true);
        manualAnchorPane.setDisable(false);
    }

    public void loadIntroAndEpilogueText() {
        String introduction = this.answerableTest.getTest().getTest().getIntroduction();
        String epilogue = this.answerableTest.getTest().getTest().getEpilogue();
        if (introduction != null)
            testLable.setText(introduction);
        if (epilogue != null) {
            bottomTestLable.setText(epilogue);
        }
    }

    public void endTest() {
        // endTestLogic
        if (this.testTimerTask != null) {
            testTimerTask.cancel();
        } else
            System.out.println("Time is up....");

        // Send to check and update
        running = false;
        this.answerableTest.setAnswerableTestStatus(Status.TestFinished);
        this.answerableTest.setTimeFinished(new Date());
        // exam auto-review
        ExamReview examReview = new ExamReview(answerableTest);
        int grade = examReview.checkExam();
        System.out.println(grade);
        this.answerableTest.setScore(grade);

        sendAnswerableTestToServerForBackup();

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
        if (!isManualTest) {
            hoursLabel.setText(String.valueOf(hours));
            minutesLabel.setText(String.valueOf(minutes));
            secondsLabel.setText(String.valueOf(seconds));
        } else {
            hoursLabel1.setText(String.valueOf(hours));
            minutesLabel1.setText(String.valueOf(minutes));
            secondsLabel1.setText(String.valueOf(seconds));
        }
        timer = new Timer();
        this.testTimerTask = new TestTimerTask();
        bundle.put("testTimerTask", this.testTimerTask);
        timer.schedule(testTimerTask, 0, 1000);
    }

    class TestTimerTask extends TimerTask {
        @Override
        public void run() {
            Platform.runLater(() -> {
                seconds--;
                System.out.println("Hours: " + hours + " Minutes: " + minutes + " Seconds: " + seconds);
                if (hours == (minutes) && minutes == (seconds) && seconds == (0)) {
                    this.cancel();
                    testTimerTask = null;
                    endTest();
                } else if (minutes == (0) && seconds == (-1)) {
                    hours--;
                    minutes = 59;
                    seconds = 59;
                } else if (seconds == -1) {
                    minutes--;
                    seconds = 59;
                }
                if (!isManualTest) {
                    secondsLabel.setText(String.valueOf(seconds));
                    minutesLabel.setText(String.valueOf(minutes));
                    hoursLabel.setText(String.valueOf(hours));
                } else {
                    secondsLabel1.setText(String.valueOf(seconds));
                    minutesLabel1.setText(String.valueOf(minutes));
                    hoursLabel1.setText(String.valueOf(hours));
                }
            });
        }
    }

    public void initHBox() {
        questionsLabelsList = new ArrayList<>();
        for (int i = 0; i < questionList.size(); i++) {
            Label label = new Label(String.valueOf(i + 1));
            label.setFont(Font.font("Calibri Light", FontPosture.ITALIC, 45));
            label.setTextFill(Color.web("#c4c4c4"));
            questionsHbox.getChildren().add(label);
            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    for (int i = 0; i < questionList.size(); i++) {
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
                        answers.put(questionSelected, 1);
                    else if (group.getSelectedToggle() == answer2RadioBtn)
                        answers.put(questionSelected, 2);
                    else if (group.getSelectedToggle() == answer3RadioBtn)
                        answers.put(questionSelected, 3);
                    else if (group.getSelectedToggle() == answer4RadioBtn)
                        answers.put(questionSelected, 4);
                }
                numberOfQuestionsAnswered = answers.size();
                questionsAnsweredLabel.setText(numberOfQuestionsAnswered + "/" + numberOfQuestions);
            }
        });

        rightArrowPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                questionsLabelsList.get(questionNumber - 1).setTextFill(Color.web("#c4c4c4"));
                loadQuestion(++questionNumber);
            }
        });
        leftArrowPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                questionsLabelsList.get(questionNumber - 1).setTextFill(Color.web("#c4c4c4"));
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
        this.questionsLabelsList.get(questionNumber - 1).setTextFill(Color.web("#143452"));
        questionNumberLabel.setText(String.valueOf(this.questionNumber));
        questionSelected = questionList.get(questionNumber - 1);
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
        } else {
            switch (answers.get(questionSelected)) {
                case 1:
                    group.selectToggle(answer1RadioBtn);
                    break;
                case 2:
                    group.selectToggle(answer2RadioBtn);
                    break;
                case 3:
                    group.selectToggle(answer3RadioBtn);
                    break;
                case 4:
                    group.selectToggle(answer4RadioBtn);
                    break;
            }
        }
    }
    public int calculateQuestionsAnswered() {
        int count = 0;
        for (Question question : questionList)
        {
            if (answerableTest.getAnswers().get(question) !=null)
              count++;
        }
        return count;
    }
    public void initNumberOfQuestions() {
        numberOfQuestionsAnswered = calculateQuestionsAnswered();
//        for (Question question : answerableTest.getQuestionsSet()){
//            if (answerableTest.getQuestionsSet().contains(question))
//                numberOfQuestionsAnswered++;
//        }
        numberOfQuestions = questionList.size();
        questionsAnsweredLabel.setText(numberOfQuestionsAnswered + "/" + numberOfQuestions);
    }

    public long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }

    public void addExtraTime(int extraTime) {
        this.extraTime = extraTime;
        int additionalHours = extraTime / 60;
        int additionalMinutes = extraTime % 60;
        this.hours += additionalHours;
        this.minutes += additionalMinutes;
        additionalTimePane.setVisible(true);
        additionalTimePane.setDisable(false);
        additionalTimeLabel.setText("+ " + additionalMinutes + " minutes");
    }

    public void notifyTestIsStarting() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if (this.answerableTest.getTimeStarted() == null) {
            alert.setTitle("Test is about to start");
            alert.setHeaderText("Test Session");
            alert.setContentText("Press enter to start the test");
        }
        else {
            alert.setTitle("Test is already started");
            alert.setHeaderText("Test Session");
            alert.setContentText("Press enter to get back to the session");
        }
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(300, 200);
        Optional<ButtonType> result = alert.showAndWait();

    }

    public void loadAnswerableTest() {
        answerableTest = (AnswerableTest) bundle.get("answerableTest");
        if (answerableTest.getTest().getManual() == true)
            isManualTest = true;
        if (answerableTest.getTimeStarted() == null)
            this.answerableTest.setTimeStarted(new Date());

    }

    public void initDummyData() {
        Teacher teacher2 = new Teacher("Joel_Nakaka", "1234", "ynak@gmail.com", "Joel", "Nakaka", "male");

        Subject subject2 = new Subject("Science");
        subject2.addTeacher(teacher2);

        Question question6 = new Question("What is the speed of light?", "~300000 KM/SEC", "~200000 KM/SEC", "~1080000000 KM/SEC", "~400000 KM/SEC", 1, teacher2, subject2);
        Question question7 = new Question("What is the name of the smallest body bone?", "wrist", "knee", "tail", "stapes", 3, teacher2, subject2);
        Question question8 = new Question("Simplify: 12a + 26b -4b – 16a", "4a+22b", "-4a+22b", "-9a+30b", "2a+3b", 2, teacher2, subject2);
        Question question9 = new Question("What is |-26|?", "-26", "26", "0", "-1", 1, teacher2, subject2);
        Question question10 = new Question("Which is bigger planet?", "Earth", "Sun", "Earth-moon", "Venus", 2, teacher2, subject2);
        Question question16 = new Question("Humans and chimpanzees share roughly how much DNA?", "90%", "98%", "85%", "88%", 2, teacher2, subject2);
        Question question17 = new Question("What is the most abundant gas in the Earth’s atmosphere?", "Nitrogen", "CO2", "Hainkenotrydomo", "Oxygen", 1, teacher2, subject2);
        Question question18 = new Question("Which famous British physicist wrote A Brief History of Time?", "Sir Arthur Stanley Eddington", "Oliver Heaviside", "Edward Victor Appleton", "Stephen Hawking", 4, teacher2, subject2);
        Question question19 = new Question("Find the value of 3 + 2 • (8 – 3):", "25", "13", "17", "24", 2, teacher2, subject2);
        Question question20 = new Question("Factor: 16w^3 – u^4 * w^3:", "w^3(4 + u^2)(2 + u)(2 - u)", "w^3(4 + u^2)(2 - u)", "w^3(4 + u^2)(2 + u)", "w^3(4 + u^2)(4 + u)", 1, teacher2, subject2);

        Student student = new Student("yoav_ben_haim", "1234", "yovavi@gmail.com", "Yoav", "Ben Haim", "Male");
        Student student1 = new Student("yarden_ganon", "1234", "yardenganon@gmail.com", "Yarden", "Ganon", "Male");
        Student student2 = new Student("daniel_levi", "1234", "levidaniel@gmail.com", "Daniel", "Levi", "Female");
        Student student3 = new Student("ohad_fridman", "1234", "ohadfridman@gmail.com", "Ohad", "Fridman", "Male");

        Course scienceADV = new Course("ScienceADV", subject2, teacher2);
        scienceADV.addStudent(student);

        Test test = new Test(teacher2, subject2);
        test.addQuestion(question6, 10);
        test.addQuestion(question7, 10);
        test.addQuestion(question8, 10);
        test.addQuestion(question9, 10);
        test.addQuestion(question10, 10);
        test.addQuestion(question16, 10);
        test.addQuestion(question17, 10);
        test.addQuestion(question18, 10);
        test.addQuestion(question19, 10);
        test.addQuestion(question20, 10);
        test.setCommentForTeachers("Test for idiots, very EZ");
        test.setEpilogue("Good Luck");
        test.setIntroduction("Answer all questions please, every question = 10 points");
        test.setTime(20);

        ReadyTest readyTest = new ReadyTest(test, "1234", scienceADV, teacher2);

        AnswerableTest answerableTest = new AnswerableTest(readyTest, student);
        this.answerableTest = answerableTest;
    }

    public void sendAnswerableTestToServerForBackup() {
        CustomProgressIndicator customProgressIndicator = new CustomProgressIndicator(mainPane);
        customProgressIndicator.start();
        client.getHstsClientInterface().addGUIController(this);
        Task<Response> task = new Task<Response>() {
            @Override
            protected Response call() throws Exception {
                AnswerableTestUpdateCommand command = new AnswerableTestUpdateCommand(answerableTest);
                client.getHstsClientInterface().sendCommandToServer(command);

                while (responseFromServer == null) {
                    Thread.sleep(10);
                }

                return responseFromServer;
            }
        };
        task.setOnSucceeded(e -> {
            responseFromServer = task.getValue();
            customProgressIndicator.stop();
            System.out.println(responseFromServer.getStatus());
            AnswerableTest updatedAnswerableTest = (AnswerableTest) responseFromServer.getReturnedObject();
            System.out.println(updatedAnswerableTest);
            responseFromServer = null;

            // Automatic test-check

            // Display test summary

            openSummaryWindow();
        });
        new Thread(task).start();
    }

    public void receivedResponseFromServer(Response response) {
        responseFromServer = response;
        System.out.println("Command received in controller " + response);
    }

    public void openSummaryWindow() {
        running = false;
        Stage testSummaryStage = new Stage();
        Scene scene = null;
        try {
            scene = new Scene(MainClass.loadFXML("TestSummary"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        testSummaryStage.setScene(scene);
        testSummaryStage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                Events.navigateMenuEvent(questionAnsweredLabel);
            }
        });
        testSummaryStage.initModality(Modality.APPLICATION_MODAL);
        bundle.put("testInProgressStage", (Stage) testLable.getScene().getWindow());
        testSummaryStage.show();


    }
}

