package il.ac.haifa.cs.HSTS.server.CommandInterface;

import il.ac.haifa.cs.HSTS.server.Entities.Question;

public class QuestionUpdateCommand extends CommandInterface{

    private Question questionToUpdate;

    public QuestionUpdateCommand(Question question){
        this.questionToUpdate = question;
    }

    public Question getQuestionToUpdate() {
        return questionToUpdate;
    }
}
