package il.ac.haifa.cs.HSTS.ocsf.server.Repositories;

import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Question;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Subject;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Teacher;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.User;
import il.ac.haifa.cs.HSTS.ocsf.server.Services.SessionFactoryGlobal;
import org.hibernate.Criteria;
import org.hibernate.Session;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

// Repository, data manipulation in DB- Create, Read, Update, Delete

public class QuestionsRepository {

    private static Session session;

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

    public Question updateQuestion(Question question) {
        Question question1;
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);
            /* Insert data here */
            session.update(question);
            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session,exception);
        } finally {
            question1 = getQuestionById(question.getId());
            System.out.println(question1);
            SessionFactoryGlobal.closeSession(session);
        }

        return question1;
    }

     public List<Subject> getQuestionsBySubject(List<Subject> subjects) {
        List<Subject> results = new ArrayList<Subject>();
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);

            /* Ask for data here */
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Subject> criteriaQuery = builder.createQuery(Subject.class);
            Root<Subject> root = criteriaQuery.from(Subject.class);

            for (int i = 0;i< subjects.size();i++) {
                String subject = subjects.get(i).getSubjectName();
                criteriaQuery.select(root).where(
                        builder.equal(root.get("subjectName"),subject));
                Query query = session.createQuery(criteriaQuery);
                results.addAll(query.getResultList());
            }
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
