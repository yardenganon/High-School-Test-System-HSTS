package il.ac.haifa.cs.HSTS.server.Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "answerableTest")
public class AnswerableTest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "test_id")
    ReadyTest test;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    Student student;

    @ElementCollection
    @CollectionTable(name="answersForQuestions",
            joinColumns = {@JoinColumn(name = "answerabletest_id", referencedColumnName = "id")})
    @MapKeyColumn(name="question")
    @Column(name = "answers")
    Map<Question,Integer> answers;

    Date timeStarted;
    Date timeFinished;
    private Integer score;
    private Boolean isChecked;
    private String teacherComment;


    public AnswerableTest(ReadyTest test, Student student) {
        this.test = test;
        this.student = student;
        this.answers = new HashMap<>();
        this.timeStarted = new Date();

        test.addAnswerableTest(this);
        student.addAnswerableTest(this);
    }

    public int getId() {
        return id;
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

    public void addAnswer(Question question, Integer integer){
        this.answers.put(question,integer);
    }
    public ReadyTest getTest() {
        return test;
    }

    public void setTest(ReadyTest test) {
        this.test = test;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Map<Question, Integer> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Question, Integer> answers) {
        this.answers = answers;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public String getTeacherComment() {
        return teacherComment;
    }

    public void setTeacherComment(String teacherComment) {
        this.teacherComment = teacherComment;
    }
}