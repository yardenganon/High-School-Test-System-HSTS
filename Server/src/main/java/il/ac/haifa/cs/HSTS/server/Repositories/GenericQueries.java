package il.ac.haifa.cs.HSTS.server.Repositories;

import il.ac.haifa.cs.HSTS.server.Services.SessionFactoryGlobal;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class GenericQueries {
    private static Session session;
    public static <T> T getById(Class<T> object, int id) {
        T returnedObject = null;
        TypedQuery<T> query = null;
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = builder.createQuery(object);
            Root<T> rootEntry = criteriaQuery.from(object);
            CriteriaQuery<T> CriteriaQuery = criteriaQuery.select(rootEntry);
            criteriaQuery.select(rootEntry).where(builder.equal(rootEntry.get("id"),id));
            query = session.createQuery(CriteriaQuery);
            returnedObject = query.getSingleResult();
            session = SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session,exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
        }
        return returnedObject;
    }

    public static <T> List<T> getAll(Class<T> object) {
        TypedQuery<T> allQuery = null;
        List<T> returnedList = null;
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = builder.createQuery(object);
            Root<T> rootEntry = criteriaQuery.from(object);
            CriteriaQuery<T> allCriteriaQuery = criteriaQuery.select(rootEntry);

            allQuery = session.createQuery(allCriteriaQuery);
            returnedList = allQuery.getResultList();
            session = SessionFactoryGlobal.closeTransaction(session);

        } catch (Exception exception) {
            session = SessionFactoryGlobal.exceptionCaught(session,exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
        }
        return returnedList;
    }
}
