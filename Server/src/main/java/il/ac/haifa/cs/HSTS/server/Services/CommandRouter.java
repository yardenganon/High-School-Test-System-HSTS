package il.ac.haifa.cs.HSTS.server.Services;

import il.ac.haifa.cs.HSTS.server.CommandInterface.*;
import il.ac.haifa.cs.HSTS.server.Controllers.ControllerInterface;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        commandsMap.put(QuestionPushCommand.class.getSimpleName(), controllers.get(1));

        commandsMap.put(TestReadAllCommand.class.getSimpleName(), controllers.get(2));
        commandsMap.put(TestReadByIdCommand.class.getSimpleName(), controllers.get(2));
        commandsMap.put(TestsFacadeReadBySubjectCommand.class.getSimpleName(), controllers.get(2));
        commandsMap.put(TestPushCommand.class.getSimpleName(), controllers.get(2));
        commandsMap.put(ReadyTestExtendedFacadeReadByTeacherCommand.class.getSimpleName(), controllers.get(2));
        commandsMap.put(CreateReadyTestCommand.class.getSimpleName(), controllers.get(2));
        commandsMap.put(ReadyTestReadByIdCommand.class.getSimpleName(), controllers.get(2));
        commandsMap.put(ReadyTestUpdateActivityCommand.class.getSimpleName(), controllers.get(2));
        commandsMap.put(PushAnswerableTestCommand.class.getSimpleName(), controllers.get(2));
        commandsMap.put(AnswerableTestReadCommand.class.getSimpleName(), controllers.get(2));
        commandsMap.put(AnswerableTestsFacadeReadByStudentCommand.class.getSimpleName(), controllers.get(2));
        commandsMap.put(AnswerableTestUpdateByIdCommand.class.getSimpleName(), controllers.get(2));
        commandsMap.put(AnswerableTestUpdateCommand.class.getSimpleName(), controllers.get(2));
        commandsMap.put(AnswerableTestsFacadeReadCommand.class.getSimpleName(), controllers.get(2));
        commandsMap.put(AnswerableTestsFacadeReadByCourseCommand.class.getSimpleName(), controllers.get(2));
        commandsMap.put(AnswerableTestsFacadeReadByCourseAndStudentCommand.class.getSimpleName(), controllers.get(2));

        commandsMap.put(RequestAnswerableTestCommand.class.getSimpleName(), controllers.get(3));

        commandsMap.put(RequestTimeExtensionCommand.class.getSimpleName(), controllers.get(4));
        commandsMap.put(TimeExtensionReadAllCommand.class.getSimpleName(), controllers.get(4));
        commandsMap.put(TimeExtensionRequestUpdateCommand.class.getSimpleName(), controllers.get(4));
        commandsMap.put(TimeExtensionStatusCommand.class.getSimpleName(), controllers.get(4));

        commandsMap.put(CourseReadAllFacadeCommand.class.getSimpleName(), controllers.get(5));

        System.out.println("Router initiated");
    }

    public Response handleRequest(CommandInterface commandFromClient){
        System.out.println("In handleRequest");
        Response responseMessage = null;
        String commandName = commandFromClient.getCommandName();
        System.out.println(commandName);
        try {
            ControllerInterface controllerInterface = commandsMap.get(commandName);
            responseMessage = controllerInterface.executeCommand(commandFromClient);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return responseMessage;
    }
}
