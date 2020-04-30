package il.ac.haifa.cs.HSTS.ocsf.server.Controllers;

import il.ac.haifa.cs.HSTS.Command;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Question;
import il.ac.haifa.cs.HSTS.ocsf.server.Repositories.QuestionsRepository;

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
            case "read" : break;
            case "push" : questionsRepository.pushQuestion((Question)command.getParameter(0)); break;
            case "update" : break;
            case "delete" : break;
            // cases
            default : command.setStatus("Command invalid");
                return command;
        }
        command.notifySuccessfullyHandled();
        return command;
    }

}
