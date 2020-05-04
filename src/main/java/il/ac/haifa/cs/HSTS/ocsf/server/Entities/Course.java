package il.ac.haifa.cs.HSTS.ocsf.server.Entities;


import java.util.ArrayList;
import java.util.List;

public class Course {

    int id;
    Subject subject;
    Teacher teacher;
    List<Student> students;

    public Course(Subject subject, Teacher teacher) {
        this.subject = subject;
        this.teacher = teacher;
        this.students = new ArrayList<Student>();
    }
    public void addStudent(Student student)
    {
        students.add(student);
    }
    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
