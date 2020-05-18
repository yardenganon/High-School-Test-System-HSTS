package il.ac.haifa.cs.HSTS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import il.ac.haifa.cs.HSTS.ocsf.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.ocsf.server.Controllers.*;
import il.ac.haifa.cs.HSTS.ocsf.server.AbstractServer;
import il.ac.haifa.cs.HSTS.ocsf.server.ConnectionToClient;
import il.ac.haifa.cs.HSTS.ocsf.server.Services.CommandRouter;
import il.ac.haifa.cs.HSTS.ocsf.server.Services.Response;

public class HSTSServer extends AbstractServer {

    List<ControllerInterface> controllers;
    CommandRouter commandRouter;

    public HSTSServer(int port) {
        super(port);
        System.out.println("Server initiated");
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
                System.out.println(responseMessage.getReturnedObjectName());
            } catch (Exception e) {
                responseMessage = new Response();
                responseMessage.setDateHandled(new Date());
                responseMessage.setStatus("Controller not found");
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

