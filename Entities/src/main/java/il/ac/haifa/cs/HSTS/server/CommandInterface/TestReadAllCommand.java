package il.ac.haifa.cs.HSTS.server.CommandInterface;

public class TestReadAllCommand extends CommandInterface{

    public TestReadAllCommand(){}

    @Override
    public String getCommandName() {
        return this.getClass().getSimpleName();
    }
}
