package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

public class UncheckedAnswerableTest {

    private String idQuestion;
    private String question;
    private String studentAnswer;
    private String correctAnswer;

    public UncheckedAnswerableTest(String idQuestion, String question, String studentAnswer, String correctAnswer) {
        this.idQuestion = idQuestion;
        this.question = question;
        this.studentAnswer = studentAnswer;
        this.correctAnswer = correctAnswer;
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
