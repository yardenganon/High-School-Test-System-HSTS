package il.ac.haifa.cs.HSTS.server.CommandInterface;

public class TestsFacadeReadBySubjectCommand extends CommandInterface {
    String subjectName;

    public TestsFacadeReadBySubjectCommand(String subject){
        this.subjectName = subject;
    }

    public String getSubjectName() {
        return subjectName;
    }
}
