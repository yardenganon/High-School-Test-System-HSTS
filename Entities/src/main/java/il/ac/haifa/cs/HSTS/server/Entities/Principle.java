package il.ac.haifa.cs.HSTS.server.Entities;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class Principle extends User implements Serializable {

    public Principle(String username, String password, String email, String first_name, String last_name, String gender) {
        super(username, password, email, first_name, last_name, gender);
    }

    public Principle() {
    }
}
