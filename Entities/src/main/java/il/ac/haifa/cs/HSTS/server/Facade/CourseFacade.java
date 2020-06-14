package il.ac.haifa.cs.HSTS.server.Facade;

import java.io.Serializable;

public class CourseFacade implements Serializable {
    int courseId;
    String name;
    String subjectOfCourse;

    public CourseFacade(int courseId, String name, String subjectOfCourse) {
        this.courseId = courseId;
        this.name = name;
        this.subjectOfCourse = subjectOfCourse;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubjectOfCourse() {
        return subjectOfCourse;
    }

    public void setSubjectOfCourse(String subjectOfCourse) {
        this.subjectOfCourse = subjectOfCourse;
    }
}