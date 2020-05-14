package il.ac.haifa.cs.HSTS.ocsf.commandInterface;

public class QuestionReadByIdCommand extends CommandInterface{

    int id;

    public QuestionReadByIdCommand(int id){
        this.id = id;
    }

    public String getCommandName(){
        return this.getClass().getSimpleName();
    }
}