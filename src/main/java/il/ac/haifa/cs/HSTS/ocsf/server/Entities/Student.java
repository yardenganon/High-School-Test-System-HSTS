package il.ac.haifa.cs.HSTS.ocsf.server.Entities;

public class Student extends User {
    public Student(String username, String password, String email, String first_name, String last_name, String gender, String job) {
        super(username, password, email, first_name, last_name, gender, job);
    }

    public Student() {
    }
}
