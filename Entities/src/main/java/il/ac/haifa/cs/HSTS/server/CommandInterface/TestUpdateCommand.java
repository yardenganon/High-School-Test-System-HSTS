package il.ac.haifa.cs.HSTS.server.CommandInterface;

import il.ac.haifa.cs.HSTS.server.Entities.Question;
import il.ac.haifa.cs.HSTS.server.Entities.Test;

public class TestUpdateCommand extends CommandInterface{

    private Test testToUpdate;

    public TestUpdateCommand(Test test){
        this.testToUpdate = test;
    }

    public Test getTestToUpdate() {
        return testToUpdate;
    }

    @Override
    public String getCommandName() {
        return this.getClass().getSimpleName();
    }

}
