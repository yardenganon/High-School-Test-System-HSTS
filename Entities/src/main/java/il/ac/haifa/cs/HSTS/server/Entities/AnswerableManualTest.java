package il.ac.haifa.cs.HSTS.server.Entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "answerableManualTest")
public class AnswerableManualTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "readytest_id")
    ReadyTest readyTest;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    Student student;

    Date timeStarted;
    Date timeFinished;
    int score;
    String teacherComment;
    String link;
    String submissionLink;

    public AnswerableManualTest(ReadyTest readyTest, Student student) {
        this.readyTest = readyTest;
        this.student = student;
        this.timeStarted = new Date();
    }

    public String getSubmissionLink() {
        return submissionLink;
    }

    public void setSubmissionLink(String submissionLink) {
        this.submissionLink = submissionLink;
    }

    public int getId() {
        return id;
    }

    public ReadyTest getReadyTest() {
        return readyTest;
    }

    public void setReadyTest(ReadyTest readyTest) {
        this.readyTest = readyTest;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Date getTimeStarted() {
        return timeStarted;
    }

    public void setTimeStarted(Date timeStarted) {
        this.timeStarted = timeStarted;
    }

    public Date getTimeFinished() {
        return timeFinished;
    }

    public void setTimeFinished(Date timeFinished) {
        this.timeFinished = timeFinished;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTeacherComment() {
        return teacherComment;
    }

    public void setTeacherComment(String teacherComment) {
        this.teacherComment = teacherComment;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
