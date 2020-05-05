package il.ac.haifa.cs.HSTS.ocsf.server.Entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student extends User {
    @ManyToMany(mappedBy = "students",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Course> courses;

    public Student(String username, String password, String email, String first_name, String last_name, String gender, String job) {
        super(username, password, email, first_name, last_name, gender);
        this.courses = new ArrayList<Course>();
    }
    public void addCourse(Course course){
        courses.add(course);
    }
    public Student() {
    }
}
