package il.ac.haifa.cs.HSTS.server.Facade;

import java.io.Serializable;
import java.util.Date;

public class TestFacade implements Serializable{
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeacherWriter() {
        return teacherWriter;
    }

    public void setTeacherWriter(String teacherWriter) {
        this.teacherWriter = teacherWriter;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
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
