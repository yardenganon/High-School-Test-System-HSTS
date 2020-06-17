package il.ac.haifa.cs.HSTS.server.Controllers;

import il.ac.haifa.cs.HSTS.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.server.CommandInterface.Response;
import il.ac.haifa.cs.HSTS.server.Repositories.CourseRepository;
import il.ac.haifa.cs.HSTS.server.Repositories.GenericQueries;
import il.ac.haifa.cs.HSTS.server.Status.Status;

import java.util.Date;

public class CourseController implements ControllerInterface{
    final private CourseRepository courseRepository;

    public CourseController() {
        this.courseRepository = new CourseRepository();
    }

    @Override
    public Response executeCommand(CommandInterface command) {
        String commandName = command.getCommandName();
        Response serverMessage = new Response(commandName);
        Object returnedObject = null;
        switch(commandName){
            case("CourseReadAllFacadeCommand"):
                returnedObject = courseRepository.readAllCoursesFacade();
                break;
            case("SubjectReadAllCommand"):
                returnedObject = courseRepository.getAllSubjects();
                break;
            default:
                System.out.println("Error - Command not found in controller");
        }
        serverMessage.setReturnedObject(returnedObject);
        serverMessage.setDateHandled(new Date());
        serverMessage.setStatus(Status.Success);
        System.out.print("Command handled successfully ");
        return serverMessage;
    }
}