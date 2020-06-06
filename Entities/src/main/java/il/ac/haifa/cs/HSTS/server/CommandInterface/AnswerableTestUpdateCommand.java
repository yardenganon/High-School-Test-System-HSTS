package il.ac.haifa.cs.HSTS.server.CommandInterface;

import il.ac.haifa.cs.HSTS.server.Entities.AnswerableTest;

public class AnswerableTestUpdateCommand extends CommandInterface{
    AnswerableTest answerableTest;

    public AnswerableTestUpdateCommand(AnswerableTest answerableTest) {
        this.answerableTest = answerableTest;
    }

    public AnswerableTest getAnswerableTest() {
        return answerableTest;
    }
}
