package il.ac.haifa.cs.HSTS.server.Repositories;

import il.ac.haifa.cs.HSTS.server.Entities.Question;
import il.ac.haifa.cs.HSTS.server.Entities.Subject;
import il.ac.haifa.cs.HSTS.server.Entities.Test;
import il.ac.haifa.cs.HSTS.server.Services.SessionFactoryGlobal;
import org.hibernate.Session;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class TestsRepository {
    private static Session session;


    public Test updateTest(Test test) {
        Test updatedTest;
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);
            /* Insert data here */
            session.update(test);
            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session,exception);
        } finally {
            updatedTest = getTestById(test.getId());
            System.out.println(updatedTest);
            SessionFactoryGlobal.closeSession(session);
        }

        return updatedTest;
    }

    public List<Test> getTestsBySubject(Subject subject) {
        List<Test> testListFromSubject = new ArrayList<Test>();
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);

            /* Ask for data here */
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Test> criteriaQuery = builder.createQuery(Test.class);
            Root<Test> root = criteriaQuery.from(Test.class);

            criteriaQuery.select(root).where(
                    builder.equal(root.get("subject"), subject));
            Query query = session.createQuery(criteriaQuery);
            testListFromSubject.addAll(query.getResultList());

            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session, exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
        }
        return testListFromSubject;
    }

    public Test getTestById(int id) {
        Test test = null;
        try {
            session =  SessionFactoryGlobal.openSessionAndTransaction(session);
            /* Ask for data here */
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Test> criteriaQuery = builder.createQuery(Test.class);
            Root<Test> root = criteriaQuery.from(Test.class);
            criteriaQuery.select(root).where(builder.equal(root.get("id"),id));

            Query query = session.createQuery(criteriaQuery);
            test = (Test)query.getSingleResult();

            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session,exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
        }
        return test;
    }

    public List<Test> getAll() {
        List<Test> testList = new ArrayList<Test>();
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Test> criteriaQuery = builder.createQuery(Test.class);
            Root<Test> rootEntry = criteriaQuery.from(Test.class);
            CriteriaQuery<Test> allCriteriaQuery = criteriaQuery.select(rootEntry);
            Query allQuery = session.createQuery(allCriteriaQuery);
            testList.addAll(allQuery.getResultList());

            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session,exception);
        } finally {

            SessionFactoryGlobal.closeSession(session);
        }
        System.out.println(testList);
        return testList;
    }
}
