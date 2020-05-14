package il.ac.haifa.cs.HSTS.ocsf.server.Controllers;

import il.ac.haifa.cs.HSTS.ocsf.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.ocsf.server.CommandInterface.QuestionReadBySubjectCommand;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Subject;
import il.ac.haifa.cs.HSTS.ocsf.server.Repositories.QuestionsRepository;
import il.ac.haifa.cs.HSTS.ocsf.server.Services.Respond;

import java.util.Date;
import java.util.List;

public class QuestionReadBySubjectController implements ControllerInterface{

    final private QuestionsRepository questionsRepository;

    public QuestionReadBySubjectController(){
        this.questionsRepository = new QuestionsRepository();
    }

    @Override
    public Respond executeCommand(CommandInterface command){
        QuestionReadBySubjectCommand questionReadBySubjectCommand = (QuestionReadBySubjectCommand) command;
        List<Subject> subjectsList = questionReadBySubjectCommand.getSubjectsList();
        List<Subject> subjectsFromRepository = questionsRepository.getQuestionsBySubject(subjectsList);

        Respond respondMessage = new Respond();
        respondMessage.setDateHandled(new Date());
        respondMessage.setReturnedObject(subjectsFromRepository);
        respondMessage.setStatus("Success");
        respondMessage.setRespondName("ReadBySubject");
        System.out.print("Command handled successfully ");
        return respondMessage;
    }

    @Override
    public String getControllerName() {
        return "QuestionReadBySubjectController";
    }
}

