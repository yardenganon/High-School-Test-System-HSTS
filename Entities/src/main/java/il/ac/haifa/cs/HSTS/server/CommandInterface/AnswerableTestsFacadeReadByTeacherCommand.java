package il.ac.haifa.cs.HSTS.server.CommandInterface;

import il.ac.haifa.cs.HSTS.server.Entities.Teacher;

public class AnswerableTestsFacadeReadByTeacherCommand extends CommandInterface{
    Teacher teacher;
    int testId;

    public AnswerableTestsFacadeReadByTeacherCommand(Teacher teacher, int testId) {
        this.teacher = teacher;
        this.testId = testId;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public int getTestId() {
        return testId;
    }
}
