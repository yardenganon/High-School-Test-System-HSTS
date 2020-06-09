package il.ac.haifa.cs.HSTS.server.CommandInterface;

import il.ac.haifa.cs.HSTS.server.Entities.TimeExtensionRequest;

public class TimeExtensionRequestUpdateCommand extends CommandInterface{
    TimeExtensionRequest updatedTimeExtensionRequest;

    public TimeExtensionRequestUpdateCommand(TimeExtensionRequest updatedTimeExtensionRequest) {
        this.updatedTimeExtensionRequest = updatedTimeExtensionRequest;
    }

    public TimeExtensionRequest getUpdatedTimeExtensionRequest() {
        return updatedTimeExtensionRequest;
    }
}
