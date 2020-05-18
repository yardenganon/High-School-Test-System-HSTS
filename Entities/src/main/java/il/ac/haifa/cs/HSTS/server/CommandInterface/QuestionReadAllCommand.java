package il.ac.haifa.cs.HSTS.server.CommandInterface;

public class QuestionReadAllCommand extends CommandInterface{

    public QuestionReadAllCommand(){}

    @Override
    public String getCommandName() {
        return this.getClass().getSimpleName();
    }
}
