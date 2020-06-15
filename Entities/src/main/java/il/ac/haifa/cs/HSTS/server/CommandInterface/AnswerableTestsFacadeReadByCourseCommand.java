package il.ac.haifa.cs.HSTS.server.CommandInterface;

public class AnswerableTestsFacadeReadByCourseCommand extends CommandInterface{
    String courseName;

    public AnswerableTestsFacadeReadByCourseCommand(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }
}
