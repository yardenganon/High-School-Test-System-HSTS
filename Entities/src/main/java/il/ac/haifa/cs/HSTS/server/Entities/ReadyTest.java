package il.ac.haifa.cs.HSTS.server.Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "readyTest")
public class ReadyTest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date dateCreated;
    private Boolean isManual;
    private Boolean isActive;
    private String code;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "test_id")
    private Test test;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "modifiedPointsForQuestion",
            joinColumns = {@JoinColumn(name = "readytest_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "question")
    @Column(name = "modifiedPoints")
    private Map<Question, Integer> modifiedPoints;

    private Integer modifiedTime;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "modifierteacher_id")
    private Teacher modifierWriter;

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, mappedBy = "test")
    List<AnswerableTest> answerableTests;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "readyTest")
    List<AnswerableManualTest> answerableManualTests;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "test")
    TimeExtensionRequest timeExtensionRequest;

    public ReadyTest(){}

    public ReadyTest(Test test, String code, Course course, Teacher modifierWriter) {
        this.test = test;
        this.code = code;
        this.course = course;
        this.isManual = false;
        this.isActive = false;
        this.modifiedTime = test.getTime();
        this.modifiedTime = test.getTime();
        this.modifiedPoints = new HashMap<>();
        this.modifierWriter = modifierWriter;
        this.dateCreated = new Date();
        for (Question question : test.getQuestionSet())
            modifiedPoints.put(question, test.getPoints().get(question));

        this.answerableTests = new ArrayList<AnswerableTest>();
        this.answerableManualTests = new ArrayList<AnswerableManualTest>();
        /*
        course.addReadyTest(this);
        test.addReadyTest(this);
        modifierWriter.addReadyTest(this);
         */
    }

    public TimeExtensionRequest getTimeExtensionRequest() {
        return timeExtensionRequest;
    }

    public void setTimeExtensionRequest(TimeExtensionRequest timeExtensionRequest) {
        this.timeExtensionRequest = timeExtensionRequest;
    }

    public Boolean getManual() {
        return isManual;
    }

    public void setManual(Boolean manual) {
        isManual = manual;
    }

    public void addAnswerableManualTest(AnswerableManualTest answerableManualTest) {
        this.answerableManualTests.add(answerableManualTest);
    }

    public void addAnswerableTest(AnswerableTest answerableTest) {
        this.answerableTests.add(answerableTest);
    }

    public int getId() {
        return id;
    }

    public Teacher getModifierWriter() {
        return modifierWriter;
    }

    public void setModifierWriter(Teacher modifierWriter) {
        this.modifierWriter = modifierWriter;
    }

    public void modifyPointsForQuestion(Question question, Integer points) {
        this.modifiedPoints.put(question, points);
    }

    public Map<Question, Integer> getModifiedPoints() {
        return modifiedPoints;
    }

    public void setModifiedPoints(Map<Question, Integer> modifiedPoints) {
        this.modifiedPoints = modifiedPoints;
    }

    public Integer getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Integer modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "ReadyTest{" +
                "id=" + id +
                ", dateCreated=" + dateCreated +
                ", isManual=" + isManual +
                ", isActive=" + isActive +
                ", code='" + code + '\'' +
                ", course=" + course +
                ", test=" + test +
                ", modifiedPoints=" + modifiedPoints +
                ", modifiedTime=" + modifiedTime +
                ", modifierWriter=" + modifierWriter +
                '}';
    }
}
