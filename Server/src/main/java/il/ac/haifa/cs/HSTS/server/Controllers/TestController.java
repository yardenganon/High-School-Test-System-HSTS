package il.ac.haifa.cs.HSTS.server.Controllers;

import il.ac.haifa.cs.HSTS.server.CommandInterface.*;
import il.ac.haifa.cs.HSTS.server.Entities.Teacher;
import il.ac.haifa.cs.HSTS.server.Repositories.TestsRepository;
import il.ac.haifa.cs.HSTS.server.Status.Status;

import java.util.Date;

public class TestController implements ControllerInterface {

    final private TestsRepository testsRepository;

    public TestController() {
        this.testsRepository = new TestsRepository();
    }

    @Override
    public Response executeCommand(CommandInterface command) {
        String commandName = command.getCommandName();
        Response responseMessage = new Response(commandName);
        Object returnedObject = null;
        switch(commandName) {
            case("CreateReadyTestCommand"):
                returnedObject = testsRepository.pushReadyTest(((CreateReadyTestCommand) command).getReadyTest());
                break;
            case("ReadyTestFacadeReadByTeacherCommand"):
                returnedObject = testsRepository.getReadyTestsFacadeByTeacherId(
                        ((ReadyTestFacadeReadByTeacherCommand)command).getTeacherId());
                break;
            case("TestReadAllCommand") :
                returnedObject = testsRepository.getAll();
                break;
            case("TestReadByIdCommand") :
                returnedObject = testsRepository.getTestById(
                        ((TestReadByIdCommand)command).getId());
                break;
            case("TestsFacadeReadBySubjectCommand") :
                returnedObject = testsRepository.getTestsFacadeBySubject(
                        ((TestsFacadeReadBySubjectCommand) command).getSubjectName());
                break;
            case("TestPushCommand") :
                returnedObject = testsRepository.pushTest(
                        ((TestPushCommand)command).getNewTest());
                break;
            case("AnswerableTestReadCommand"):
                returnedObject = testsRepository.getAnswerableTestById(((AnswerableTestReadCommand) command).getAnswerableTestId());
            case("PushAnswerableTestCommand"):
                returnedObject = testsRepository.pushAnswerableTest(((PushAnswerableTestCommand)command).getAnswerableTest());
                break;
            case("AnswerableTestUpdateCommand"):
                returnedObject = testsRepository.updateAnswerableTest(((AnswerableTestUpdateCommand) command).getAnswerableTest());
            case("AnswerableTestUpdateByIdCommand"):
                int id = ((AnswerableTestUpdateByIdCommand) command).getAnswerableTestId();
                Teacher teacher = ((AnswerableTestUpdateByIdCommand) command).getTeacher();
                returnedObject = testsRepository.updateAnswerableTestById(id, teacher);
                break;
            case("getAnswerableTestsFacadeCommand"):
                returnedObject = testsRepository.getAnswerableTestsFacade(((getAnswerableTestsFacadeCommand) command).getTeacher());
                break;
            default:
                System.out.println("Error - Command not found in controller");
        }
        responseMessage.setReturnedObject(returnedObject);
        responseMessage.setDateHandled(new Date());
        responseMessage.setStatus(Status.Success);
        System.out.print("Command handled successfully ");
        return responseMessage;
    }
}
