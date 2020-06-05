package il.ac.haifa.cs.HSTS.server.CommandInterface;

import il.ac.haifa.cs.HSTS.server.Entities.Teacher;

public class getAnswerableTestsFacadeCommand extends CommandInterface{
    Teacher teacher;

    public getAnswerableTestsFacadeCommand(Teacher teacher){
        this.teacher = teacher;
    }

    public Teacher getTeacher() {
        return teacher;
    }
}
