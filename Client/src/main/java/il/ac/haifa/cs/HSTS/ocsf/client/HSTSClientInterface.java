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

        if (serverResponse.getRespondName().equals(LoginCommand.class.getSimpleName())){
            ((LoginController) guiControllers.get(LoginController.class.getSimpleName()))
                    .receivedRespondFromServer(serverResponse);
        }
        if (serverResponse.getRespondName().equals(QuestionReadBySubjectCommand.class.getSimpleName())) {
            // Getting questions asked for
            System.out.println("subjects with question received: " + serverResponse.getReturnedObject());
            ((QuestionsController) guiControllers.get(QuestionsController.class.getSimpleName())).
                    receivedRespondFromServer(serverResponse);
        }
        if (serverResponse.getRespondName().equals(QuestionReadAllCommand.class.getSimpleName())) {
            // Getting questions asked for
            System.out.println("All question received: " + serverResponse.getReturnedObject());
            ((QuestionsController) guiControllers.get(QuestionsController.class.getSimpleName())).
                    receivedRespondFromServer(serverResponse);
        }

        if (serverResponse.getRespondName().equals(TestReadBySubjectCommand.class.getSimpleName())) {
            // Getting questions asked for
            System.out.println("All testFacade received: " + serverResponse.getReturnedObject());
            ((TestsController) guiControllers.get(TestsController.class.getSimpleName())).
                    receivedRespondFromServer(serverResponse);
        }
        if (serverResponse.getRespondName().equals(TestReadByIdCommand.class.getSimpleName())) {
            // Getting questions asked for
            System.out.println("Test received: " + serverResponse.getReturnedObject());
            ((TestsController) guiControllers.get(TestsController.class.getSimpleName())).
                    receivedRespondFromServer(serverResponse);
        }
        if (serverResponse.getRespondName().equals(QuestionUpdateCommand.class.getSimpleName()))
            ((EditQuestionController) guiControllers.get(EditQuestionController.class.getSimpleName()))
                    .receivedResponseFromServer(serverResponse);
        if (serverResponse.getRespondName().equals(QuestionPushCommand.class.getSimpleName()))
            ((CreateQuestionController) guiControllers.get(CreateQuestionController.class.getSimpleName()))
                    .receivedResponseFromServer(serverResponse);
        if (serverResponse.getRespondName().equals(TestReadByIdCommand.class.getSimpleName()))
            ((MakeExecuteTestController) guiControllers.get(MakeExecuteTestController.class.getSimpleName()))
                    .receivedResponseFromServer(serverResponse);
        if (serverResponse.getRespondName().equals(CreateReadyTestCommand.class.getSimpleName()))
            ((MakeExecuteTestController) guiControllers.get(MakeExecuteTestController.class.getSimpleName()))
                    .receivedResponseFromServer(serverResponse);

    }

    public void closeConnection() {
        System.out.println("Connection closed.");
        System.exit(0);
    }

    public static void main(String[] args) throws IOException {
        MainClass.main(args);
    }
}
