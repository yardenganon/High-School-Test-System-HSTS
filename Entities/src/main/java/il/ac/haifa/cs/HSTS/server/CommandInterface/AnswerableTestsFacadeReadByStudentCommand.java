package il.ac.haifa.cs.HSTS.server.CommandInterface;

import il.ac.haifa.cs.HSTS.server.Entities.Student;

public class AnswerableTestsFacadeReadByStudentCommand extends CommandInterface{
    Student student;

    public AnswerableTestsFacadeReadByStudentCommand(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }
}
