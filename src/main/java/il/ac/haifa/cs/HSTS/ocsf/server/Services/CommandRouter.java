package il.ac.haifa.cs.HSTS.ocsf.server.Services;

import il.ac.haifa.cs.HSTS.ocsf.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.ocsf.server.Controllers.ControllerInterface;

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
        System.out.println("In initRouter Function");
        commandsMap.put("LoginCommand", controllerInterfaces.get(0));
        commandsMap.put("QuestionReadBySubjectCommand", controllerInterfaces.get(1));
        commandsMap.put("QuestionUpdateCommand", controllerInterfaces.get(2));
        commandsMap.put("QuestionReadAllCommand", controllerInterfaces.get(3));
//        System.out.println(commandsMap.get("LoginCommand").getControllerName());
    }

    public Respond handleRequest(CommandInterface commandFromClient){
        System.out.println("In handleRequest");
        Respond respondMessage;
        String commandName = commandFromClient.getCommandName();
        System.out.println(commandName);
        ControllerInterface controllerInterface = commandsMap.get(commandName);
        respondMessage = controllerInterface.executeCommand(commandFromClient);
        return respondMessage;
    }
}
