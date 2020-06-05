package il.ac.haifa.cs.HSTS.server.CommandInterface;

import il.ac.haifa.cs.HSTS.server.Entities.ReadyTest;

public class CreateReadyTestCommand extends CommandInterface{
    private ReadyTest readyTest;

    public CreateReadyTestCommand(ReadyTest readyTest){
        this.readyTest = readyTest;
    }

    public ReadyTest getReadyTest(){
        return this.readyTest;
    }
}
