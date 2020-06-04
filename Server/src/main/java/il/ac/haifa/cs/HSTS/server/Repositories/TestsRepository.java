package il.ac.haifa.cs.HSTS.server.Repositories;

import il.ac.haifa.cs.HSTS.server.Entities.AnswerableTest;
import il.ac.haifa.cs.HSTS.server.Entities.ReadyTest;
import il.ac.haifa.cs.HSTS.server.Entities.Student;
import il.ac.haifa.cs.HSTS.server.Facade.ReadyTestFacade;
import il.ac.haifa.cs.HSTS.server.Facade.TestFacade;
import il.ac.haifa.cs.HSTS.server.Entities.Test;
import il.ac.haifa.cs.HSTS.server.Services.SessionFactoryGlobal;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class TestsRepository {
    private static Session session;

    public AnswerableTest getAnwerableTestByStudent(Student student){
        AnswerableTest answerableTest = null;
        int studentGeneratedId = student.getIdNumber();
        try{
            session = SessionFactoryGlobal.openSessionAndTransaction(session);

            NativeQuery query = session.createSQLQuery("select*from answerableteset where student_id = :studentId");
            query.setParameter("studentId", studentGeneratedId);
            answerableTest = (AnswerableTest) query.getSingleResult();

            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception){
            SessionFactoryGlobal.exceptionCaught(session, exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
        }
        return answerableTest;
    }

    public ReadyTestFacade getReadyTestsByCode(String readyTestCode){
        ReadyTestFacade readyTest = null;
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);

            Query<ReadyTestFacade> query = session.createQuery("select new il.ac.haifa.cs.HSTS.server.Facade.ReadyTestFacade(m.id,m.modifierWriter.username,m.course.courseName,m.dateCreated,m.modifiedTime,m.isManual,m.isActive,m.code)"
                    + " from il.ac.haifa.cs.HSTS.server.Entities.ReadyTest m where m.code= :testCode");
            query.setParameter("testCode", readyTestCode);
            readyTest = query.getSingleResult();

            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session, exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
        }
        return readyTest;
    }

    public List<ReadyTestFacade> getReadyTestsByCode(int id){
        List<ReadyTestFacade> readyTests = null;
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);

            Query<ReadyTestFacade> query = session.createQuery("select new il.ac.haifa.cs.HSTS.server.Facade.ReadyTestFacade(m.id,m.modifierWriter.username,m.course.courseName,m.dateCreated,m.modifiedTime,m.isManual,m.isActive,m.code)"
                    + " from il.ac.haifa.cs.HSTS.server.Entities.ReadyTest m where m.modifierWriter.id= :teacherId");
            query.setParameter("teacherId", id);
            readyTests = query.list();

            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session, exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
        }
            return readyTests;
    }

    public ReadyTest getReadyTestById(int id) {
        ReadyTest test = null;
        try {
            session =  SessionFactoryGlobal.openSessionAndTransaction(session);
            /* Ask for data here */
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<ReadyTest> criteriaQuery = builder.createQuery(ReadyTest.class);
            Root<ReadyTest> root = criteriaQuery.from(ReadyTest.class);
            criteriaQuery.select(root).where(builder.equal(root.get("id"),id));

            Query query = session.createQuery(criteriaQuery);
            test = (ReadyTest)query.getSingleResult();

            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session,exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
        }
        return test;
    }

    public ReadyTest pushReadyTest(ReadyTest test) {
        ReadyTest newReadyTest = null;
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);
            /* Insert data here */
            session.save(test);
            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session,exception);
        } finally {
            newReadyTest = getReadyTestById(test.getId());
            System.out.println(newReadyTest);
            SessionFactoryGlobal.closeSession(session);
        }
        return newReadyTest;
    }

    public Test pushTest(Test test) {
        Test newTest;
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);
            /* Insert data here */
            session.save(test);
            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session,exception);
        } finally {
            newTest = getTestById(test.getId());
            System.out.println(newTest);
            SessionFactoryGlobal.closeSession(session);
        }
        return newTest;
    }

    public AnswerableTest getAnserableTestById(int id){
        AnswerableTest answerableTest = null;
        try {
            session =  SessionFactoryGlobal.openSessionAndTransaction(session);
            /* Ask for data here */
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Test> criteriaQuery = builder.createQuery(Test.class);
            Root<Test> root = criteriaQuery.from(Test.class);
            criteriaQuery.select(root).where(builder.equal(root.get("id"),id));

            Query query = session.createQuery(criteriaQuery);
            answerableTest = (AnswerableTest)query.getSingleResult();

            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session,exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
        }
        return answerableTest;
    }

    public void pushAnserableTest(AnswerableTest test) {
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);
            /* Insert data here */
            session.save(test);
            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session,exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
        }
    }

    public List<TestFacade> getTestsBySubject(String subject) {
        List<TestFacade> results = null;
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);

            Query<TestFacade> query = session.createQuery("select new il.ac.haifa.cs.HSTS.server.Facade.TestFacade(m.id,m.writer.username,m.subject.subjectName,m.dateCreated,m.questionList.size,m.time)"
                    + " from il.ac.haifa.cs.HSTS.server.Entities.Test m where m.subject.subjectName= :sub");
            query.setParameter("sub",subject);
            results = query.list();

            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session, exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
        }
        return results;
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
