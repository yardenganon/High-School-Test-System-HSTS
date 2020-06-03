package il.ac.haifa.cs.HSTS.server.CommandInterface;

public class TestReadByTeacherCommand extends CommandInterface{
    private int teacherId;

    public TestReadByTeacherCommand(int teacherId){
        this.teacherId = teacherId;
    }

    public int getTeacherId() {
        return teacherId;
    }
}
