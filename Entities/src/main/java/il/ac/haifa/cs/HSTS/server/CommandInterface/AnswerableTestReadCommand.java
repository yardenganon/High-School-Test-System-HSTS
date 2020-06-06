package il.ac.haifa.cs.HSTS.server.CommandInterface;

public class AnswerableTestReadCommand extends CommandInterface{
    int answerableTestId;

    public AnswerableTestReadCommand(int answerableTestId){
        this.answerableTestId = answerableTestId;
    }

    public int getAnswerableTestId() {
        return answerableTestId;
    }
}
