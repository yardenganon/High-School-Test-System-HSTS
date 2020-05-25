package il.ac.haifa.cs.HSTS.server.CommandInterface;

import il.ac.haifa.cs.HSTS.server.Entities.Subject;

import java.util.List;

public class QuestionReadBySubjectCommand extends CommandInterface {

    List<Subject> subjectsList;

    public QuestionReadBySubjectCommand(List<Subject> subjectsList){
        this.subjectsList = subjectsList;
    }

    public List<Subject> getSubjectsList() {
        return subjectsList;
    }

    @Override
    public String getCommandName() {
        return this.getClass().getSimpleName();
    }
}