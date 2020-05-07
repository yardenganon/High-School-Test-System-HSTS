package il.ac.haifa.cs.HSTS.ocsf.server.Controllers;

import il.ac.haifa.cs.HSTS.Command;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Question;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Subject;
import il.ac.haifa.cs.HSTS.ocsf.server.Repositories.QuestionsRepository;
import java.util.List;

/* Controller - receiving commands from handleMessageFromClient() at HSTS server
 and maps the query via repository */

public class QuestionsController {
    private final QuestionsRepository questionsRepository;

    public QuestionsController(QuestionsRepository questionsRepository) {
        this.questionsRepository = questionsRepository;
    }

    public Command QuestionHandler(Command command) {
        switch (command.getCommand()) {
            case "readbysubject":
                Object listOfSubjectsParamater = command.getParameter(0);
                @SuppressWarnings("unchecked cast 'java.lang.Object") //maybe we will delete this in the future
                List<Subject> subjects = (List<Subject>)listOfSubjectsParamater;
                List<Subject> subjectsFromRepository = questionsRepository.getQuestionsBySubject(subjects);
                command.setReturnedObject(subjectsFromRepository);
                break;
            case "readbyid":
                Object questionIdParameter = command.getParameter(0);
                int questionId = (Integer.parseInt(String.valueOf(questionIdParameter)));
                Question question = questionsRepository.getQuestionById(questionId);
                command.setReturnedObject(question);
                break;
            case "push":
                Object questionToPushParameter = command.getParameter(0);
                Question newQuestion = (Question)questionToPushParameter;
                questionsRepository.pushQuestion(newQuestion);
                break;
            case "update":
                Object questionToUpdateParameter = command.getParameter(0);
                Question questionToUpdate = (Question) questionToUpdateParameter;
                questionsRepository.updateQuestion(questionToUpdate);
                break;
            case "delete":
                Object questionToDeleteParameter = command.getParameter(0);
                Question questionToDelete = (Question) questionToDeleteParameter;
                questionsRepository.deleteQuestion(questionToDelete);
            default:
                command.setStatus("Command invalid");
                System.out.println("Command invalid: "+command.getCommand());
                return command;
        }
        command.notifySuccessfullyHandled();
        System.out.print("Command handled successfully ");
        command.printCommandDetails();
        return command;
    }

}
