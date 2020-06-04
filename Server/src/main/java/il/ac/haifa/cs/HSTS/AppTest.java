package il.ac.haifa.cs.HSTS;

import il.ac.haifa.cs.HSTS.server.Entities.*;
import il.ac.haifa.cs.HSTS.server.Facade.ReadyTestFacade;
import il.ac.haifa.cs.HSTS.server.Facade.TestFacade;
import il.ac.haifa.cs.HSTS.server.Repositories.GenericQueries;
import il.ac.haifa.cs.HSTS.server.Repositories.TestsRepository;
import il.ac.haifa.cs.HSTS.server.Repositories.UsersRepository;
import il.ac.haifa.cs.HSTS.server.Services.SessionFactoryGlobal;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppTest {
    private static Session session;

    public static void main(String[] args){
            //Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
//            SessionFactory sessionFactory = SessionFactoryGlobal.getSessionFactory();
            //session = SessionFactoryGlobal.openSessionAndTransaction(session);

            TestsRepository testsRepository = new TestsRepository();
            System.out.println(GenericQueries.getById(Question.class,1));
            System.out.println(GenericQueries.getById(Question.class,2));
            System.out.println(GenericQueries.getById(AnswerableTest.class,1));
            System.out.println(GenericQueries.getAll(Question.class));

    }
}
