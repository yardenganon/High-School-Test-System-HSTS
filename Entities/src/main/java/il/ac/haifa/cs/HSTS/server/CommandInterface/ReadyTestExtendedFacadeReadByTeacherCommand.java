package il.ac.haifa.cs.HSTS.server.CommandInterface;

public class ReadyTestExtendedFacadeReadByTeacherCommand extends CommandInterface{
    private int teacherId;

    public ReadyTestExtendedFacadeReadByTeacherCommand(int teacherId){
        this.teacherId = teacherId;
    }

    public int getTeacherId() {
        return teacherId;
    }
}
