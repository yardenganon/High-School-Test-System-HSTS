package il.ac.haifa.cs.HSTS;

import il.ac.haifa.cs.HSTS.server.Entities.*;
import il.ac.haifa.cs.HSTS.server.Facade.ReadyTestFacade;
import il.ac.haifa.cs.HSTS.server.Facade.TestFacade;
import il.ac.haifa.cs.HSTS.server.Repositories.TestsRepository;
import il.ac.haifa.cs.HSTS.server.Services.SessionFactoryGlobal;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppTest {
    private static Session session;

    public static void main(String[] args){
        try {
            Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
            SessionFactory sessionFactory = SessionFactoryGlobal.getSessionFactory();
            session = SessionFactoryGlobal.openSessionAndTransaction(session);

            ReadyTest readyTest;
            TestsRepository testsRepository = new TestsRepository();
//            test = testsRepository.getTestById(1);
//            System.out.println(test.getQuestionList());

            List<ReadyTestFacade> results = testsRepository.getReadyTestsByTeacher(22);
            System.out.println(results);

            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session, exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
        }
    }
}
