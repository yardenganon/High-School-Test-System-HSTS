package il.ac.haifa.cs.HSTS.ocsf.server.Controllers;

import il.ac.haifa.cs.HSTS.ocsf.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.ocsf.server.Services.Response;

public interface ControllerInterface {
    Response executeCommand(CommandInterface command);
    String getControllerName();
}
