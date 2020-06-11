package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

public class UncheckedAnswerableTest {

    private String idQuestion;
    private String question;
    private String studentAnswer;
    private String correctAnswer;
    private String answer1, answer2, answer3, answer4;

    public UncheckedAnswerableTest(String idQuestion, String question, String studentAnswer, String correctAnswer, String answer1,
                                   String answer2, String answer3, String answer4) {
        this.idQuestion = idQuestion;
        this.question = question;
        this.studentAnswer = studentAnswer;
        this.correctAnswer = correctAnswer;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public String getIdQuestion() {
        return idQuestion;
    }

    public String getQuestion() {
        return question;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
