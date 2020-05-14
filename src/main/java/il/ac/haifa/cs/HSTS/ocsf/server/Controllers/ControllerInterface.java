package il.ac.haifa.cs.HSTS.ocsf.server.Controllers;

import il.ac.haifa.cs.HSTS.ocsf.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.ocsf.server.Services.Respond;

public interface ControllerInterface {
    Respond executeCommand(CommandInterface command);
    String getControllerName();
}
