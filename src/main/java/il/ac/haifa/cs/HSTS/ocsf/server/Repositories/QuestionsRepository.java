package il.ac.haifa.cs.HSTS.ocsf.server.Repositories;

import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Question;
import il.ac.haifa.cs.HSTS.ocsf.server.Services.SessionFactoryGlobal;
import org.hibernate.Criteria;
import org.hibernate.Session;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

// Repository, data manipulation in DB- Create, Read, Update, Delete

public class QuestionsRepository {

    private static Session session;

    /* Methods that have to be added:
     * getAllQuestionsBySubject -Read
     * getQuestionById -Read
     * deleteQuestionById -Delete
     * updateQuestionById -Update */

    // Create
    public void pushQuestion(Question question) {
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);
            /* Insert data here */
            session.save(question);
            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session,exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
        }
    }
    public void updateQuestion(Question question) {
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);
            /* Insert data here */
            session.update(question);
            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session,exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
        }

    }
    public List<Question> getQuestionsBySubject(String subject) {
        List<Question> results = null;
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);
            /* Ask for data here */
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Question> criteriaQuery = builder.createQuery(Question.class);
            Root<Question> root = criteriaQuery.from(Question.class);
            criteriaQuery.select(root).where(builder.like(root.get("subject"),"%"+subject+"%"));

            Query query = session.createQuery(criteriaQuery);
            results = query.getResultList();

            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session,exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
        }
        return results;
    }
    public void deleteQuestion(Question question) {
        try {
            session =  SessionFactoryGlobal.openSessionAndTransaction(session);
            /* Ask for data here */
            session.delete(question);

            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session,exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
        }
    }
    public Question getQuestionById(int id) {
        Question result = null;
        try {
           session =  SessionFactoryGlobal.openSessionAndTransaction(session);
            /* Ask for data here */
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Question> criteriaQuery = builder.createQuery(Question.class);
            Root<Question> root = criteriaQuery.from(Question.class);
            criteriaQuery.select(root).where(builder.equal(root.get("id"),id));

            Query query = session.createQuery(criteriaQuery);
            result = (Question)query.getSingleResult();

            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session,exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
        }
        return result;
    }

    public static <T> List<T> getAll(Class<T> object) {
        TypedQuery<T> allQuery = null;
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = builder.createQuery(object);
            Root<T> rootEntry = criteriaQuery.from(object);
            CriteriaQuery<T> allCriteriaQuery = criteriaQuery.select(rootEntry);
            allQuery = session.createQuery(allCriteriaQuery);

        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session,exception);
        } finally {

             SessionFactoryGlobal.closeTransaction(session);
            SessionFactoryGlobal.closeSession(session);
        }
        return allQuery.getResultList();

    }

    private static <E> void updateEntities(List<? extends E> obj) throws Exception {
        for (E object : obj)
            session.update(object);
    }
}
