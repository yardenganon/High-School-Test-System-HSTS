package il.ac.haifa.cs.HSTS.server.Controllers;

import il.ac.haifa.cs.HSTS.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.server.CommandInterface.RequestTimeExtensionCommand;
import il.ac.haifa.cs.HSTS.server.CommandInterface.Response;
import il.ac.haifa.cs.HSTS.server.Repositories.TimeExtensionRepository;
import il.ac.haifa.cs.HSTS.server.Status.Status;

import java.util.Date;

public class TimeExtensionController implements ControllerInterface{
    final private TimeExtensionRepository timeExtensionRepository;

    public TimeExtensionController() {
        this.timeExtensionRepository = new TimeExtensionRepository();
    }

    @Override
    public Response executeCommand(CommandInterface command) {
        String commandName = command.getCommandName();
        Response serverMessage = new Response(commandName);
        Object returnedObject = null;
        switch(commandName){
            case("RequestTimeExtensionCommand"):
                returnedObject = timeExtensionRepository.pushTimeExtensionRequest(((RequestTimeExtensionCommand) command).getTimeExtensionRequest());
                break;
            case("TimeExtensionReadAllCommand"):
                returnedObject = timeExtensionRepository.getAllTimeExtensions();
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
