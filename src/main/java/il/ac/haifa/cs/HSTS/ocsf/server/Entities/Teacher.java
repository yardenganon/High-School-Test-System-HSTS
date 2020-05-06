package il.ac.haifa.cs.HSTS.ocsf.server.Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Teacher extends User implements Serializable {
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "teacher")
    List<Course> courses;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "writer")
    List<Question> questions;

    @ManyToMany
    @JoinTable(
            name = "subjects_teacher",
            joinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id", referencedColumnName = "id"))
    List<Subject> subjects;

    public Teacher(String username, String password, String email, String first_name, String last_name, String gender) {
        super(username, password, email, first_name, last_name, gender);
        this.courses = new ArrayList<Course>();
        this.questions = new ArrayList<Question>();
        this.subjects = new ArrayList<Subject>();
    }

    public Teacher(){}

    public void addCourse(Course course){
        this.courses.add(course);
    }

    public void addQuestion(Question question) { this.questions.add(question);}

    public void addSubject(Subject subject) {this.subjects.add(subject);}

}
