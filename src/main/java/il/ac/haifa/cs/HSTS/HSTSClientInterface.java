package il.ac.haifa.cs.HSTS;

import il.ac.haifa.cs.HSTS.ocsf.client.CLI.CLIInterface;
import il.ac.haifa.cs.HSTS.ocsf.client.FXML.GUIInterface;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

// That was CLIChatClient
public class HSTSClientInterface {

    private HSTSClient client;
    private CLIInterface cliInterface;
    private GUIInterface guiInterface;
    private boolean isRunning;
    private static final String SHELL_STRING = "Enter command (or exit to help)> ";
    private Thread loopThread;

    public HSTSClientInterface(HSTSClient client) {
        this.client = client;
        this.isRunning = false;
        this.cliInterface = new CLIInterface(this);
        this.guiInterface = new GUIInterface(this);
    }

    public void loop() throws IOException {
        loopThread = new Thread(new Runnable() {

            @Override
            public void run() {
                /* Here we have to write Client logic (in while->try scope)
                 * Open login window interface, asking the user to write details.
                 * Then, send request to the server for obtaining User according to the details
                 * Etc.....		*/

                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String message;
                Command command;
                while (client.isConnected()) {
                    System.out.print(SHELL_STRING);
                    // Parser and CLI logic moved into new CLIInterface class
                    cliInterface.CLIInterfaceLoop();
                    guiInterface.guiInterfaceLoop();
            }
        }
    });

        loopThread.start();
        this.isRunning =true;

}
    public void sendCommandToClient(Command command)
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
            System.out.print("(Interrupted)\n");
        }
        Command commandHandled = (Command) message;
        System.out.println("Command received from server : " + commandHandled.toString());


        if (commandHandled.isCommand("questions", "readBySubject")) {
            // Getting questions asked for
            ArrayList<Question> list = (ArrayList<Question>) ((Command) message).getReturnedObject();
            for (Question q : list)
                System.out.println(q.toString());
        }
        if (isRunning)
            System.out.print(SHELL_STRING);
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
        }
    }
}
