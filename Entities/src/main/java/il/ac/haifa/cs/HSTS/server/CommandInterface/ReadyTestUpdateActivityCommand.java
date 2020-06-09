package il.ac.haifa.cs.HSTS.server.CommandInterface;

public class ReadyTestUpdateActivityCommand extends CommandInterface{
    int readyTestId;
    Boolean isActive;

    public ReadyTestUpdateActivityCommand(int readyTestId, Boolean isActive) {
        this.readyTestId = readyTestId;
        this.isActive = isActive;
    }

    public int getReadyTestId() {
        return readyTestId;
    }

    public Boolean getActive() {
        return isActive;
    }
}
