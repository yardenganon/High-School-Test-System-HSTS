package il.ac.haifa.cs.HSTS.server.Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student extends User implements Serializable {
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "students")
    List<Course> courses;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "student")
    List<AnswerableTest> answerableTests;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "student")
    List<AnswerableManualTest> answerableManualTests;

    public Student(String username, String password, String email, String first_name, String last_name, String gender) {
        super(username, password, email, first_name, last_name, gender);
        this.courses = new ArrayList<Course>();
        this.answerableTests = new ArrayList<AnswerableTest>();
        this.answerableManualTests = new ArrayList<AnswerableManualTest>();
    }

    public Student() {
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void addAnswerableTest(AnswerableTest answerableTest) {
        this.answerableTests.add(answerableTest);
    }

    public void addAnswerableManualTest(AnswerableManualTest answerableManualTest) {
        this.answerableManualTests.add(answerableManualTest);
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<AnswerableTest> getAnswerableTests() {
        return answerableTests;
    }

    public void setAnswerableTests(List<AnswerableTest> answerableTests) {
        this.answerableTests = answerableTests;
    }

    public List<AnswerableManualTest> getAnswerableManualTests() {
        return answerableManualTests;
    }

    public void setAnswerableManualTests(List<AnswerableManualTest> answerableManualTests) {
        this.answerableManualTests = answerableManualTests;
    }
}
