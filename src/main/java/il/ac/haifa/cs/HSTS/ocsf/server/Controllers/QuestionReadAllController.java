package il.ac.haifa.cs.HSTS.ocsf.server.Controllers;
import il.ac.haifa.cs.HSTS.ocsf.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Question;
import il.ac.haifa.cs.HSTS.ocsf.server.Repositories.QuestionsRepository;
import il.ac.haifa.cs.HSTS.ocsf.server.Services.Response;

import java.util.Date;
import java.util.List;

public class QuestionReadAllController implements ControllerInterface {
    final private QuestionsRepository questionsRepository;

    public QuestionReadAllController(){
        this.questionsRepository = new QuestionsRepository();
    }

    @Override
    public Response executeCommand(CommandInterface command){
        List<Question> questionsFromRepository = questionsRepository.getAll();

        Response responseMessage = new Response();
        responseMessage.setDateHandled(new Date());
        responseMessage.setReturnedObject(questionsFromRepository);
        responseMessage.setStatus("Success");
        responseMessage.setRespondName("ReadAllQuestions");
        System.out.print("Command handled successfully ");
        return responseMessage;
    }

    @Override
    public String getControllerName() {
        return "QuestionReadAllController";
    }
}
