package il.ac.haifa.cs.HSTS.server.Entities;

import il.ac.haifa.cs.HSTS.server.Status.Status;

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
    @JoinColumn(name = "teacher_id")
    Teacher initiator;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "principle_id")
    Principle principle;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "readytest_id")
    ReadyTest test;

    Date dateInitiated;
    Date dateHandledByPrinciple;
    Status status;
    String description;
    int timeToAdd;

    public TimeExtensionRequest(Teacher initiator, ReadyTest test, String description, int timeToAdd) {
        this.initiator = initiator;
        this.test = test;
        this.dateInitiated = new Date();
        this.status = Status.OpenRequest;
        this.description = description;
        this.timeToAdd = timeToAdd;
    }

    public TimeExtensionRequest() {
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

    public Date getDateHandledByPrinciple() {
        return dateHandledByPrinciple;
    }

    public void setDateHandledByPrinciple(Date dateHandledByPrinciple) {
        this.dateHandledByPrinciple = dateHandledByPrinciple;
    }

    public Principle getPrincipleHandled() {
        return principle;
    }

    public void setPrincipleHandled(Principle principleHandled) {
        this.principle = principleHandled;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Principle getPrinciple() {
        return principle;
    }

    public void setPrinciple(Principle principle) {
        this.principle = principle;
    }

    public int getTimeToAdd() {
        return timeToAdd;
    }

    public void setTimeToAdd(int timeToAdd) {
        this.timeToAdd = timeToAdd;
    }
}
