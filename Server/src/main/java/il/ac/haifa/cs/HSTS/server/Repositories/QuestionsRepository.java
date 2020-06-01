package il.ac.haifa.cs.HSTS.server.Repositories;

import il.ac.haifa.cs.HSTS.server.Entities.Question;
import il.ac.haifa.cs.HSTS.server.Entities.Subject;
import il.ac.haifa.cs.HSTS.server.Services.SessionFactoryGlobal;
import org.hibernate.Session;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

// Repository, data manipulation in DB- Create, Read, Update, Delete

public class QuestionsRepository {

    private static Session session;

    // Create
    public Question pushQuestion(Question question) {
        Question returendQuestion;
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);
            /* Insert data here */
            session.save(question);
            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session,exception);
        } finally {
            returendQuestion = getQuestionById(question.getId());
            SessionFactoryGlobal.closeSession(session);
        }
        return returendQuestion;
    }

    public Question updateQuestion(Question question) {
        Question returendQuestion;
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);
            /* Insert data here */
            session.update(question);
            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session,exception);
        } finally {
            returendQuestion = getQuestionById(question.getId());
            SessionFactoryGlobal.closeSession(session);
        }
        return returendQuestion;
    }

     public List<Question> getQuestionsBySubject(List<Subject> subjects) {
        List<Question> results = new ArrayList<Question>();
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);

            /* Ask for data here */
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Question> criteriaQuery = builder.createQuery(Question.class);
            Root<Question> root = criteriaQuery.from(Question.class);

            for (Subject sub: subjects) {
                criteriaQuery.select(root).where(
                        builder.equal(root.get("subject"),sub));
                Query query = session.createQuery(criteriaQuery);
                results.addAll(query.getResultList());
            }
            System.out.println(results);
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

    public List<Question> getAll() {
        List<Question> list = new ArrayList<Question>();
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Question> criteriaQuery = builder.createQuery(Question.class);
            Root<Question> rootEntry = criteriaQuery.from(Question.class);
            CriteriaQuery<Question> allCriteriaQuery = criteriaQuery.select(rootEntry);
            Query allQuery = session.createQuery(allCriteriaQuery);
            list.addAll(allQuery.getResultList());

            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session,exception);
        } finally {

            SessionFactoryGlobal.closeSession(session);
        }
        System.out.println(list);
        return list;
    }

    private static <E> void updateEntities(List<? extends E> obj) throws Exception {
        for (E object : obj)
            session.update(object);
    }
}
