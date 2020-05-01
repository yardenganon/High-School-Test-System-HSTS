package il.ac.haifa.cs.HSTS;

import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Question;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

// That was CLIChatClient
public class HSTSClientInterface {

    private HSTSClient client;
    private boolean isRunning;
    private static final String SHELL_STRING = "Enter command (or exit to help)> ";
    private Thread loopThread;

    public HSTSClientInterface(HSTSClient client) {
        this.client = client;
        this.isRunning = false;
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

                    try {
                        // Parsing message -> command
                        message = reader.readLine();
                        StringTokenizer stringTokenizer = new StringTokenizer(message);
                        String[] tokens = new String[stringTokenizer.countTokens()];
                        int i = 0;
                        while (stringTokenizer.hasMoreTokens()) {
                            tokens[i] = stringTokenizer.nextToken();
                            i++;
                        }
                        // Check command
                        if (i > 2) {
                            if (tokens[0].toLowerCase().equals("push") && tokens[1].toLowerCase().equals("questions")) {
                                if (i == 10) {
                                    command = new Command(tokens[0].toLowerCase(), tokens[1].toLowerCase(), new Question(tokens[2]
                                            , tokens[3], tokens[4], tokens[5], tokens[6], Integer.parseInt(tokens[7]), tokens[8], tokens[9]));
                                    sendCommandToClient(command);
                                }else
                                    System.out.println("Invalid CLI command");

                            }
                            else if (tokens[0].toLowerCase().equals(("readbysubject"))) {
                                command = new Command(tokens[0], tokens[1], tokens[2]);
                                sendCommandToClient(command);
                            }

                        }
                        else if (message.equalsIgnoreCase("help"))
                            helpCLI();
                        else if (message.equalsIgnoreCase("exit")) {
                            System.out.println("Closing connection.");
                            client.closeConnection();
                        }
                    }
                catch(IOException e1){
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

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
    public void helpCLI() {
        System.out.println("Enter [push] [questions] ['Question'] [Ans1] [Ans2] [Ans3] [Ans4] [Correct answer] [Writer]");
        System.out.println("Enter [readBySubject] [questions] ['Subject']");
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
