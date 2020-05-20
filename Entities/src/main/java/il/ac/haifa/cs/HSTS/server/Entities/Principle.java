package il.ac.haifa.cs.HSTS.server.Entities;

import javax.persistence.Entity;
import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Principle extends User implements Serializable {

    List<TimeExtensionRequest> timeExtensionRequestList;

    public Principle(String username, String password, String email, String first_name, String last_name, String gender) {
        super(username, password, email, first_name, last_name, gender);
        this.timeExtensionRequestList = new ArrayList<TimeExtensionRequest>();
    }

    public Principle() {
    }

    public void addTimeExtensionRequest(TimeExtensionRequest request) {
        this.timeExtensionRequestList.add(request);
    }
}
