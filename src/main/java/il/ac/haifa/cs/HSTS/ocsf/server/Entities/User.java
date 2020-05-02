package il.ac.haifa.cs.HSTS.ocsf.server.Entities;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class User {
    private String username;
    private String password;
    private String email;
    private String first_name;
    private String last_name;
    private String gender;
    private Date date_of_signup;
    private Date date_of_last_login;

    private String job;

    public User(String username, String password, String email, String first_name, String last_name, String gender, String job) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.date_of_signup = new Date();
        this.date_of_last_login = new Date();
        this.job = job;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
