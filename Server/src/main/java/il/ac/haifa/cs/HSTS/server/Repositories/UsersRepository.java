package il.ac.haifa.cs.HSTS.server.Repositories;

import il.ac.haifa.cs.HSTS.server.Entities.*;
import il.ac.haifa.cs.HSTS.server.Facade.ReadyTestFacade;
import il.ac.haifa.cs.HSTS.server.Services.SessionFactoryGlobal;
import org.hibernate.Session;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.logging.Logger;

public class UsersRepository {

    private static Session session;

    public void pushUser(User user) {
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);
            /* Insert data here */
            session.save(user);
            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session,exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
        }
    }

    public User login(String username, String password){
        User resultUser = null;
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);
            /* Ask for data here */
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.select(root).where(
                    builder.equal(root.get("username"),username),
                    builder.equal(root.get("password"),password));
            Query query = session.createQuery(criteriaQuery);
            List res = query.getResultList();
            Object object = null;
            if (res.size()>0) {
                object = query.getResultList().get(0);
                resultUser = (User) object;
            }
            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session,exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
        }
        return resultUser;
    }

//    public Boolean checkIfstudentInCourse(Student student, String courseName){
//        Boolean result = null;
//        List<Course> coursesList;
//        try{
//            session = SessionFactoryGlobal.openSessionAndTransaction(session);
//
//            Query<Course> query = session.createQuery("select from il.ac.haifa.cs.HSTS.server.Entities.Student m where m.courses");
//            coursesList = query.list();
//
//        } catch (Exception exception){
//            SessionFactoryGlobal.exceptionCaught(session, exception);
//        } finally {
//            SessionFactoryGlobal.closeSession(session);
//        }
//        return result;
//    }

    public List<Subject> getSubjectsListByUsername(String username){
        User resultTeacher = null;
        List<Subject> subjectList = null;
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);
            /* Ask for data here */
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Teacher> criteriaQuery = builder.createQuery(Teacher.class);
            Root<Teacher> root = criteriaQuery.from(Teacher.class);
            criteriaQuery.select(root).where(
                    builder.equal(root.get("username"),username));
            Query query = session.createQuery(criteriaQuery);
            resultTeacher = (Teacher) query.getResultList().get(0);
            if (resultTeacher!=null)
                subjectList =((Teacher)resultTeacher).getSubjects();
            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session,exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
        }
        return subjectList;
    }
}
