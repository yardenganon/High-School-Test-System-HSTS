package il.ac.haifa.cs.HSTS.server.Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
@Table(name = "timeExtensionRequests")
public class TimeExtensionRequest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Teacher initiator;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    ReadyTest test;

    Principle principleHandled;

    Date dateInitiated;
    Date handledByPrinciple;
    String status;
    String description;

    public TimeExtensionRequest(Teacher initiator, ReadyTest test, String description) {
        this.initiator = initiator;
        this.test = test;
        this.dateInitiated = new Date();
        this.status = "Pending";
        this.description = description;

        initiator.addTimeExtensionRequest(this);
        test.addTimeExtensionRequest(this);

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public Teacher getInitiator() {
        return initiator;
    }

    public void setInitiator(Teacher initiator) {
        this.initiator = initiator;
    }

    public ReadyTest getTest() {
        return test;
    }

    public void setTest(ReadyTest test) {
        this.test = test;
    }

    public Date getDateInitiated() {
        return dateInitiated;
    }

    public void setDateInitiated(Date dateInitiated) {
        this.dateInitiated = dateInitiated;
    }

    public Date getHandledByPrinciple() {
        return handledByPrinciple;
    }

    public void setHandledByPrinciple(Date handledByPrinciple) {
        this.handledByPrinciple = handledByPrinciple;
    }

    public Principle getPrincipleHandled() {
        return principleHandled;
    }

    public void setPrincipleHandled(Principle principleHandled) {
        this.principleHandled = principleHandled;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
