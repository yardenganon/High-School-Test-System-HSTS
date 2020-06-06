package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.server.Entities.AnswerableTest;
import il.ac.haifa.cs.HSTS.server.Entities.Question;
import java.util.Map;

public class ExamReview {
    private AnswerableTest answerableTest;

    public ExamReview(AnswerableTest answerableTest) {
        this.answerableTest = answerableTest;
    }

    public int checkExam(){
        Map<Question, Integer> testAnswers = this.answerableTest.getAnswers();
        Map<Question, Integer> questionPoints = this.answerableTest.getTest().getModifiedPoints();

        int sum = 0;
        for(Question question: testAnswers.keySet()){
            if(question != null)
                if(question.getCorrectAnswer() == testAnswers.get(question))
                    sum += questionPoints.get(question);
        }
        return sum;
    }
}
