package il.ac.haifa.cs.HSTS.server.Repositories;

import il.ac.haifa.cs.HSTS.server.Entities.*;
import il.ac.haifa.cs.HSTS.server.Facade.AnswerableTestFacade;
import il.ac.haifa.cs.HSTS.server.Facade.ReadyTestExtendedFacade;
import il.ac.haifa.cs.HSTS.server.Facade.ReadyTestFacade;
import il.ac.haifa.cs.HSTS.server.Facade.TestFacade;
import il.ac.haifa.cs.HSTS.server.Services.SessionFactoryGlobal;
import il.ac.haifa.cs.HSTS.server.Status.Status;
import org.hibernate.Session;
import org.hibernate.query.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class TestsRepository {
    private static Session session;

    /* ----------------------------------------------Test-------------------------------------------- */
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

    public List<TestFacade> getTestsFacadeBySubject(String subject) {
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
    /* ----------------------------------------------ReadyTest-------------------------------------------- */
    public ReadyTest getReadyTestById(int id) {
        ReadyTest readyTest = null;
        try {
            session =  SessionFactoryGlobal.openSessionAndTransaction(session);
            /* Ask for data here */
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<ReadyTest> criteriaQuery = builder.createQuery(ReadyTest.class);
            Root<ReadyTest> root = criteriaQuery.from(ReadyTest.class);
            criteriaQuery.select(root).where(builder.equal(root.get("id"),id));

            Query query = session.createQuery(criteriaQuery);
            readyTest = (ReadyTest)query.getSingleResult();

            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session,exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
        }
        return readyTest;
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

    public ReadyTest updateReadyTestActivity(int readyTestId, Boolean status){
        ReadyTest updatedReadyTest = null;
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);
            Query<ReadyTest> query = session.createQuery("update il.ac.haifa.cs.HSTS.server.Entities.ReadyTest set isActive =: status where id =: testId");
            query.setParameter("status", status).setParameter("testId", readyTestId);
            query.executeUpdate();

            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session, exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
            updatedReadyTest = getReadyTestById(readyTestId);
        }
        return updatedReadyTest;
    }

    public ReadyTestFacade getReadyTestsFacadeByExecutionCode(String readyTestCode){
        ReadyTestFacade readyTest = null;
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);

            Query<ReadyTestFacade> query = session.createQuery("select new il.ac.haifa.cs.HSTS.server.Facade.ReadyTestFacade(m.id,m.modifierWriter.username,m.course.courseName,m.dateCreated,m.modifiedTime,m.isManual,m.isActive,m.code)"
                    + " from il.ac.haifa.cs.HSTS.server.Entities.ReadyTest m where m.code= :testCode");
            query.setParameter("testCode", readyTestCode);
            readyTest = query.getSingleResult();

            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            if(readyTest == null )
                return readyTest;
            SessionFactoryGlobal.exceptionCaught(session, exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
        }
        return readyTest;
    }

    public List<ReadyTestExtendedFacade> getReadyTestsFacadeByTeacherId(int id){
        List<ReadyTestExtendedFacade> readyTests = null;
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);

            Query<ReadyTestExtendedFacade> query = session.createQuery("select new il.ac.haifa.cs.HSTS.server.Facade.ReadyTestExtendedFacade(m.id,m.modifierWriter.username,m.course.courseName,m.dateCreated,m.modifiedTime,m.isManual,m.isActive,m.code,m.timeExtensionRequest.timeToAdd,m.timeExtensionRequest.description,m.timeExtensionRequest.status)"
                    + " from il.ac.haifa.cs.HSTS.server.Entities.ReadyTest m where m.modifierWriter.id= :teacherId");
            query.setParameter("teacherId", id);
            readyTests = query.list();

            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session, exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
        }
        System.out.println("Ready test is:" + readyTests);
        return readyTests;
    }
    /* ----------------------------------------------AnswerableTest-------------------------------------------- */
    public AnswerableTest getAnswerableTestByStudent(Student student, ReadyTestFacade readyTestFacade){
        AnswerableTest answerableTest = null;
        try{
            session = SessionFactoryGlobal.openSessionAndTransaction(session);

            Query<AnswerableTest> query = session.createQuery("from il.ac.haifa.cs.HSTS.server.Entities.AnswerableTest m where m.test.code =: code and m.student.id =: studentId");
            query.setParameter("studentId", student.getId()).setParameter("code", readyTestFacade.getCode());
            answerableTest = query.getSingleResult();

            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            if(answerableTest == null )
                return answerableTest;
            SessionFactoryGlobal.exceptionCaught(session, exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
        }
        return answerableTest;
    }

    public AnswerableTest updateAnswerableTest(AnswerableTest test) {
        AnswerableTest updatedAnswerableTest = null;
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);
            /* Insert data here */
            session.update(test);
            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session,exception);
        } finally {
            updatedAnswerableTest = getAnswerableTestById(test.getId());
            System.out.println(updatedAnswerableTest);
            SessionFactoryGlobal.closeSession(session);
        }
        return updatedAnswerableTest;
    }

    public AnswerableTest getAnswerableTestById(int id){
        AnswerableTest answerableTest = null;
        try {
            session =  SessionFactoryGlobal.openSessionAndTransaction(session);
            /* Ask for data here */
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<AnswerableTest> criteriaQuery = builder.createQuery(AnswerableTest.class);
            Root<AnswerableTest> root = criteriaQuery.from(AnswerableTest.class);
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

    public AnswerableTest pushAnswerableTest(AnswerableTest test) {
        AnswerableTest newAnserableTest = null;
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);
            /* Insert data here */
            session.save(test);
            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session,exception);
        } finally {
            newAnserableTest = getAnswerableTestById(test.getId());
            SessionFactoryGlobal.closeSession(session);
        }
        return newAnserableTest;
    }

    public List<AnswerableTestFacade> updateAnswerableTestById(int id, Teacher teacher){
        List<AnswerableTestFacade> answerableTestsFacade = null;
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);
            Query<AnswerableTestFacade> query = session.createQuery("update il.ac.haifa.cs.HSTS.server.Entities.AnswerableTest set isChecked =: true where id =: testId");
            query.setParameter("true", true).setParameter("testId", id);
            query.executeUpdate();

            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session, exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
            answerableTestsFacade = getAnswerableTestsFacade(teacher);
        }
        return answerableTestsFacade;
    }

    public List<AnswerableTestFacade> getAnswerableTestsFacade(Teacher teacher){
        List<AnswerableTestFacade> answerableTestsFacade = null;
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);
            Query<AnswerableTestFacade> query = session.createQuery("select new il.ac.haifa.cs.HSTS.server.Facade.AnswerableTestFacade(m.id, m.score, m.test.course.courseName, m.student.first_name, m.student.last_name)"
                    + "from il.ac.haifa.cs.HSTS.server.Entities.AnswerableTest m where m.answerableTestStatus =: notActive and m.test.modifierWriter.id =: teacherId and m.isChecked =: false2");
            query.setParameter("notActive",Status.TestFinished).setParameter("teacherId", teacher.getId()).setParameter("false2", false);
            answerableTestsFacade = query.list();

            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session, exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
        }
        return answerableTestsFacade;
    }

    //should be deleted, there is a generic class of sessions
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
