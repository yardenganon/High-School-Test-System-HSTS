package il.ac.haifa.cs.HSTS.server.Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "tests")
public class Test implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "questions_tests",
            joinColumns = @JoinColumn(name = "test_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "question_id", referencedColumnName = "id"))
    List<Question> questionList;

    @ElementCollection
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

    String introduction;
    String epilogue;
    Integer time;
    String commentForTeachers;

    public Test(Teacher writer, Subject subject) {
        this.readyTests = new ArrayList<ReadyTest>();
        this.questionList = new ArrayList<Question>();
        this.points = new HashMap<Question, Integer>(); // Mapping question to it's points
        this.writer = writer;
        writer.addTest(this);
        this.subject = subject;
        subject.addTest(this);
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

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
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
}
