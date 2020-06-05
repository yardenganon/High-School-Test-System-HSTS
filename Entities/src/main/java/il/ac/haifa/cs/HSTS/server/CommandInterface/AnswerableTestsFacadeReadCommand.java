package il.ac.haifa.cs.HSTS.server.CommandInterface;

import il.ac.haifa.cs.HSTS.server.Entities.Teacher;

public class AnswerableTestsFacadeReadCommand extends CommandInterface{
    Teacher teacher;

    public AnswerableTestsFacadeReadCommand(Teacher teacher){
        this.teacher = teacher;
    }

    public Teacher getTeacher() {
        return teacher;
    }
}
