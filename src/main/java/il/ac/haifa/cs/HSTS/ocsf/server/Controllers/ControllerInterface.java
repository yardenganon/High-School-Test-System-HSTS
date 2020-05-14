package il.ac.haifa.cs.HSTS.ocsf.server.Controllers;

import il.ac.haifa.cs.HSTS.ocsf.Respond.Respond;
import il.ac.haifa.cs.HSTS.ocsf.commandInterface.CommandInterface;

public interface ControllerInterface {
    Respond executeCommand(CommandInterface command);
    String getControllerName();
}
