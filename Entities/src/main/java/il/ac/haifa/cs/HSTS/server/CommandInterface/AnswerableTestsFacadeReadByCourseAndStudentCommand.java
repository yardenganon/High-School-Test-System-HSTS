package il.ac.haifa.cs.HSTS.server.CommandInterface;

public class AnswerableTestsFacadeReadByCourseAndStudentCommand extends CommandInterface{
    int studentId;
    String courseName;

    public AnswerableTestsFacadeReadByCourseAndStudentCommand(int studentId, String courseName) {
        this.studentId = studentId;
        this.courseName = courseName;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
