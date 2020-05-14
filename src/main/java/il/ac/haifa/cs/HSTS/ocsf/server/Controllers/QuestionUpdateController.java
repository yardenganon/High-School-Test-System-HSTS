package il.ac.haifa.cs.HSTS.ocsf.server.Controllers;

import il.ac.haifa.cs.HSTS.ocsf.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.ocsf.server.CommandInterface.QuestionUpdateCommand;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Question;
import il.ac.haifa.cs.HSTS.ocsf.server.Repositories.QuestionsRepository;
import il.ac.haifa.cs.HSTS.ocsf.server.Services.Respond;

import java.util.Date;

public class QuestionUpdateController implements ControllerInterface {

    final private QuestionsRepository questionsRepository;

    public QuestionUpdateController(){
        this.questionsRepository = new QuestionsRepository();
    }

    @Override
    public Respond executeCommand(CommandInterface command) {
        System.out.println("Execute Command - Update Question");
        QuestionUpdateCommand questionUpdateCommand = (QuestionUpdateCommand) command;
        Question questionToUpdate = questionUpdateCommand.getQuestionToUpdate();
        Question updatedQuestion = questionsRepository.updateQuestion(questionToUpdate);

        Respond respondMessage = new Respond();
        respondMessage.setDateHandled(new Date());
        respondMessage.setReturnedObject(updatedQuestion);
        respondMessage.setStatus("Success");
        respondMessage.setRespondName("UpdateQuestion");
        System.out.println("Command handled successfully ");
        System.out.println("Return respond");
        return respondMessage;
    }

    @Override
    public String getControllerName() {
        return "QuestionUpdateController";
    }
}
