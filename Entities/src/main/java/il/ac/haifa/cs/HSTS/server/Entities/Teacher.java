package il.ac.haifa.cs.HSTS.server.Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Teacher extends User implements Serializable {
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, mappedBy = "teacher")
    Set<Course> courses;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "writer")
    List<Question> questions;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "writer")
    List<Test> tests;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "modifierWriter")
    List<ReadyTest> readyTests;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "initiator")
    List<TimeExtensionRequest> timeExtensionRequests;

    @ManyToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    @JoinTable(
            name = "subjects_teacher",
            joinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id", referencedColumnName = "id"))
    List<Subject> subjects;

    public Teacher(String username, String password, String email, String first_name, String last_name, String gender) {
        super(username, password, email, first_name, last_name, gender);
        this.courses = new HashSet<>();
        this.questions = new ArrayList<Question>();
        this.subjects = new ArrayList<Subject>();
        this.tests = new ArrayList<Test>();
        this.readyTests = new ArrayList<ReadyTest>();
        this.timeExtensionRequests = new ArrayList<TimeExtensionRequest>();
    }

    public Teacher() {
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }

    public void addQuestion(Question question) {
        //this.questions.add(question);
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public void addSubject(Subject subject) {
        this.subjects.add(subject);
    }

    public void addReadyTest(ReadyTest readyTest) {
        this.readyTests.add(readyTest);
    }

    public void addTimeExtensionRequest(TimeExtensionRequest request) {
        this.timeExtensionRequests.add(request);
    }

    public void addTest(Test test) {
        this.tests.add(test);
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    public List<ReadyTest> getReadyTests() {
        return readyTests;
    }

    public void setReadyTests(List<ReadyTest> readyTests) {
        this.readyTests = readyTests;
    }

    public List<TimeExtensionRequest> getTimeExtensionRequests() {
        return timeExtensionRequests;
    }

    public void setTimeExtensionRequests(List<TimeExtensionRequest> timeExtensionRequests) {
        this.timeExtensionRequests = timeExtensionRequests;
    }
}
