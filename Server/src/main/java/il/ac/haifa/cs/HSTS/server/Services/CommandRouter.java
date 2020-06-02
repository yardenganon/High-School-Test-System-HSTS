package il.ac.haifa.cs.HSTS.server.Services;

import il.ac.haifa.cs.HSTS.server.CommandInterface.*;
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

    public void initRouter(List<ControllerInterface> controllers){
        commandsMap.put(LoginCommand.class.getSimpleName(), controllers.get(0));
        commandsMap.put(QuestionReadBySubjectCommand.class.getSimpleName(), controllers.get(1));
        commandsMap.put(QuestionUpdateCommand.class.getSimpleName(), controllers.get(1));
        commandsMap.put(QuestionReadAllCommand.class.getSimpleName(), controllers.get(1));
        commandsMap.put(TestReadAllCommand.class.getSimpleName(), controllers.get(2));
        commandsMap.put(TestReadByIdCommand.class.getSimpleName(), controllers.get(2));
        commandsMap.put(TestReadBySubjectCommand.class.getSimpleName(), controllers.get(2));
        commandsMap.put(TestPushCommand.class.getSimpleName(), controllers.get(2));
        commandsMap.put(TestReadByTeacherCommand.class.getSimpleName(), controllers.get(2));

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
