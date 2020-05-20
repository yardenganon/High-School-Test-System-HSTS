package il.ac.haifa.cs.HSTS.server.Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student extends User implements Serializable {
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "students")
    List<Course> courses;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "student")
    List<AnswerableTest> answerableTests;

    public Student(String username, String password, String email, String first_name, String last_name, String gender) {
        super(username, password, email, first_name, last_name, gender);
        this.courses = new ArrayList<Course>();
        this.answerableTests = new ArrayList<AnswerableTest>();
    }
    public Student() {
    }
    public void addCourse(Course course){
        courses.add(course);
    }
    public void addAnswerableTest(AnswerableTest answerableTest) {this.answerableTests.add(answerableTest); }

}
