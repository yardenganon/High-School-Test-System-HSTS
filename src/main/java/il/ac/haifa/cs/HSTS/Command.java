package il.ac.haifa.cs.HSTS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/* Create new command in Client to ask for services from server */

public class Command implements Serializable {
    String controller;
    String command;
    Object[] parameters;
    String status;
    Date dateCreated;
    Date dateHandled;

    public Command(String controller ,String command, Object... parameters) {
        this.controller = controller;
        this.command = command;
        this.parameters = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++)
            this.parameters[i] = parameters[i];
        this.status = "Open";
        this.dateCreated = new Date();
    }

    public void notifySuccessfullyHandled() {this.status = "Success";}

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
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
    public Object[] getParameters() {
        return parameters;
    }
    public Object getParameter(int i) {
        return parameters[i];
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
    public void printCommandDetails(){
        System.out.println("Command Received- [Controller= " + (this.getController() +
                ", Type= " + this.getCommand())+" ,Status= "+this.getStatus() +", Date = " + this.getDateCreated() +"]");
    }

    @Override
    public String toString() {
        return "Command{" +
                "command='" + command + '\'' +
                ", status='" + status + '\'' +
                ", dateCreated=" + dateCreated +
                ", dateHandled=" + dateHandled +
                '}';
    }
}
