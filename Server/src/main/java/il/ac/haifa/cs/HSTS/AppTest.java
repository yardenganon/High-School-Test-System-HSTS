package il.ac.haifa.cs.HSTS;

import il.ac.haifa.cs.HSTS.server.Controllers.TimeExtensionController;
import il.ac.haifa.cs.HSTS.server.Entities.ReadyTest;
import il.ac.haifa.cs.HSTS.server.Entities.Teacher;
import il.ac.haifa.cs.HSTS.server.Entities.TimeExtensionRequest;
import il.ac.haifa.cs.HSTS.server.Repositories.TestsRepository;
import il.ac.haifa.cs.HSTS.server.Repositories.TimeExtensionRepository;
import il.ac.haifa.cs.HSTS.server.Repositories.UsersRepository;
import org.hibernate.Session;

import java.util.List;

public class AppTest {
    private static Session session;

    public static void main(String[] args){
        //Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

        UsersRepository usersRepository = new UsersRepository();
        TestsRepository testsRepository = new TestsRepository();
        TimeExtensionRepository timeExtensionRepository = new TimeExtensionRepository();
        TimeExtensionController timeExtensionController = new TimeExtensionController();
        List<TimeExtensionRequest> extensionRequestList = timeExtensionRepository.getAllTimeExtensions();

        Teacher teacher = (Teacher)usersRepository.login("yaffa_hamuza", "1234");
        ReadyTest readyTest1 = testsRepository.getReadyTestById(1);
        ReadyTest readyTest2 = testsRepository.getReadyTestById(2);
        ReadyTest readyTest3 = testsRepository.getReadyTestById(3);


        extensionRequestList.get(0).setInitiator(teacher);
        extensionRequestList.get(0).setTest(readyTest1);

        extensionRequestList.get(1).setInitiator(teacher);
        extensionRequestList.get(1).setTest(readyTest2);

        extensionRequestList.get(2).setInitiator(teacher);
        extensionRequestList.get(2).setTest(readyTest3);

        timeExtensionRepository.pushTimeExtensionRequest(extensionRequestList.get(0));
        timeExtensionRepository.pushTimeExtensionRequest(extensionRequestList.get(1));
        timeExtensionRepository.pushTimeExtensionRequest(extensionRequestList.get(2));
    }
}
