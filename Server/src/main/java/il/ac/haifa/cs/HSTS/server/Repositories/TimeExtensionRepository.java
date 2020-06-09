package il.ac.haifa.cs.HSTS.server.Repositories;

import il.ac.haifa.cs.HSTS.server.Entities.Test;
import il.ac.haifa.cs.HSTS.server.Entities.TimeExtensionRequest;
import il.ac.haifa.cs.HSTS.server.Services.SessionFactoryGlobal;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class TimeExtensionRepository {
    private static Session session;

    public TimeExtensionRequest pushTimeExtensionRequest(TimeExtensionRequest timeExtensionRequest){
        TimeExtensionRequest newTimeExtensionRequest;
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);
            /* Insert data here */
            session.save(timeExtensionRequest);
            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session,exception);
        } finally {
            newTimeExtensionRequest = getTimeExtensionRequestId(timeExtensionRequest.getId());
            System.out.println(newTimeExtensionRequest);
            SessionFactoryGlobal.closeSession(session);
        }
        return newTimeExtensionRequest;
    }

    public TimeExtensionRequest getTimeExtensionRequestId(int id) {
        TimeExtensionRequest timeExtensionRequest = null;
        try {
            session =  SessionFactoryGlobal.openSessionAndTransaction(session);
            /* Ask for data here */
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
}
