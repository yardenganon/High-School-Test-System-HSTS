package il.ac.haifa.cs.HSTS.ocsf.server.Repositories;

import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Question;
import il.ac.haifa.cs.HSTS.ocsf.server.Services.SessionFactoryGlobal;
import org.hibernate.Session;

// Repository, data manipulation in DB- Create, Read, Update, Delete

public class QuestionsRepository {

    private static Session session;

    /* Methods that have to be added:
     * getAllQuestionsBySubject -Read
     * getQuestionById -Read
     * deleteQuestionById -Delete
     * updateQuestionById -Update
     */

    // Create
    public void pushQuestion(Question question) {
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);
            /* Insert data here */
            session.save(question);

            session = SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            session = SessionFactoryGlobal.exceptionCaught(session,exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
        }
    }
}
