package il.ac.haifa.cs.HSTS.server.CommandInterface;

public class ReadyTestFacadeReadByTeacherCommand extends CommandInterface{
    private int teacherId;

    public ReadyTestFacadeReadByTeacherCommand(int teacherId){
        this.teacherId = teacherId;
    }

    public int getTeacherId() {
        return teacherId;
    }
}
