package il.ac.haifa.cs.HSTS.server.CommandInterface;

import il.ac.haifa.cs.HSTS.server.Entities.Teacher;

public class TestFacadeReadAllByTeacherCommand extends CommandInterface{
    Teacher teacher;

    public TestFacadeReadAllByTeacherCommand(Teacher teacher) {
        this.teacher = teacher;
    }

    public Teacher getTeacher() {
        return teacher;
    }
}
