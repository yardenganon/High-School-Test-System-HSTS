package il.ac.haifa.cs.HSTS.server.Facade;

import java.util.Date;

public class TestFacade {
    int id;
    String teacherWriter;
    String subject;
    Date dateCreated;
    int numberOfQuestions;
    int time;
    public TestFacade(int id, String teacherWriter, String subject, Date dateCreated, int numberOfQuestions, int time) {
        this.id = id;
        this.teacherWriter = teacherWriter;
        this.subject = subject;
        this.dateCreated = dateCreated;
        this.numberOfQuestions = numberOfQuestions;
        this.time = time;
    }

    @Override
    public String toString() {
        return "TestFacade{" +
                "id=" + id +
                ", teacherWriter='" + teacherWriter + '\'' +
                ", subject='" + subject + '\'' +
                ", dateCreated=" + dateCreated +
                ", numberOfQuestions=" + numberOfQuestions +
                ", time=" + time +
                '}';
    }
}
