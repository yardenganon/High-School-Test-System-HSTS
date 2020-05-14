package il.ac.haifa.cs.HSTS.ocsf.commandInterface;

public class QuestionReadAllCommand extends CommandInterface{

    public QuestionReadAllCommand(){}

    @Override
    public String getCommandName() {
        return this.getClass().getSimpleName();
    }
}
