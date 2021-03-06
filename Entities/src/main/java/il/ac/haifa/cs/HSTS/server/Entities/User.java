package il.ac.haifa.cs.HSTS.server.Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

//@Entity(name = "users")
//@MappedSuperclass//(strategy = InheritanceType.SINGLE_TABLE)

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "users")
public abstract class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int idNumber;
    private String username;
    private String password;
    private String email;
    private String first_name;
    private String last_name;
    private String gender;
    private Date date_of_signup;
    private Date date_of_last_login;
    private Boolean isLoggedIn;

    public User(String username, String password, String email, String first_name, String last_name, String gender) {
        this.username = username;
        this.idNumber = 123456789;
        this.password = password;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.date_of_signup = new Date();
        this.date_of_last_login = new Date();
        this.isLoggedIn = false;
    }

    public int getId() {
        return id;
    }

    public User(){}

    public Boolean getLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public Date getDate_of_signup() {
        return date_of_signup;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Date getDate_of_last_login() {
        return date_of_last_login;
    }

    public void setDate_of_last_login(Date date_of_last_login) {
        this.date_of_last_login = date_of_last_login;
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

    public int getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                '}';
    }
}
