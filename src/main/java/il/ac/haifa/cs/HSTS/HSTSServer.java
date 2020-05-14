package il.ac.haifa.cs.HSTS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import il.ac.haifa.cs.HSTS.ocsf.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.ocsf.server.Controllers.*;
import il.ac.haifa.cs.HSTS.ocsf.server.AbstractServer;
import il.ac.haifa.cs.HSTS.ocsf.server.ConnectionToClient;
import il.ac.haifa.cs.HSTS.ocsf.server.Repositories.QuestionsRepository;
import il.ac.haifa.cs.HSTS.ocsf.server.Repositories.UsersRepository;
import il.ac.haifa.cs.HSTS.ocsf.server.Services.CommandRouter;
import il.ac.haifa.cs.HSTS.ocsf.server.Services.Respond;

public class HSTSServer extends AbstractServer {

    //    UserLoginController usersController;
//    QuestionReadBySubjectController questionReadBySubjectController;
//    QuestionReadAllController questionReadAllController;
//    QuestionUpdateController questionUpdateController;
    List<ControllerInterface> controllers;

    public HSTSServer(int port) {
        super(port);
        controllers = new ArrayList<>();
        controllers.add(new UserLoginController());
        controllers.add(new QuestionReadBySubjectController());
        controllers.add(new QuestionUpdateController());
        controllers.add(new QuestionReadAllController());
//        System.out.println(controllers.get(0).getControllerName());
//        System.out.println(controllers.get(1).getControllerName());
//        System.out.println(controllers.get(2).getControllerName());
//        System.out.println(controllers.get(3).getControllerName());
        System.out.println("Server initiated");
    }
    @Override
    protected void handleMessageFromClient(Object message, ConnectionToClient client) {
        if (message != null) {
            Respond respondMessage;
            CommandInterface commandFromClient = null;
            try {
                commandFromClient = (CommandInterface) message;
                System.out.print("Command received from client ");
                CommandRouter commandRouter = new CommandRouter();
                commandRouter.initRouter(this.controllers);
//                System.out.println(controllers.get(0).getControllerName());
//                System.out.println(controllers.get(1).getControllerName());
//                System.out.println(controllers.get(2).getControllerName());
//                System.out.println(controllers.get(3).getControllerName());
                respondMessage = commandRouter.handleRequest(commandFromClient);
                System.out.println("got back from DB");
                System.out.println(respondMessage.getReturnedObjectName());
            } catch (Exception e) {
                respondMessage = new Respond();
                respondMessage.setDateHandled(new Date());
                respondMessage.setStatus("Controller not found");
                e.printStackTrace();
            }
            try {
                //sending back respond to the client
                System.out.println("Sending response back to client");
                client.sendToClient(respondMessage);
                System.out.println("Sent!");
            } catch (IOException e) {
                System.out.println("Command has not sent back to client");
                e.printStackTrace();
            }
        }
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

