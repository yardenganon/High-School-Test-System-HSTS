package il.ac.haifa.cs.HSTS.server.CommandInterface;

import il.ac.haifa.cs.HSTS.server.Entities.AnswerableTest;

public class PushAnswerableTestCommand extends CommandInterface{
    AnswerableTest answerableTest;

    public PushAnswerableTestCommand(AnswerableTest answerableTest){
        this.answerableTest = answerableTest;
    }

    public AnswerableTest getAnswerableTest(){
        return this.answerableTest;
    }
}
