package il.ac.haifa.cs.HSTS.server.CommandInterface;

import il.ac.haifa.cs.HSTS.server.Entities.TimeExtensionRequest;

public class RequestTimeExtensionCommand extends CommandInterface{
    TimeExtensionRequest timeExtensionRequest;

    public RequestTimeExtensionCommand(TimeExtensionRequest timeExtensionRequest) {
        this.timeExtensionRequest = timeExtensionRequest;
    }

    public TimeExtensionRequest getTimeExtensionRequest() {
        return timeExtensionRequest;
    }
}
