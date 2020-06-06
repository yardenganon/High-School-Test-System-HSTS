package il.ac.haifa.cs.HSTS.server.Facade;

import java.io.Serializable;

public class AnswerableTestFacade implements Serializable {
    Integer answerableTestId;
    Integer score;
    String courseName;
    String firstName;
    String lastName;

    public AnswerableTestFacade(){}

    public AnswerableTestFacade(Integer answerableTestId, Integer score, String courseName, String firstName, String lastName) {
        this.answerableTestId = answerableTestId;
        this.score = score;
        this.courseName = courseName;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getAnswerableTestId() {
        return answerableTestId;
    }

    public void setAnswerableTestId(Integer answerableTestId) {
        this.answerableTestId = answerableTestId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "AnswerableTestFacade{" +
                "answerableTestId=" + answerableTestId +
                ", score=" + score +
                ", courseName='" + courseName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public Integer getAnswerableTestId() { return answerableTestId; }

    public Integer getScore() { return score; }

    public String getCourseName() { return courseName; }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

}
