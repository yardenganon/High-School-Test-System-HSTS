package il.ac.haifa.cs.HSTS.server.Controllers;

import il.ac.haifa.cs.HSTS.server.CommandInterface.*;
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
            case("TimeExtensionRequestUpdateCommand"):
                returnedObject = timeExtensionRepository.updateTimeExtensionRequest(
                        ((TimeExtensionRequestUpdateCommand) command).getUpdatedTimeExtensionRequest());
                break;
            case("TimeExtensionStatusCommand"):
                returnedObject = timeExtensionRepository.getTimeExtensionRequestByReadyTestId(
                        ((TimeExtensionStatusCommand) command).getReadTestId());
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