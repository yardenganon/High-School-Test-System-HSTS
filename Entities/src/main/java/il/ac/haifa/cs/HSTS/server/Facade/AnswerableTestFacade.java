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
