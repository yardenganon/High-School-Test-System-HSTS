package il.ac.haifa.cs.HSTS;
import il.ac.haifa.cs.HSTS.ocsf.client.FXML.*;
import il.ac.haifa.cs.HSTS.ocsf.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.ocsf.server.Services.Respond;

import java.io.IOException;

// That was CLIChatClient
public class HSTSClientInterface {

    private static HSTSClient client;
//    private CLIInterface cliInterface;
//    private GUIInterface guiInterface;
    private MainClass mainClass;

    private boolean isRunning;
    //private static final String SHELL_STRING = "Enter command (or exit to help)> ";
    private Thread loopThread;

    public HSTSClientInterface(HSTSClient client) {
        HSTSClientInterface.client = client;
        this.isRunning = false;
//        this.cliInterface = new CLIInterface(this);
//        this.guiInterface = new GUIInterface(this);
    }

    public void loop() throws IOException {
        loopThread = new Thread(new Runnable() {

            @Override
            public void run() {
                /* Here we have to write Client logic (in while->try scope)
                 * Open login window interface, asking the user to write details.
                 * Then, send request to the server for obtaining User according to the details
                 * Etc.....		*/
//                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//                String message;
//                Command command;
//                while (client.isConnected()) {
                    //System.out.print(SHELL_STRING);
                    // Parser and CLI logic moved into new CLIInterface class
                    //cliInterface.CLIInterfaceLoop();


                    //guiInterface.guiInterfaceSendCommandToClient(null);
//            }
        }
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
        Respond serverResponse = (Respond) message;
        System.out.println("Command received from server : " + serverResponse.getRespondName());
        System.out.println("Command returned object : "+ (serverResponse.getReturnedObject()!=null?serverResponse.getReturnedObject():"null"));
        if (serverResponse.getRespondName().equals("Login")){
            LoginController.receivedRespondFromServer(serverResponse);
        }
        if (serverResponse.getRespondName().equals("ReadBySubject")) {
            // Getting questions asked for
            System.out.println("subjects with question received: "+ serverResponse.getReturnedObject());
            Menu.receivedRespondFromServer(serverResponse);
        }
        if (serverResponse.getRespondName().equals("ReadAllQuestions")) {
            // Getting questions asked for
            System.out.println("All question received: "+ serverResponse.getReturnedObject());
            Menu.receivedRespondFromServer(serverResponse);
        }
        if (serverResponse.getRespondName().equals("UpdateQuestion"))
            EditInterface.receivedResponseFromServer(serverResponse);
    }

    public void closeConnection() {
        System.out.println("Connection closed.");
        System.exit(0);
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Required arguments: <host> <port>");
        } else {
            String host = args[0];
            int port = Integer.parseInt(args[1]);

            HSTSClient chatClient = new HSTSClient(host, port);
            chatClient.openConnection();
            MainClass.main(args);
            chatClient.closeConnection();
        }
    }
}
