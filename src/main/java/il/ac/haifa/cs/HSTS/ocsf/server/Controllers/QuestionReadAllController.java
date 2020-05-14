package il.ac.haifa.cs.HSTS.ocsf.server.Controllers;

import il.ac.haifa.cs.HSTS.ocsf.Respond.Respond;
import il.ac.haifa.cs.HSTS.ocsf.commandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.ocsf.commandInterface.QuestionReadBySubjectCommand;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Question;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Subject;
import il.ac.haifa.cs.HSTS.ocsf.server.Repositories.QuestionsRepository;

import java.util.Date;
import java.util.List;

public class QuestionReadAllController implements ControllerInterface {
    final private QuestionsRepository questionsRepository;

    public QuestionReadAllController(){
        this.questionsRepository = new QuestionsRepository();
    }

    @Override
    public Respond executeCommand(CommandInterface command){
        List<Question> questionsFromRepository = questionsRepository.getAll();

        Respond respondMessage = new Respond();
        respondMessage.setDateHandled(new Date());
        respondMessage.setReturnedObject(questionsFromRepository);
        respondMessage.setStatus("Success");
        respondMessage.setRespondName("ReadAllQuestions");
        System.out.print("Command handled successfully ");
        return respondMessage;
    }

    @Override
    public String getControllerName() {
        return "QuestionReadAllController";
    }
}
