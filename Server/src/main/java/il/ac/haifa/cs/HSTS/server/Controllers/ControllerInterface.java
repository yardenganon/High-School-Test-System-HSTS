package il.ac.haifa.cs.HSTS.server.Controllers;

import il.ac.haifa.cs.HSTS.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.server.CommandInterface.Response;

public interface ControllerInterface {
    Response executeCommand(CommandInterface command);
    String getControllerName();
}
