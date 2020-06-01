package il.ac.haifa.cs.HSTS.server.CommandInterface;

import il.ac.haifa.cs.HSTS.server.Entities.Question;

public class QuestionPushCommand extends CommandInterface{

    private Question newQuestion;

    public QuestionPushCommand(Question newQuestion){
        this.newQuestion = newQuestion;
    }

    public Question getNewQuestion() {
        return newQuestion;
    }
}
