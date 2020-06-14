package il.ac.haifa.cs.HSTS.server.Repositories;

import il.ac.haifa.cs.HSTS.server.Facade.CourseFacade;
import il.ac.haifa.cs.HSTS.server.Facade.TestFacade;
import il.ac.haifa.cs.HSTS.server.Services.SessionFactoryGlobal;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class CourseRepository {
    private static Session session;

    public List<CourseFacade> readAllCoursesFacade() {
        List<CourseFacade> courseFacadeList = null;
        try {
            session = SessionFactoryGlobal.openSessionAndTransaction(session);

            Query<CourseFacade> query = session.createQuery("select new il.ac.haifa.cs.HSTS.server.Facade.CourseFacade(m.id,m.courseName,m.subject.subjectName)"
                    + " from il.ac.haifa.cs.HSTS.server.Entities.Course m");
            courseFacadeList = query.list();

            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session, exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
        }
        return courseFacadeList;
    }
}
