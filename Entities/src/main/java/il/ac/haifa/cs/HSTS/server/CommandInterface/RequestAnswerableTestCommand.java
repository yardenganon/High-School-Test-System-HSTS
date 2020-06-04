package il.ac.haifa.cs.HSTS.server.CommandInterface;

import il.ac.haifa.cs.HSTS.server.Entities.Student;

public class RequestAnswerableTestCommand extends CommandInterface{
    private String readyTestCode;
    private Student student;

    public RequestAnswerableTestCommand(String readyTestCode, Student student){
        this.readyTestCode = readyTestCode;
        this.student = student;
    }

    public String getReadyTestCode() {
        return readyTestCode;
    }

    public Student getStudent() {
        return student;
    }
}
