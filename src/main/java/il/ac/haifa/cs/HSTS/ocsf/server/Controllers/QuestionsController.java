package il.ac.haifa.cs.HSTS.ocsf.server.Controllers;

import il.ac.haifa.cs.HSTS.Command;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Question;
import il.ac.haifa.cs.HSTS.ocsf.server.Repositories.QuestionsRepository;

import java.util.List;

/* Controller - receiving commands from handleMessageFromClient() at HSTS server
 and maps the query via it's repository */

public class QuestionsController {
    private final QuestionsRepository questionsRepository;

    public QuestionsController(QuestionsRepository questionsRepository) {
        this.questionsRepository = questionsRepository;
    }

    public Command QuestionHandler(Command command) {

        // CRUD - Create , Read , Update , Delete
        switch (command.getCommand()) {
            case "readbysubject" : command.setReturnedObject(questionsRepository.getQuestionsBySubject((String)command.getParameter(0)));break;
            case "push" : questionsRepository.pushQuestion((Question)command.getParameter(0));break;
            case "update" :
            case "delete" :
                break;
            // cases
            default : command.setStatus("Command invalid");
                System.out.println("Command invalid: "+command.getCommand());
                return command;
        }
        command.notifySuccessfullyHandled();
        System.out.print("Command handled successfully ");
        command.printCommandDetails();
        return command;
    }

}
