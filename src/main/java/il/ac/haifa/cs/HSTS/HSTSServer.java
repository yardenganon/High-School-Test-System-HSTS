package il.ac.haifa.cs.HSTS;

import java.io.IOException;
import java.util.Date;

import il.ac.haifa.cs.HSTS.ocsf.server.AbstractServer;
import il.ac.haifa.cs.HSTS.ocsf.server.ConnectionToClient;
import il.ac.haifa.cs.HSTS.ocsf.server.Controllers.QuestionsController;
import il.ac.haifa.cs.HSTS.ocsf.server.Repositories.QuestionsRepository;

public class HSTSServer extends AbstractServer {
    QuestionsController questionsController;

    public HSTSServer(int port) {
        super(port);
        questionsController = new QuestionsController(new QuestionsRepository());
    }

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        /* Here we have to write controller
         * Getting user details from login window, send back The object or msg
         * by "client.sendToClient("msg object");
         * Etc.....		*/
        if (msg != null) {
            Command commandFromClient = (Command) msg;
            commandFromClient.printCommandDetails();
            switch (commandFromClient.getController()) {
                case "questions": commandFromClient = questionsController.QuestionHandler(commandFromClient); break;
                // other cases

                default:
                    commandFromClient.setStatus("Controller not found");
                    commandFromClient.setDateHandled(new Date());
            }
            try {
                // after handle command, send back the command to Client (update status, etc...)
                client.sendToClient(commandFromClient);
            } catch (IOException e) {
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
