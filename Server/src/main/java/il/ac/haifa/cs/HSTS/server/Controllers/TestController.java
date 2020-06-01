package il.ac.haifa.cs.HSTS.server.Controllers;

import il.ac.haifa.cs.HSTS.server.CommandInterface.*;
import il.ac.haifa.cs.HSTS.server.Repositories.TestsRepository;
import il.ac.haifa.cs.HSTS.server.Status.Status;

import java.util.Date;

public class TestController implements ControllerInterface {

    final private TestsRepository testsRepository;
    final private AnswerableTestFactory answerableTestFactory;

    public TestController() {
        this.testsRepository = new TestsRepository();
        this.answerableTestFactory = new AnswerableTestFactory();
    }

    @Override
    public Response executeCommand(CommandInterface command) {
        String commandName = command.getCommandName();
        Response responseMessage = new Response(commandName);
        Object returnedObject = null;
        switch (commandName) {
            case ("TestReadAllCommand") :
                returnedObject = testsRepository.getAll(); break;
            case ("TestReadByIdCommand") :
                returnedObject = testsRepository.getTestById(
                        ((TestReadByIdCommand)command).getId()); break;
            case ("TestReadBySubjectCommand") :
                returnedObject = testsRepository.getTestsBySubject(
                        ((TestReadBySubjectCommand) command).getSubject()); break;
            case ("TestUpdateCommand") :
                returnedObject = testsRepository.updateTest(
                        ((TestUpdateCommand)command).getTestToUpdate()); break;
            default:
                System.out.println("Error - Command not found in controller");
        }
        responseMessage.setReturnedObject(returnedObject);
        responseMessage.setDateHandled(new Date());
        responseMessage.setStatus(Status.Success);
        System.out.print("Command handled successfully ");
        return responseMessage;
    }

    @Override
    public String getControllerName() {
        return "TestController";
    }
}
