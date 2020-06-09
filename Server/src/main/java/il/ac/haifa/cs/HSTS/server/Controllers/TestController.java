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
            case("ReadyTestUpdateActivityCommand"):
                int readyTestId = ((ReadyTestUpdateActivityCommand) command).getReadyTestId();
                Boolean status = ((ReadyTestUpdateActivityCommand) command).getActive();
                returnedObject = testsRepository.updateReadyTestActivity(readyTestId, status);
                break;
            case("ReadyTestExtendedFacadeReadByTeacherCommand"):
                returnedObject = testsRepository.getReadyTestsFacadeByTeacherId(
                        ((ReadyTestExtendedFacadeReadByTeacherCommand)command).getTeacherId());
                break;
            case("ReadyTestReadByIdCommand"):
                returnedObject = testsRepository.getReadyTestById(((ReadyTestReadByIdCommand) command).getReadyTestId());
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
                break;
            case("PushAnswerableTestCommand"):
                returnedObject = testsRepository.pushAnswerableTest(((PushAnswerableTestCommand)command).getAnswerableTest());
                break;
            case("AnswerableTestUpdateCommand"):
                returnedObject = testsRepository.updateAnswerableTest(((AnswerableTestUpdateCommand) command).getAnswerableTest());
                break;
            case("AnswerableTestUpdateByIdCommand"):
                int id = ((AnswerableTestUpdateByIdCommand) command).getAnswerableTestId();
                Teacher teacher = ((AnswerableTestUpdateByIdCommand) command).getTeacher();
                returnedObject = testsRepository.updateAnswerableTestById(id, teacher);
                break;
            case("AnswerableTestsFacadeReadCommand"):
                returnedObject = testsRepository.getAnswerableTestsFacade(((AnswerableTestsFacadeReadCommand) command).getTeacher());
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
