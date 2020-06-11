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

        Teacher teacher = (Teacher) usersRepository.login("yaffa_hamuza", "1234");
        ReadyTest readyTest = testsRepository.getReadyTestById(1);
        ReadyTest readyTest2 = testsRepository.getReadyTestById(3);

        TimeExtensionRequest ter = new TimeExtensionRequest(teacher, readyTest, "kacha", 100);
        ter = timeExtensionRepository.pushTimeExtensionRequest(ter);

        System.out.println(ter);

        TimeExtensionRequest ter1 = new TimeExtensionRequest(teacher, readyTest2, "lama", 100);
        ter1 = timeExtensionRepository.pushTimeExtensionRequest(ter1);

        System.out.println(ter1);

       // System.out.println("All Time Ex: " + timeExtensionRepository.getAll());

        //List<TimeExtensionRequest> list = timeExtensionRepository.getAll();

       // ter = list.get(0);
        ter.setStatus(Status.PermissionDenied);
        ter = timeExtensionRepository.updateTimeExtensionRequest(ter);
        System.out.println("updated:" + ter);

       // System.out.println("All Time Ex: " + timeExtensionRepository.getAll());

        //System.out.println("---------------Extenden Facade List--------------------------");
        //System.out.println(testsRepository.getReadyTestsFacadeByTeacherId(teacher.getId()));
        //System.out.println(testsRepository.getReadyTestsFacadeByTeacherId(teacher.getId()));
        //System.out.println(testsRepository.getReadyTestsExtendedFacadeByTeacherId(teacher.getId()));
//        List<Object> objectList = testsRepository.getReadyTestsExtendedFacadeByTeacherId(teacher.getId());
//        List<ReadyTestExtendedFacade> readyTestExtendedFacades = (List<ReadyTestExtendedFacade>) objectList.get(0);
//        List<ReadyTestFacade> readyTestFacades = (List<ReadyTestFacade>) objectList.get(1);
//        System.out.println(readyTestExtendedFacades);
//        System.out.println(readyTestFacades);

    }
}
