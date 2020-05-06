package il.ac.haifa.cs.HSTS.ocsf.client.CLI;

import il.ac.haifa.cs.HSTS.Command;
import il.ac.haifa.cs.HSTS.HSTSClientInterface;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Question;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Subject;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Teacher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class CLIInterface {
    HSTSClientInterface hstsClientInterface;

    public CLIInterface(HSTSClientInterface hstsClientInterface) {
        this.hstsClientInterface = hstsClientInterface;
    }

    public void helpCLI() {
        System.out.println("Enter [push] [questions] [*Question Object*] [Ans1] [Ans2] [Ans3] [Ans4] [Correct answer] [Writer]");
        System.out.println("Enter [readBySubject] [questions] ['List<Subject>']"); // You'll get list of subjects with question inside every subject (subject.getQuestions())
        System.out.println("Enter [readById] [questions] [id]");
        System.out.println("Enter [update] [questions] [*Question Object*]");
        System.out.println("Enter [delete] [questions] [*Question Object*]");
        System.out.println("Enter [login] [users] ['username'] ['password']");
    }

    public void CLIInterfaceLoop() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            // Parsing message -> command
            String message = null;
            try {
                message = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Command command = null;
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
                                , tokens[3], tokens[4], tokens[5], tokens[6], Integer.parseInt(tokens[7]), new Teacher(), new Subject("Math")));
                        hstsClientInterface.sendCommandToServer(command);
                    } else
                        System.out.println("Invalid CLI command");


                } else if (tokens[0].toLowerCase().equals("login") && tokens[1].toLowerCase().equals("users")){
                    command = new Command("login","users",tokens[2],tokens[3]);
                    hstsClientInterface.sendCommandToServer(command);
                } else if (tokens[0].toLowerCase().equals(("readbysubject")) && tokens[1].toLowerCase().equals("questions")) {
                    List<Subject> subjectList = new ArrayList<Subject>();
                    Subject subject = new Subject("Mathematics");
                    Subject subject2 = new Subject("Science");
                    subjectList.add(subject);
                    subjectList.add(subject2);
                    command = new Command(tokens[0], tokens[1], subjectList);
                    hstsClientInterface.sendCommandToServer(command);
                } else if (tokens[0].toLowerCase().equals("readbyid") && tokens[1].toLowerCase().equals("questions")) {
                    command = new Command(tokens[0], tokens[1], tokens[2]);
                    hstsClientInterface.sendCommandToServer(command);
                } else if (tokens[0].toLowerCase().equals("update") && tokens[1].toLowerCase().equals("questions")) {
                    command = new Command(tokens[0], tokens[1], tokens[2]);
                    hstsClientInterface.sendCommandToServer(command);
                } else if (tokens[0].toLowerCase().equals("delete") && tokens[1].toLowerCase().equals("questions")) {
                    command = new Command(tokens[0], tokens[1], tokens[2]);
                    hstsClientInterface.sendCommandToServer(command);
                }

            } else if (message.equalsIgnoreCase("help"))
                helpCLI();
            else if (message.equalsIgnoreCase("exit")) {
                System.out.println("Closing connection.");
                try {
                    hstsClientInterface.getClient().closeConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
