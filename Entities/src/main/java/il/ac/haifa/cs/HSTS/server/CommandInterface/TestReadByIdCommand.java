package il.ac.haifa.cs.HSTS.server.CommandInterface;

public class TestReadByIdCommand extends CommandInterface {
    int id;

    public TestReadByIdCommand(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
