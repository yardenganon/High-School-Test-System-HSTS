package il.ac.haifa.cs.HSTS.server.CommandInterface;

public class TestReadByTeacherCommand extends CommandInterface{
    private int teacherId;

    public TestReadByTeacherCommand(int teacherName){
        this.teacherId = teacherName;
    }

    public int getTeacherId() {
        return teacherId;
    }
}
