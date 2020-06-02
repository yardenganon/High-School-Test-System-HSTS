package il.ac.haifa.cs.HSTS.server.CommandInterface;

import il.ac.haifa.cs.HSTS.server.Entities.Test;

public class TestPushCommand extends CommandInterface{

    private Test newTest;

    public TestPushCommand(Test test){
        this.newTest = test;
    }

    public Test getNewTest() {
        return newTest;
    }
}
