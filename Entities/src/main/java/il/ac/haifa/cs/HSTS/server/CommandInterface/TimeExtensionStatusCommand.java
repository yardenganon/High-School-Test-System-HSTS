package il.ac.haifa.cs.HSTS.server.CommandInterface;

public class TimeExtensionStatusCommand extends CommandInterface{
    int readTestId;

    public TimeExtensionStatusCommand(int readTestId) {
        this.readTestId = readTestId;
    }

    public int getReadTestId() {
        return readTestId;
    }
}
