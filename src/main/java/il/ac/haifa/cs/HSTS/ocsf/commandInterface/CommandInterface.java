package il.ac.haifa.cs.HSTS.ocsf.commandInterface;

import java.io.Serializable;
import java.util.Date;

public abstract class CommandInterface implements Serializable {
    protected String status;
    protected Date dateCreated;
    protected Date dateHandled;

    public CommandInterface(){
        status = "Open";
        dateCreated = new Date();
        dateHandled = null;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateHandled() {
        return dateHandled;
    }

    public void setDateHandled(Date dateHandled) {
        this.dateHandled = dateHandled;
    }

    public String getCommandName(){
        return this.getClass().getSimpleName();
    }
}
