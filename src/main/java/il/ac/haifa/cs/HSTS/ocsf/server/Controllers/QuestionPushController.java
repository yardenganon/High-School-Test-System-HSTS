//package il.ac.haifa.cs.HSTS.ocsf.server.Controllers;
//
//import il.ac.haifa.cs.HSTS.ocsf.Respond.Respond;
//import il.ac.haifa.cs.HSTS.ocsf.commandInterface.CommandInterface;
//import il.ac.haifa.cs.HSTS.ocsf.commandInterface.QuestionPushCommand;
//import il.ac.haifa.cs.HSTS.ocsf.commandInterface.QuestionUpdateCommand;
//import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Question;
//import il.ac.haifa.cs.HSTS.ocsf.server.Repositories.QuestionsRepository;
//
//import java.util.Date;
//
//public class QuestionPushController implements ControllerInterface {
//
//    final private QuestionsRepository questionsRepository;
//
//    public QuestionPushController(){
//        this.questionsRepository = new QuestionsRepository();
//    }
//
//    @Override
//    public Respond executeCommand(CommandInterface command) {
//        QuestionPushCommand questionPushCommand = (QuestionPushCommand) command;
//        Question questionToPush = questionPushCommand.getNewQuestion();
//        questionsRepository.updateQuestion(questionToPush);
//
//        Respond respondMessage = new Respond();
//        respondMessage.setDateHandled(new Date());
//        respondMessage.setStatus("Success");
//        System.out.print("Command handled successfully ");
//        return respondMessage;
//    }
//}
