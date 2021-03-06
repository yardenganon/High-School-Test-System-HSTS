package il.ac.haifa.cs.HSTS.server.Facade;

import java.io.Serializable;

public class AnswerableTestFacade implements Serializable {
    Integer answerableTestId;
    String fullName;
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

    public AnswerableTestFacade(Integer answerableTestId, String firstName,
                                String lastName, int score) {
        this.answerableTestId = answerableTestId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = firstName + " " + lastName;
        this.score = score;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

}
