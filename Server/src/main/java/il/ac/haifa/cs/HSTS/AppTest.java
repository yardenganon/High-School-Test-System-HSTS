package il.ac.haifa.cs.HSTS;

import il.ac.haifa.cs.HSTS.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.server.CommandInterface.RequestAnswerableTestCommand;
import il.ac.haifa.cs.HSTS.server.CommandInterface.Response;
import il.ac.haifa.cs.HSTS.server.CommandInterface.TimeExtensionStatusCommand;
import il.ac.haifa.cs.HSTS.server.Controllers.RequestAnswerableTestController;
import il.ac.haifa.cs.HSTS.server.Controllers.TimeExtensionController;
import il.ac.haifa.cs.HSTS.server.Entities.*;
import il.ac.haifa.cs.HSTS.server.Facade.ReadyTestExtendedFacade;
import il.ac.haifa.cs.HSTS.server.Facade.ReadyTestFacade;
import il.ac.haifa.cs.HSTS.server.Repositories.TestsRepository;
import il.ac.haifa.cs.HSTS.server.Repositories.TimeExtensionRepository;
import il.ac.haifa.cs.HSTS.server.Repositories.UsersRepository;
import il.ac.haifa.cs.HSTS.server.Status.Status;
import org.hibernate.Session;

import java.sql.Time;
import java.util.List;

public class AppTest {
    private static Session session;

    public static void main(String[] args){
        //Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

        UsersRepository usersRepository = new UsersRepository();
        TestsRepository testsRepository = new TestsRepository();
        TimeExtensionRepository timeExtensionRepository = new TimeExtensionRepository();
        TimeExtensionController timeExtensionController = new TimeExtensionController();

        Student student = (Student ) usersRepository.login("ohad_fridman", "1234");

        System.out.println(testsRepository.getAnswerableTestsFacadeByStudentId(student));
    }
}
