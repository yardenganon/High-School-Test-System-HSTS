package il.ac.haifa.cs.HSTS.ocsf.commandInterface;

import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Question;

public class QuestionUpdateCommand extends CommandInterface{

    private Question questionToUpdate;

    public QuestionUpdateCommand(Question question){
        this.questionToUpdate = question;
    }

    public Question getQuestionToUpdate() {
        return questionToUpdate;
    }

    @Override
    public String getCommandName() {
        return this.getClass().getSimpleName();
    }

}
