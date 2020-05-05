package il.ac.haifa.cs.HSTS.ocsf.server.Entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Teacher extends User{
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "teacher")
    List<Course> courses;

    public Teacher(String username, String password, String email, String first_name, String last_name, String gender, String job) {
        super(username, password, email, first_name, last_name, gender, job);
        this.courses = new ArrayList<Course>();
    }

    public Teacher(){}

    public void addCourse(Course course){
        courses.add(course);
    }

}
