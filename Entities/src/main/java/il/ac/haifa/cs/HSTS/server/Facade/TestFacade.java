package il.ac.haifa.cs.HSTS.server.Facade;

import java.util.Date;

public class TestFacade {
    int id;
    String teacherWriter;
    String subject;
    Date dateCreated;
    int numberOfQuestions;
    public TestFacade(int id, String teacherWriter, String subject, Date dateCreated, int numberOfQuestions) {
        this.id = id;
        this.teacherWriter = teacherWriter;
        this.subject = subject;
        this.dateCreated = dateCreated;
        this.numberOfQuestions = numberOfQuestions;
    }

    @Override
    public String toString() {
        return "AppResult{" +
                "id=" + id +
                ", teacherWriter='" + teacherWriter + '\'' +
                ", subject='" + subject + '\'' +
                ", dateCreated=" + dateCreated +
                ", numberOfQuestions=" + numberOfQuestions +
                '}';
    }
}
