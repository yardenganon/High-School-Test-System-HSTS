package il.ac.haifa.cs.HSTS.server.CommandInterface;

import il.ac.haifa.cs.HSTS.server.Entities.Teacher;

public class AnswerableTestUpdateByIdCommand extends CommandInterface{
    private int answerableTestId;
    private Teacher teacher;

    public AnswerableTestUpdateByIdCommand(int answerableTestId, Teacher teacher) {
        this.answerableTestId = answerableTestId;
        this.teacher = teacher;
    }

    public int getAnswerableTestId() {
        return answerableTestId;
    }

    public Teacher getTeacher() {
        return teacher;
    }
}
