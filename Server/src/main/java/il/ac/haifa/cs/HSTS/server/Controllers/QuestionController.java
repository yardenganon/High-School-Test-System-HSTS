package il.ac.haifa.cs.HSTS.server.Controllers;
import il.ac.haifa.cs.HSTS.server.CommandInterface.*;
import il.ac.haifa.cs.HSTS.server.Entities.Question;
import il.ac.haifa.cs.HSTS.server.Entities.Subject;
import il.ac.haifa.cs.HSTS.server.Repositories.QuestionsRepository;
import il.ac.haifa.cs.HSTS.server.CommandInterface.Response;

import java.util.Date;
import java.util.List;

public class QuestionController implements ControllerInterface {
    final private QuestionsRepository questionsRepository;

    public QuestionController(){
        this.questionsRepository = new QuestionsRepository();
    }

    @Override
    public Response executeCommand(CommandInterface command){
        String commandClass = command.getCommandName();
        String responseName = null;
        Object returnedObject = null;
        switch (commandClass) {
            case ("QuestionReadAllCommand") :
                returnedObject = questionsRepository.getAll();
                responseName = "ReadAllQuestions"; break;
            case ("QuestionReadByIdCommand") : // not checked yet
                returnedObject = questionsRepository.getQuestionById(
                        ((QuestionReadByIdCommand)command).getId());
                responseName = "ReadById"; break;
            case ("QuestionReadBySubjectCommand") :
                List<Subject> subjectsList = ((QuestionReadBySubjectCommand) command).getSubjectsList();
                returnedObject = questionsRepository.getQuestionsBySubject(subjectsList);
                responseName = "ReadBySubject"; break;
            case ("QuestionUpdateCommand") :
                Question questionToUpdate = ((QuestionUpdateCommand) command).getQuestionToUpdate();
                returnedObject = questionsRepository.updateQuestion(questionToUpdate);
                responseName ="UpdateQuestion"; break;
            default:
                System.out.println("Error - Command not found in controller");
        }
        Response responseMessage = new Response();
        responseMessage.setReturnedObject(returnedObject);
        responseMessage.setDateHandled(new Date());
        responseMessage.setStatus("Success");
        responseMessage.setRespondName(responseName);
        System.out.print("Command handled successfully ");
        return responseMessage;
    }

    @Override
    public String getControllerName() {
        return "QuestionReadAllController";
    }
}
