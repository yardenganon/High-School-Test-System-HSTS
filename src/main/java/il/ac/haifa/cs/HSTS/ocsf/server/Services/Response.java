package il.ac.haifa.cs.HSTS.ocsf.server.Services;

import java.io.Serializable;
import java.util.Date;

public class Response implements Serializable {
    protected String status;
    protected Date dateCreated;
    protected Date dateHandled;
    protected Object returnedObject;
    protected String respondName;

    public Response(){
        status = "Open";
        dateCreated = new Date();
        dateHandled = null;
        returnedObject = null;
        respondName = "";
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

    public Date getDateHandled() {
        return dateHandled;
    }

    public void setDateHandled(Date dateHandled) {
        this.dateHandled = dateHandled;
    }

    public Object getReturnedObject() {
        return returnedObject;
    }

    public void setReturnedObject(Object returnedObject) {
        this.returnedObject = returnedObject;
    }

    public String getRespondName() {
        return respondName;
    }

    public void setRespondName(String respondName) {
        this.respondName = respondName;
    }

    public String getReturnedObjectName(){
        if(returnedObject != null)
            return returnedObject.getClass().getSimpleName();
        else return "No Returned Object";
    }

    @Override
    public String toString() {
        return "Respond{" +
                "status='" + status + '\'' +
                ", dateCreated=" + dateCreated +
                ", dateHandled=" + dateHandled +
                ", returnedObject=" + returnedObject +
                ", respondName='" + respondName + '\'' +
                '}';
    }
}
