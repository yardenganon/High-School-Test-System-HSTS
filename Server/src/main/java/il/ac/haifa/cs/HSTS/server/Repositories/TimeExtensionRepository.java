package il.ac.haifa.cs.HSTS.server.Repositories;

import il.ac.haifa.cs.HSTS.server.Entities.TimeExtensionRequest;
import il.ac.haifa.cs.HSTS.server.Services.SessionFactoryGlobal;
import il.ac.haifa.cs.HSTS.server.Status.Status;
import org.hibernate.Session;
import org.hibernate.query.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class TimeExtensionRepository {
    private static Session session;

    public TimeExtensionRequest pushTimeExtensionRequest(TimeExtensionRequest timeExtensionRequest){
        TimeExtensionRequest newTimeExtensionRequest;
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);
            session.save(timeExtensionRequest);
            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session,exception);
        } finally {
            newTimeExtensionRequest = getTimeExtensionRequestById(timeExtensionRequest.getId());
            System.out.println(newTimeExtensionRequest);
            SessionFactoryGlobal.closeSession(session);
        }
        return newTimeExtensionRequest;
    }

    public TimeExtensionRequest getTimeExtensionRequestById(int id) {
        TimeExtensionRequest timeExtensionRequest = null;
        try {
            session =  SessionFactoryGlobal.openSessionAndTransaction(session);

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<TimeExtensionRequest> criteriaQuery = builder.createQuery(TimeExtensionRequest.class);
            Root<TimeExtensionRequest> root = criteriaQuery.from(TimeExtensionRequest.class);
            criteriaQuery.select(root).where(builder.equal(root.get("id"),id));

            Query query = session.createQuery(criteriaQuery);
            timeExtensionRequest = (TimeExtensionRequest)query.getSingleResult();

            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session,exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
        }
        return timeExtensionRequest;
    }

    public List<TimeExtensionRequest> getAllTimeExtensions(){
        List<TimeExtensionRequest> timeExtensionRequestList = null;
        try{
            session = SessionFactoryGlobal.openSessionAndTransaction(session);

            Query<TimeExtensionRequest> query = session.createQuery("from il.ac.haifa.cs.HSTS.server.Entities.TimeExtensionRequest m where m.status =: status");
            query.setParameter("status", Status.OpenRequest);
            timeExtensionRequestList = query.list();

            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            if(timeExtensionRequestList == null )
                return timeExtensionRequestList;
            SessionFactoryGlobal.exceptionCaught(session, exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
        }
        return timeExtensionRequestList;
    }

    public TimeExtensionRequest updateTimeExtensionRequest(TimeExtensionRequest timeExtensionRequest) {
        TimeExtensionRequest updatedTimeExtensionRequest = null;
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);
            session.update(timeExtensionRequest);
            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session,exception);
        } finally {
            updatedTimeExtensionRequest = getTimeExtensionRequestById(timeExtensionRequest.getId());
            System.out.println(updatedTimeExtensionRequest);
            SessionFactoryGlobal.closeSession(session);
        }
        return updatedTimeExtensionRequest;
    }

    public TimeExtensionRequest getTimeExtensionRequestByReadyTestId(int readyTestId){
        TimeExtensionRequest timeExtensionRequest = null;
        try{
            session = SessionFactoryGlobal.openSessionAndTransaction(session);

            Query<TimeExtensionRequest> query = session.createQuery("from il.ac.haifa.cs.HSTS.server.Entities.TimeExtensionRequest m where m.status =: status and m.test.id =: testId");
            query.setParameter("status", Status.TimeExtensionRequestApproved).setParameter("testId", readyTestId);
            timeExtensionRequest = query.getSingleResult();

            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            if(timeExtensionRequest == null )
                return timeExtensionRequest;
            SessionFactoryGlobal.exceptionCaught(session, exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
        }
        return timeExtensionRequest;
    }
}
