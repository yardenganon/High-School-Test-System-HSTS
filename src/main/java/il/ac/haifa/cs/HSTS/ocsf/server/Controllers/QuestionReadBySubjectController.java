package il.ac.haifa.cs.HSTS.ocsf.server.Controllers;

import il.ac.haifa.cs.HSTS.ocsf.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.ocsf.server.CommandInterface.QuestionReadBySubjectCommand;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Subject;
import il.ac.haifa.cs.HSTS.ocsf.server.Repositories.QuestionsRepository;
import il.ac.haifa.cs.HSTS.ocsf.server.Services.Response;

import java.util.Date;
import java.util.List;

public class QuestionReadBySubjectController implements ControllerInterface{

    final private QuestionsRepository questionsRepository;

    public QuestionReadBySubjectController(){
        this.questionsRepository = new QuestionsRepository();
    }

    @Override
    public Response executeCommand(CommandInterface command){
        QuestionReadBySubjectCommand questionReadBySubjectCommand = (QuestionReadBySubjectCommand) command;
        List<Subject> subjectsList = questionReadBySubjectCommand.getSubjectsList();
        List<Subject> subjectsFromRepository = questionsRepository.getQuestionsBySubject(subjectsList);

        Response responseMessage = new Response();
        responseMessage.setDateHandled(new Date());
        responseMessage.setReturnedObject(subjectsFromRepository);
        responseMessage.setStatus("Success");
        responseMessage.setRespondName("ReadBySubject");
        System.out.print("Command handled successfully ");
        return responseMessage;
    }

    @Override
    public String getControllerName() {
        return "QuestionReadBySubjectController";
    }
}

