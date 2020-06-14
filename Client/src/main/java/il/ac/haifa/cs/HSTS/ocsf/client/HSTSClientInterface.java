package il.ac.haifa.cs.HSTS.ocsf.client;

import il.ac.haifa.cs.HSTS.ocsf.client.FXML.*;
import il.ac.haifa.cs.HSTS.server.CommandInterface.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// That was CLIChatClient
public class HSTSClientInterface {

    private HSTSClient client;
    private Map<String, Object> guiControllers;
    private boolean isRunning;
    private Thread loopThread;

    public HSTSClientInterface(HSTSClient client) {
        this.client = client;
        this.isRunning = false;
        this.guiControllers = new HashMap<>();
    }

    public void addGUIController(Object object) {
        guiControllers.put(object.getClass().getSimpleName(), object);
    }

    public void loop() throws IOException {
        loopThread = new Thread(new Runnable() {
            @Override
            public void run() {
            }
        });

        loopThread.start();
        this.isRunning = true;

    }

    public void sendCommandToServer(CommandInterface command) {
        try {
            System.out.println("Sending command to server");
            client.sendToServer(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HSTSClient getClient() {
        return client;
    }

    // Was displayMessage function in SimpleChatCLI
    public void commandFromServerHandler(Object message) {
        if (isRunning) {
            //System.out.print("(Interrupted)\n");
        }
        Response serverResponse = (Response) message;
        System.out.println("Command received from server : " + serverResponse.getRespondName());
        System.out.println("Command returned object : " + (serverResponse.getReturnedObject() != null ? serverResponse.getReturnedObject() : "null"));

        if (serverResponse.getRespondName().equals(LoginCommand.class.getSimpleName())
                && guiControllers.get(LoginController.class.getSimpleName()) != null){
            ((LoginController) guiControllers.get(LoginController.class.getSimpleName()))
                    .receivedRespondFromServer(serverResponse);
        }
        if (serverResponse.getRespondName().equals(QuestionReadBySubjectCommand.class.getSimpleName())
                && guiControllers.get(QuestionsController.class.getSimpleName()) != null) {
            // Getting questions asked for
            System.out.println("subjects with question received: " + serverResponse.getReturnedObject());
            ((QuestionsController) guiControllers.get(QuestionsController.class.getSimpleName())).
                    receivedRespondFromServer(serverResponse);
        }

        if (serverResponse.getRespondName().equals(QuestionReadBySubjectCommand.class.getSimpleName())
                && guiControllers.get(CreateTestController.class.getSimpleName()) != null) {
            // Getting questions asked for
            System.out.println("subjects with question received: " + serverResponse.getReturnedObject());
            ((CreateTestController) guiControllers.get(CreateTestController.class.getSimpleName())).
                    receivedResponseFromServer(serverResponse);
        }

        if (serverResponse.getRespondName().equals(QuestionReadAllCommand.class.getSimpleName())
                && guiControllers.get(QuestionsController.class.getSimpleName()) != null) {
            // Getting questions asked for
            System.out.println("All question received: " + serverResponse.getReturnedObject());
            ((QuestionsController) guiControllers.get(QuestionsController.class.getSimpleName())).
                    receivedRespondFromServer(serverResponse);
        }

        if (serverResponse.getRespondName().equals(TestsFacadeReadBySubjectCommand.class.getSimpleName())
                && guiControllers.get(TestsController.class.getSimpleName()) != null) {
            System.out.println("All testFacade received: " + serverResponse.getReturnedObject());
            ((TestsController) guiControllers.get(TestsController.class.getSimpleName())).
                    receivedResponseFromServer(serverResponse);
        }
        if (serverResponse.getRespondName().equals(TestReadByIdCommand.class.getSimpleName())
                && guiControllers.get(TestsController.class.getSimpleName()) != null) {
            System.out.println("Test received: " + serverResponse.getReturnedObject());
            ((TestsController) guiControllers.get(TestsController.class.getSimpleName())).
                    receivedResponseFromServer(serverResponse);
        }
        if (serverResponse.getRespondName().equals(QuestionPushCommand.class.getSimpleName())
                && guiControllers.get(EditQuestionController.class.getSimpleName()) != null)
            ((EditQuestionController) guiControllers.get(EditQuestionController.class.getSimpleName()))
                    .receivedResponseFromServer(serverResponse);

        if (serverResponse.getRespondName().equals(QuestionPushCommand.class.getSimpleName())
                && guiControllers.get(CreateQuestionController.class.getSimpleName()) != null)
            ((CreateQuestionController) guiControllers.get(CreateQuestionController.class.getSimpleName()))
                    .receivedResponseFromServer(serverResponse);

        if (serverResponse.getRespondName().equals(TestPushCommand.class.getSimpleName())
                && guiControllers.get(CreateTestController.class.getSimpleName()) != null) {
            System.out.println("Test received: " + serverResponse.getReturnedObject());
            ((CreateTestController) guiControllers.get(CreateTestController.class.getSimpleName())).
                    receivedResponseFromServer(serverResponse);
        }

        if (serverResponse.getRespondName().equals(TestReadByIdCommand.class.getSimpleName())
                && guiControllers.get(MakeExecuteTestController.class.getSimpleName()) != null)
            ((MakeExecuteTestController) guiControllers.get(MakeExecuteTestController.class.getSimpleName()))
                    .receivedResponseFromServer(serverResponse);

        if (serverResponse.getRespondName().equals(CreateReadyTestCommand.class.getSimpleName())
                && guiControllers.get(MakeExecuteTestController.class.getSimpleName()) != null)
            ((MakeExecuteTestController) guiControllers.get(MakeExecuteTestController.class.getSimpleName()))
                    .receivedResponseFromServer(serverResponse);
        if (serverResponse.getRespondName().equals(RequestAnswerableTestCommand.class.getSimpleName())
                && guiControllers.get(EnterExecutionCodePopup.class.getSimpleName()) != null)
            ((EnterExecutionCodePopup) guiControllers.get(EnterExecutionCodePopup.class.getSimpleName()))
                    .receivedResponseFromServer(serverResponse);


        if (serverResponse.getRespondName().equals(AnswerableTestsFacadeReadCommand.class.getSimpleName())
                && guiControllers.get(TestCheckingController.class.getSimpleName()) != null)
            ((TestCheckingController) guiControllers.get(TestCheckingController.class.getSimpleName()))
                    .receivedResponseFromServer(serverResponse);

        if (serverResponse.getRespondName().equals(AnswerableTestReadCommand.class.getSimpleName())
                && guiControllers.get(CheckAnswerableTestController.class.getSimpleName()) != null)
            ((CheckAnswerableTestController) guiControllers.get(CheckAnswerableTestController.class.getSimpleName()))
                    .receivedResponseFromServer(serverResponse);

        if (serverResponse.getRespondName().equals(AnswerableTestsFacadeReadByStudentCommand.class.getSimpleName())
                && guiControllers.get(TestCheckingController.class.getSimpleName()) != null)
            ((TestCheckingController) guiControllers.get(TestCheckingController.class.getSimpleName()))
                    .receivedResponseFromServer(serverResponse);



        // Update answerableTest to TestInProgress
        if (serverResponse.getRespondName().equals(AnswerableTestUpdateCommand.class.getSimpleName())
                && guiControllers.get(TestInProgressController.class.getSimpleName()) != null)
            ((TestInProgressController) guiControllers.get(TestInProgressController.class.getSimpleName()))
                    .receivedResponseFromServer(serverResponse);

        if (serverResponse.getRespondName().equals(AnswerableTestReadCommand.class.getSimpleName())
                && guiControllers.get(EnterExecutionCodePopup.class.getSimpleName()) != null)
        ((EnterExecutionCodePopup) guiControllers.get(EnterExecutionCodePopup.class.getSimpleName()))
                .receivedResponseFromServer(serverResponse);
        // Extra time request
        if (serverResponse.getRespondName().equals(TimeExtensionStatusCommand.class.getSimpleName())
                && guiControllers.get(TestInProgressController.class.getSimpleName()) != null)
            ((TestInProgressController) guiControllers.get(TestInProgressController.class.getSimpleName()))
                    .timeExtensionResponseFromServer(serverResponse);

        if (serverResponse.getRespondName().equals(AnswerableTestUpdateByIdCommand.class.getSimpleName()))
            ((TestCheckingController) guiControllers.get(TestCheckingController.class.getSimpleName()))
                    .receivedResponseFromServer(serverResponse);


        if (serverResponse.getRespondName().equals(ReadyTestExtendedFacadeReadByTeacherCommand.class.getSimpleName())
                && guiControllers.get(MenuController.class.getSimpleName()) != null)
            ((MenuController) guiControllers.get(MenuController.class.getSimpleName()))
                    .receivedResponseFromServer(serverResponse);

        if (serverResponse.getRespondName().equals(RequestTimeExtensionCommand.class.getSimpleName()))
            ((MenuController) guiControllers.get(MenuController.class.getSimpleName()))
                    .receivedResponseFromServer(serverResponse);

        if (serverResponse.getRespondName().equals(ReadyTestReadByIdCommand.class.getSimpleName()))
            ((MenuController) guiControllers.get(MenuController.class.getSimpleName()))
                    .receivedResponseFromServer(serverResponse);

        if (serverResponse.getRespondName().equals(TimeExtensionRequestUpdateCommand.class.getSimpleName()))
            ((MenuController) guiControllers.get(MenuController.class.getSimpleName()))
            .receivedResponseFromServer(serverResponse);

        if (serverResponse.getRespondName().equals(TimeExtensionReadAllCommand.class.getSimpleName()))
            ((MenuController) guiControllers.get(MenuController.class.getSimpleName()))
                    .receivedResponseFromServer(serverResponse);

        if (serverResponse.getRespondName().equals(ReadyTestUpdateActivityCommand.class.getSimpleName()))
            ((MenuController) guiControllers.get(MenuController.class.getSimpleName()))
                    .receivedResponseFromServer(serverResponse);

        if (serverResponse.getRespondName().equals(AnswerableTestsFacadeReadCommand.class.getSimpleName())
            && guiControllers.get(TeacherAnswerableTestsController.class.getSimpleName()) != null)
            ((TeacherAnswerableTestsController) guiControllers.get(TeacherAnswerableTestsController.class.getSimpleName()))
                    .receivedResponseFromServer(serverResponse);
    }

    public Map<String, Object> getGuiControllers() {
        return guiControllers;
    }

    public void closeConnection() {
        System.out.println("Connection closed.");
        System.exit(0);
    }

    public static void main(String[] args) throws IOException {
        MainClass.main(args);
    }
}
