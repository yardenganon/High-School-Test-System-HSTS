package il.ac.haifa.cs.HSTS.ocsf.server.Services;

import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Question;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SessionFactoryGlobal {
    public static SessionFactory getSessionFactory() throws HibernateException {
        Configuration configuration = new Configuration();

        // Add entities here
        configuration.addAnnotatedClass(Question.class);


        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

        return configuration.buildSessionFactory(serviceRegistry);
    }
    public static Session openSessionAndTransaction(Session session) {
        //Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        try {
            SessionFactory sessionFactory = SessionFactoryGlobal.getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction(); // open transaction
            return session;

        } catch (Exception e) {
            System.err.println("An error occurred (openSessionAndTransaction), changes have been rolled back.");
            e.printStackTrace();
        }
        return session;
    }
    public static Session closeTransaction(Session session){
        try {
            session.flush();
            session.getTransaction().commit(); // updating session
        } catch (Exception exception) {
            if (session != null)
                session.getTransaction().rollback();
            System.err.println("An error occurred (closeTransaction), changes have been rolled back.");
            exception.printStackTrace();
        } finally {
            if (session != null) {
                session.close(); // close transaction
                session.getSessionFactory().close();
            }
        }
        return session;
    }
    public static Session exceptionCaught(Session session, Exception exception) {
        if (session != null)
            session.getTransaction().rollback();
        System.err.println("An error occurred (exceptionCaught) " +exception.getMessage() + ", changes have been rolled back.");
        exception.printStackTrace();
        return session;
    }
    public static void closeSession(Session session) {

        try {
            if (session != null) {
                session.close();
                session.getSessionFactory().close();
            }
        } catch (HibernateException e) {
            System.err.println("An error occurred (closeSession), changes have been rolled back.");
            e.printStackTrace();
        }
    }
}
