package il.ac.haifa.cs.HSTS.server.Controllers;

import il.ac.haifa.cs.HSTS.server.CommandInterface.*;
import il.ac.haifa.cs.HSTS.server.Entities.Question;
import il.ac.haifa.cs.HSTS.server.Entities.Subject;
import il.ac.haifa.cs.HSTS.server.Repositories.QuestionsRepository;
import il.ac.haifa.cs.HSTS.server.CommandInterface.Response;
import il.ac.haifa.cs.HSTS.server.Status.Status;

import java.util.Date;
import java.util.List;

public class QuestionController implements ControllerInterface {
    final private QuestionsRepository questionsRepository;

    public QuestionController() {
        this.questionsRepository = new QuestionsRepository();
    }

    @Override
    public Response executeCommand(CommandInterface command) {
        String commandName = command.getCommandName();
        System.out.println(commandName);
        Response responseMessage = new Response(commandName);
        Object returnedObject = null;
        switch (commandName) {
            case ("QuestionReadAllCommand"):
                returnedObject = questionsRepository.getAll();
                break;
            case ("QuestionReadByIdCommand"): // not checked yet
                returnedObject = questionsRepository.getQuestionById(
                        ((QuestionReadByIdCommand) command).getId());
                break;
            case ("QuestionReadBySubjectCommand"):
                List<Subject> subjectsList = ((QuestionReadBySubjectCommand) command).getSubjectsList();
                returnedObject = questionsRepository.getQuestionsBySubject(subjectsList);
                break;
            case ("QuestionUpdateCommand"):
                Question questionToUpdate = ((QuestionUpdateCommand) command).getQuestionToUpdate();
                returnedObject = questionsRepository.updateQuestion(questionToUpdate);
                break;
            case ("QuestionPushCommand"):
                Question newQuestion = ((QuestionPushCommand) command).getNewQuestion();
                returnedObject = questionsRepository.pushQuestion(newQuestion);
            default:
                System.out.println("Error - Command not found in controller");
        }
        responseMessage.setReturnedObject(returnedObject);
        responseMessage.setStatus(Status.Success);
        responseMessage.setDateHandled(new Date());
        return responseMessage;
    }
}
