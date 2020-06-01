package il.ac.haifa.cs.HSTS.server.CommandInterface;

import il.ac.haifa.cs.HSTS.server.Entities.Subject;

public class TestReadBySubjectCommand extends CommandInterface {
    Subject subject;

    public TestReadBySubjectCommand(Subject subject){
        this.subject = subject;
    }

    public Subject getSubject() {
        return subject;
    }
}
