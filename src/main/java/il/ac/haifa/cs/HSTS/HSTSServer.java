package il.ac.haifa.cs.HSTS;

import java.io.IOException;
import java.util.Date;

import il.ac.haifa.cs.HSTS.ocsf.server.AbstractServer;
import il.ac.haifa.cs.HSTS.ocsf.server.ConnectionToClient;
import il.ac.haifa.cs.HSTS.ocsf.server.Controllers.QuestionsController;
import il.ac.haifa.cs.HSTS.ocsf.server.Controllers.UsersController;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.User;
import il.ac.haifa.cs.HSTS.ocsf.server.Repositories.QuestionsRepository;
import il.ac.haifa.cs.HSTS.ocsf.server.Repositories.UsersRepository;

public class HSTSServer extends AbstractServer {
    QuestionsController questionsController;
    UsersController usersController;
    public HSTSServer(int port) {
        super(port);
        questionsController = new QuestionsController(new QuestionsRepository());
        usersController = new UsersController(new UsersRepository());
        System.out.println("Server initiated");
    }

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        /* Here we have to write controller
         * Getting user details from login window, send back The object or msg
         * by "client.sendToClient("msg object");
         * Etc.....		*/
        if (msg != null) {
            Command commandFromClient = (Command) msg;
            System.out.print("Command received from client ");
            commandFromClient.printCommandDetails();

            switch (commandFromClient.getController().toLowerCase()) {
                case "questions":
                    commandFromClient = questionsController.QuestionHandler(commandFromClient); break;
                // other cases
                case "users" :
                    commandFromClient = usersController.UserHandler(commandFromClient);
                    break;

                default:
                    Command handled = commandFromClient;
                    handled.setStatus("Controller not found");
                    handled.setDateHandled(new Date());
                    System.out.println("Sending command back to client - " + handled.toString()+"\n");
                    try {
                        client.sendToClient(handled);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            try {
                // after handling command, send back the command to Client (update status, etc...)
                commandFromClient.setDateHandled(new Date());
                System.out.print("Sending command back to client - " /*+ commandFromClient*/+"\n");
                commandFromClient.printCommandDetails();
                client.sendToClient(commandFromClient);
                System.out.println(commandFromClient.getCommand());
                System.out.println("Sent!");
            } catch (IOException e) {
                System.out.println("Command has not sent back to client\n");
                e.printStackTrace();
            }
        }

//		sendToAllClients(msg);


    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Required argument: <port>");
        } else {
            HSTSServer server = new HSTSServer(Integer.parseInt(args[0]));
            server.listen();
        }
    }
}
