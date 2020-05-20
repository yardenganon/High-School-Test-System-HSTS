package il.ac.haifa.cs.HSTS.server.Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subjects")
public class Subject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    int numberOfQuestions;
    String subjectName;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, mappedBy = "subject")
    List<Question> questions;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "subject")
    List<Course> courses;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "subject")
    List<Test> tests;
    @ManyToMany(mappedBy = "subjects",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Teacher> teachers;


    public Subject(){}
    public Subject(String subjectName){
        this.subjectName = subjectName;
        this.questions = new ArrayList<Question>();
        this.teachers = new ArrayList<Teacher>();
        this.tests = new ArrayList<Test>();
        this.courses = new ArrayList<Course>();
        this.numberOfQuestions = 0;
    }

    public void addTeacher(Teacher teacher) {
        this.teachers.add(teacher);
        teacher.addSubject(this);
    }

    public int getId() {
        return id;
    }

    public String getSubjectName() {
        return this.subjectName;
    }
    public void addCourse(Course course) {
        this.courses.add(course);
    }
    public void addQuestion(Question question) {
        this.questions.add(question);
        numberOfQuestions++;
    }
    public void addTest(Test test) { this.tests.add(test); }

    public void setSubject_name(String subjectName) {
        this.subjectName = subjectName;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", numberOfQuestions=" + numberOfQuestions +
                ", subjectName='" + subjectName + '\'' +
                ", questions=" + questions +

                '}';
    }
}
