package il.ac.haifa.cs.HSTS.ocsf.server.CommandInterface;

public class QuestionReadByIdCommand extends CommandInterface{

    int id;

    public QuestionReadByIdCommand(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getCommandName(){
        return this.getClass().getSimpleName();
    }
}