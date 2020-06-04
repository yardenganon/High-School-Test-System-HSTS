package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.server.Entities.Subject;

public class QuestionOfTestTableView {
        String question;
        String answer1;
        String answer2;
        String answer3;
        String answer4;
        int correctAnswer;
        Integer points;

    public QuestionOfTestTableView(String question, String answer1, String answer2, String answer3,
                                   String answer4, int correctAnswer, int points) {
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.correctAnswer = correctAnswer;
        this.points = points;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer1() {
        return answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public int getPoints() {
        return points;
    }
}
