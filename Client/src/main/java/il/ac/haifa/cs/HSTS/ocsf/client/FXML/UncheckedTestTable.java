package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

public class UncheckedTestTable {

    private String id;
    private String courseName;
    private String studentName;
    private String grade;

    public UncheckedTestTable(String id, String courseName, String studentName, String grade) {
        this.id = id;
        this.courseName = courseName;
        this.studentName = studentName;
        this.grade = grade;
    }

    public String getId() {
        return id;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getGrade() {
        return grade;
    }

}
