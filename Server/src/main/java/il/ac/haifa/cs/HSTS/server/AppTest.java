//package il.ac.haifa.cs.HSTS.server;
//
//import il.ac.haifa.cs.HSTS.server.CommandInterface.TestReadByIdCommand;
//import il.ac.haifa.cs.HSTS.server.Entities.*;
//import il.ac.haifa.cs.HSTS.server.Repositories.QuestionsRepository;
//import il.ac.haifa.cs.HSTS.server.Repositories.TestsRepository;
//import il.ac.haifa.cs.HSTS.server.Services.SessionFactoryGlobal;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import il.ac.haifa.cs.HSTS.server.AppResult;
//import org.hibernate.query.Query;
//
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class AppTest {
//    private static Session session;
//
//    public static void main(String[] args){
//        try {
//            Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
//            SessionFactory sessionFactory = SessionFactoryGlobal.getSessionFactory();
//            session = SessionFactoryGlobal.openSessionAndTransaction(session);
//
//            Test test;
//            TestsRepository testsRepository = new TestsRepository();
//            test = testsRepository.getTestById(1);
//            System.out.println(test.getQuestionList());
//
//            Query<AppResult> query = session.createQuery("select new il.ac.haifa.cs.HSTS.server.AppResult(m.id, m.writer, m.subject)"
//                    + " from il.ac.haifa.cs.HSTS.server.Entities.Test m");
//            List<AppResult> results = query.list();
//            AppResult result = results.get(0);
//
//            SessionFactoryGlobal.closeTransaction(session);
//        } catch (Exception exception) {
//            SessionFactoryGlobal.exceptionCaught(session, exception);
//        } finally {
//            SessionFactoryGlobal.closeSession(session);
//        }
//    }
//}