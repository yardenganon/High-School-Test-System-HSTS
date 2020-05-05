package il.ac.haifa.cs.HSTS.ocsf.server.Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subject")
public class Subject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    int numberOfQuestions;
    String subjectName;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "subject")
    List<Question> questions;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "subject")
    List<Course> courses;


    public Subject(){}
    public Subject(String subjectName){
        this.subjectName = subjectName;
        this.questions = new ArrayList<Question>();
        this.numberOfQuestions = 0;
    }

    public int getId() {
        return id;
    }

    public String getSubjectName() {
        return this.subjectName;
    }

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
}
