package il.ac.haifa.cs.HSTS.ocsf.server.Repositories;

import il.ac.haifa.cs.HSTS.ocsf.server.Entities.User;
import il.ac.haifa.cs.HSTS.ocsf.server.Services.SessionFactoryGlobal;
import org.hibernate.Session;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class UserRepository {

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

    public User getUser(String username, String password){
        User resultUser = null;
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);
            /* Ask for data here */
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.select(root).where(builder.like(root.get("username"),"%"+username+"%"));

            Query query = session.createQuery(criteriaQuery);
            resultUser = (User)query.getSingleResult();
            if(!resultUser.getPassword().equals(password))
                return resultUser;
            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session,exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
        }
        return resultUser;
    }


}
