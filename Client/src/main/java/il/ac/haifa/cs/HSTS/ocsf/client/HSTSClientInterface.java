package il.ac.haifa.cs.HSTS.ocsf.client;
import il.ac.haifa.cs.HSTS.ocsf.client.FXML.*;
import il.ac.haifa.cs.HSTS.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.server.CommandInterface.Response;


import java.io.IOException;

// That was CLIChatClient
public class HSTSClientInterface {

    private static HSTSClient client;
    private MainClass mainClass;

    private boolean isRunning;
    private Thread loopThread;

    public HSTSClientInterface(HSTSClient client) {
        HSTSClientInterface.client = client;
        this.isRunning = false;
    }

    public void loop() throws IOException {
        loopThread = new Thread(new Runnable() {
            @Override
            public void run() {}
    });

        loopThread.start();
        this.isRunning =true;

}
    public static void sendCommandToServer(CommandInterface command)
    {
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
        System.out.println("Command returned object : "+ (serverResponse.getReturnedObject()!=null?serverResponse.getReturnedObject():"null"));
        if (serverResponse.getRespondName().equals("Login")){
            LoginController.receivedRespondFromServer(serverResponse);
        }
        if (serverResponse.getRespondName().equals("ReadBySubject")) {
            // Getting questions asked for
            System.out.println("subjects with question received: "+ serverResponse.getReturnedObject());
            QuestionsController.receivedRespondFromServer(serverResponse);
        }
        if (serverResponse.getRespondName().equals("ReadAllQuestions")) {
            // Getting questions asked for
            System.out.println("All question received: "+ serverResponse.getReturnedObject());
            QuestionsController.receivedRespondFromServer(serverResponse);
        }
        if (serverResponse.getRespondName().equals("UpdateQuestion"))
            EditQuestionController.receivedResponseFromServer(serverResponse);
    }

    public void closeConnection() {
        System.out.println("Connection closed.");
        System.exit(0);
    }

    public static void main(String[] args) throws IOException {
        HSTSClient chatClient = new HSTSClient("localhost", 3000);
        chatClient.openConnection();
        MainClass.main(args);
        chatClient.closeConnection();
    }
}
