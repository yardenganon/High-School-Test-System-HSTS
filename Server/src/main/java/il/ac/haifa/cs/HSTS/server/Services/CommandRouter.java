package il.ac.haifa.cs.HSTS.server.Services;

import il.ac.haifa.cs.HSTS.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.server.CommandInterface.Response;
import il.ac.haifa.cs.HSTS.server.Controllers.ControllerInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//command router
public class CommandRouter {

    private final Map<String, ControllerInterface> commandsMap;

    public CommandRouter(){
        commandsMap = new HashMap<>();
    }

    public void initRouter(List<ControllerInterface> controllerInterfaces){
        commandsMap.put("LoginCommand", controllerInterfaces.get(0));
        commandsMap.put("QuestionReadBySubjectCommand", controllerInterfaces.get(1));
        commandsMap.put("QuestionUpdateCommand", controllerInterfaces.get(1));
        commandsMap.put("QuestionReadAllCommand", controllerInterfaces.get(1));

        System.out.println("Router initiated");
    }

    public Response handleRequest(CommandInterface commandFromClient){
        System.out.println("In handleRequest");
        Response responseMessage;
        String commandName = commandFromClient.getCommandName();
        System.out.println(commandName);
        ControllerInterface controllerInterface = commandsMap.get(commandName);
        responseMessage = controllerInterface.executeCommand(commandFromClient);
        return responseMessage;
    }
}
