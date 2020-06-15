package il.ac.haifa.cs.HSTS;

import il.ac.haifa.cs.HSTS.server.AbstractServer;
import il.ac.haifa.cs.HSTS.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.server.CommandInterface.RequestTimeExtensionCommand;
import il.ac.haifa.cs.HSTS.server.CommandInterface.Response;
import il.ac.haifa.cs.HSTS.server.ConnectionToClient;
import il.ac.haifa.cs.HSTS.server.Controllers.*;
import il.ac.haifa.cs.HSTS.server.Services.Bundle;
import il.ac.haifa.cs.HSTS.server.Services.CommandRouter;
import il.ac.haifa.cs.HSTS.server.Status.Status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HSTSServer extends AbstractServer {

    List<ControllerInterface> controllers;
    CommandRouter commandRouter;
    Bundle bundle;

    public HSTSServer(int port) {
        super(port);
        System.out.println("Server initiated");
        bundle = Bundle.getInstance();
        bundle.put("server",this);
        controllers = new ArrayList<>();
        controllersInit();
        commandRouter = new CommandRouter();
        commandRouter.initRouter(this.controllers);


    }
    @Override
    protected void handleMessageFromClient(Object message, ConnectionToClient client) {
        if (message != null) {
            Response responseMessage;
            CommandInterface commandFromClient = null;
            try {
                commandFromClient = (CommandInterface) message;
                System.out.print("Command received from client ");

                responseMessage = commandRouter.handleRequest(commandFromClient);
                System.out.println("got back from DB");
                System.out.println("aaa: " +responseMessage.getReturnedObject());
                System.out.println(responseMessage.getReturnedObjectName());
            } catch (Exception e) {
                responseMessage = new Response(Status.ServerException);
                e.printStackTrace();
            }
            try {
                //sending back respond to the client
                System.out.println("Sending response back to client");
                client.sendToClient(responseMessage);
                System.out.println("Sent!");
            } catch (IOException e) {
                System.out.println("Command has not sent back to client");
                e.printStackTrace();
            }
        }
    }
    public void controllersInit(){
        controllers.add(new UserController());
        controllers.add(new QuestionController());
        controllers.add(new TestController());
        controllers.add(new RequestAnswerableTestController());
        controllers.add(new TimeExtensionController());
        controllers.add(new CourseController());
    }

    public static void main(String[] args) throws IOException {
        HSTSServer server = new HSTSServer(3000);
        server.listen();
//        if (args.length != 1) {
//            System.out.println("Required argument: <port>");
//        } else {
//
//        }
    }
}

