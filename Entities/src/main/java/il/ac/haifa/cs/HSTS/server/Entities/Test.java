package il.ac.haifa.cs.HSTS.server.Entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "tests")
public class Test implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "questions_tests",
            joinColumns = @JoinColumn(name = "test_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "question_id", referencedColumnName = "id"))
    Set<Question> questionList;

    @ElementCollection(fetch= FetchType.EAGER)
    @CollectionTable(name = "pointsForQuestionMapping",
            joinColumns = {@JoinColumn(name = "test_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "question")
    @Column(name = "points")
    Map<Question, Integer> points;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "writer_id")
    Teacher writer;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
    Subject subject;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "test")
    List<ReadyTest> readyTests;

    Date dateCreated;
    String introduction;
    String epilogue;
    Integer time;
    String commentForTeachers;

    public Test(){}

    public Date getDateCreated() {
        return dateCreated;
    }

    public Test(Teacher writer, Subject subject) {
        this.readyTests = new ArrayList<ReadyTest>();
        this.questionList = new HashSet<>();
        this.points = new HashMap<Question, Integer>(); // Mapping question to it's points
        this.writer = writer;
        writer.addTest(this);
        this.subject = subject;
        subject.addTest(this);
        this.dateCreated = new Date();
    }

    public void addReadyTest(ReadyTest readyTest) {
        this.readyTests.add(readyTest);
    }

    public void setPointsToQuestion(Question question, Integer points) {
        this.points.put(question, points);
    }

    public Map<Question, Integer> getPoints() {
        return points;
    }

    public int getId() {
        return id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setPoints(HashMap<Question, Integer> points) {
        this.points = points;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Set<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(Set<Question> questionList) {
        this.questionList = questionList;
    }

    public Teacher getWriter() {
        return writer;
    }

    public String getEpilogue() {
        return epilogue;
    }

    public void setEpilogue(String epilogue) {
        this.epilogue = epilogue;
    }

    public void addQuestion(Question question, Integer points) {
        this.questionList.add(question);
        this.points.put(question, points);
        question.addTest(this);
    }

    public String getCommentForTeachers() {
        return commentForTeachers;
    }

    public void setCommentForTeachers(String commentForTeachers) {
        this.commentForTeachers = commentForTeachers;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", questionList=" + questionList +
                ", points=" + points +
                ", writer=" + writer +
                ", subject=" + subject +
                ", dateCreated=" + dateCreated +
                ", introduction='" + introduction + '\'' +
                ", epilogue='" + epilogue + '\'' +
                ", time=" + time +
                ", commentForTeachers='" + commentForTeachers + '\'' +
                '}';
    }
}
