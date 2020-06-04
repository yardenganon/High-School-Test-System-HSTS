package il.ac.haifa.cs.HSTS.server.Controllers;

import il.ac.haifa.cs.HSTS.HSTSServer;
import il.ac.haifa.cs.HSTS.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.server.CommandInterface.RequestAnswerableTestCommand;
import il.ac.haifa.cs.HSTS.server.CommandInterface.Response;
import il.ac.haifa.cs.HSTS.server.Entities.AnswerableTest;
import il.ac.haifa.cs.HSTS.server.Entities.ReadyTest;
import il.ac.haifa.cs.HSTS.server.Entities.Student;
import il.ac.haifa.cs.HSTS.server.Facade.ReadyTestFacade;
import il.ac.haifa.cs.HSTS.server.Repositories.TestsRepository;
import il.ac.haifa.cs.HSTS.server.Services.Bundle;
import il.ac.haifa.cs.HSTS.server.Status.Status;

public class RequestAnswerableTestController implements ControllerInterface {
    private Bundle bundle;
    private HSTSServer server;
    private final TestsRepository testsRepository;

    public RequestAnswerableTestController() {
        this.testsRepository = new TestsRepository();
        bundle = Bundle.getInstance();
        server = (HSTSServer) bundle.get("server");
    }

    @Override
    public Response executeCommand(CommandInterface command) {
        Response serverMessage = new Response(command.getCommandName());
        String readyTestCode = ((RequestAnswerableTestCommand) command).getReadyTestCode();
        Student student = ((RequestAnswerableTestCommand) command).getStudent();
        ReadyTestFacade readyTestFacade = testsRepository.getReadyTestsByCode(readyTestCode);
        if(readyTestFacade == null){
            serverMessage.setReturnedObject(null);
            serverMessage.setStatus(Status.InvalidCode);
        }
        else{
            if(!readyTestFacade.getActive()){
                serverMessage.setReturnedObject(null);
                serverMessage.setStatus(Status.TestNotActive);
            }
            else{
                if(!student.isInCourse(readyTestFacade.getCourseName())){
                    serverMessage.setStatus(Status.PermissionDenied);
                    serverMessage.setReturnedObject(null);
                }
                else{
                    AnswerableTest answerableTest = testsRepository.getAnwerableTestByStudent(student);
                    if(answerableTest == null){
                        AnswerableTest newAnswerableTest = generateAnswerableTest(student, readyTestFacade);
                        testsRepository.pushAnserableTest(newAnswerableTest);
                        serverMessage.setReturnedObject(newAnswerableTest);
                        serverMessage.setStatus(Status.Success);
                    }
                    else{
                        if(answerableTest.getAnswerableTestStatus().equals(Status.TestFinished)){
                            serverMessage.setReturnedObject(null);
                            serverMessage.setStatus(Status.TestFinished);
                        }
                        else{
                            serverMessage.setReturnedObject(answerableTest);
                            serverMessage.setStatus(Status.Success);
                        }
                    }
                }
            }
        }
        return serverMessage;
    }

    public AnswerableTest generateAnswerableTest(Student student, ReadyTestFacade readyTestFacade){
        ReadyTest readyTest = testsRepository.getReadyTestById(readyTestFacade.getId());
        AnswerableTest newAnswerableTest = new AnswerableTest(readyTest, student);
        newAnswerableTest.setAnswerableTestStatus(Status.TestActive);
        return newAnswerableTest;
    }

    //   public Object makeAnswerableTestSession(String code, Student student) {
        /* 1. Have to check if ReadyTest with the given code is exists and is active (Status.InvalidCode \ Status.NotActive)
        *  2. Have to check if the student belongs to the course of that ReadyTest instance (Status.PermissionDenied)
        *  3. Have to check if there is an instance of AnswerableTest in DB belong to the student and if it's not finished (Status.OK)
        *  - True - return it
        *  - False - return new Instance */

        // 1.  ReadyTest readyTest = getReadyTestByCode(code);
        // 1.1 Boolean isActive = readyTest.isActive();
        // 1.2 Course course = getCourseFromReadyTest(readyTest);

        // 2.  Boolean permission = isStudentBelongsToCourse(student.getId(), course);

        // 3.  AnswerableTest answerableTest = getAnswerableTestByStudentAndReadyTest(readyTest, student);
        // 3.1 If (answerableTest == null) return pushAnswerableTest(readyTest, student);

   // }
}
