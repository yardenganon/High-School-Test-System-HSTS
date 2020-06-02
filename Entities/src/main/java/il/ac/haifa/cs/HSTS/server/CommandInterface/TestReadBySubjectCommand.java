package il.ac.haifa.cs.HSTS.server.CommandInterface;

public class TestReadBySubjectCommand extends CommandInterface {
    String subjectName;

    public TestReadBySubjectCommand(String subject){
        this.subjectName = subject;
    }

    public String getSubjectName() {
        return subjectName;
    }
}
