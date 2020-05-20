package il.ac.haifa.cs.HSTS.server.Controllers;

import il.ac.haifa.cs.HSTS.server.CommandInterface.*;
import il.ac.haifa.cs.HSTS.server.Repositories.TestsRepository;

import java.util.Date;

public class TestController implements ControllerInterface {

    final private TestsRepository testsRepository;

    public TestController() {
        this.testsRepository = new TestsRepository();
    }

    @Override
    public Response executeCommand(CommandInterface command) {
        String commandClass = command.getCommandName();
        Object returnedObject = null;
        switch (commandClass) {
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
        Response responseMessage = new Response();
        responseMessage.setReturnedObject(returnedObject);
        responseMessage.setDateHandled(new Date());
        responseMessage.setStatus("Success");
        responseMessage.setRespondName(commandClass);
        System.out.print("Command handled successfully ");
        return responseMessage;
    }

    @Override
    public String getControllerName() {
        return "TestController";
    }
}
