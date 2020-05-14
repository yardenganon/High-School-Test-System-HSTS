package il.ac.haifa.cs.HSTS.ocsf.server.Controllers;

import il.ac.haifa.cs.HSTS.ocsf.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.ocsf.server.Services.Response;

public class QuestionReadByIdController implements ControllerInterface{
    @Override
    public Response executeCommand(CommandInterface command) {
        return null;
    }

    @Override
    public String getControllerName() {
        return "QuestionReadByIdController";
    }
}
