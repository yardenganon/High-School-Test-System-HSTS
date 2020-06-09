package il.ac.haifa.cs.HSTS.server.CommandInterface;

public class ReadyTestReadByIdCommand extends CommandInterface{
    int readyTestId;

    public ReadyTestReadByIdCommand(int readyTestId) {
        this.readyTestId = readyTestId;
    }

    public int getReadyTestId() {
        return readyTestId;
    }
}
