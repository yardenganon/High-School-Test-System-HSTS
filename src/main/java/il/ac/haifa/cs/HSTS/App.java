package il.ac.haifa.cs.HSTS;

import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Question;
import il.ac.haifa.cs.HSTS.ocsf.server.Repositories.QuestionsRepository;
import il.ac.haifa.cs.HSTS.ocsf.server.Services.SessionFactoryGlobal;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/* Try to avoid using this file
 Run this file to create data in DB manually */

public class App {

    private static Session session;

    public static void main(String[] args) {

//        QuestionsRepository questionsRepository = new QuestionsRepository();
//        List<Question> list = questionsRepository.getQuestionsBySubject("Math");
//        for (Question question : list)
//            System.out.println(question.toString());


//        try {
//            Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
//            SessionFactory sessionFactory = SessionFactoryGlobal.getSessionFactory();
//            session = SessionFactoryGlobal.openSessionAndTransaction(session);
//
//            /* Insert data here */
//            SessionFactoryGlobal.closeTransaction(session);
//        } catch (Exception exception) {
//            SessionFactoryGlobal.exceptionCaught(session, exception);
//        } finally {
//            SessionFactoryGlobal.closeSession(session);
//        }
    }
}
