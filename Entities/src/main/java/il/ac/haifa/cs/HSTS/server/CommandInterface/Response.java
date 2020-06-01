package il.ac.haifa.cs.HSTS.server.CommandInterface;

import il.ac.haifa.cs.HSTS.server.Status.Status;
import net.bytebuddy.dynamic.scaffold.TypeInitializer;

import javax.lang.model.type.NullType;
import java.io.Serializable;
import java.util.Date;

public class Response implements Serializable {
    protected Status status;
    protected Date dateCreated;
    protected Date dateHandled;
    protected Object returnedObject;
    protected String respondName;

    public Response(Status status){
        this.status = status;
        dateCreated = new Date();
        dateHandled = new Date();
        this.returnedObject = null;
        this.respondName = "";
    }

    public Response(String respondName){
        status = Status.OpenRequest;
        dateCreated = new Date();
        dateHandled = null;
        this.returnedObject = null;
        this.respondName = respondName;
    }

    public Response(Object returnedObject, String respondName){
        status = Status.OpenRequest;
        dateCreated = new Date();
        dateHandled = null;
        this.returnedObject = returnedObject;
        this.respondName = respondName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
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
