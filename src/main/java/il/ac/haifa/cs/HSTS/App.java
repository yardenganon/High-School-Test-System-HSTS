package il.ac.haifa.cs.HSTS;

import il.ac.haifa.cs.HSTS.ocsf.server.Entities.*;
import il.ac.haifa.cs.HSTS.ocsf.server.Repositories.QuestionsRepository;
import il.ac.haifa.cs.HSTS.ocsf.server.Services.SessionFactoryGlobal;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/* Try to avoid using this test file
 Run this file to create data in DB manually */

public class App {

    private static Session session;

    public static void main(String[] args) {

//        QuestionsRepository questionsRepository = new QuestionsRepository();
//        Question q = questionsRepository.getQuestionById(2);
//        System.out.println(q);
//        q.setAnswer(1,"g");
//        questionsRepository.updateQuestion(q);
//        Question q2 = questionsRepository.getQuestionById(2);
//        System.out.println(q2);
//        questionsRepository.deleteQuestion(q2);

//        List<Question> list = questionsRepository.getQuestionsBySubject("Math");
//        for (Question question : list)
//            System.out.println(question.toString());
        try {
            Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
            SessionFactory sessionFactory = SessionFactoryGlobal.getSessionFactory();
            session = SessionFactoryGlobal.openSessionAndTransaction(session);

            /* Insert data here */
//            Teacher teacher = new Teacher("Yaffa_Hamuza","1234","yaffa@gmail.com","Yaffa","Hamuza","female");
//            session.save(teacher);
//            Subject subject = new Subject("Mathematics");
//            subject.addTeacher(teacher);
//            session.save(subject);
//            Question question1 = new Question("Solve that equation: X+5=10","3","2","5","4.5",3,teacher,subject);
//            Question question2 = new Question("Solve that equation: X+7=17","3","2","10","4.5",3,teacher,subject);
//            session.save(question1);
//            session.save(question2);
//
//            Teacher teacher2 = new Teacher("Joel_Nakaka","1234","ynak@gmail.com","Joel","Nakaka","male");
//            session.save(teacher2);
//            Subject subject2 = new Subject("Science");
//            subject2.addTeacher(teacher2);
//            session.save(subject2);
//            Question question3 = new Question("Which is bigger planet?","Earth","Sun","Earth-moon","Venus",2,teacher2,subject2);
//            session.save(question3);


            QuestionsRepository questionsRepository= new QuestionsRepository();

            Principle principle = new Principle("nitza_patiti","1234","nitza_patiti@gmail.com",
                    "Nitza","Patiti","Female");
            session.save(principle);

//            List<Subject> subjectList = new ArrayList<Subject>();
//            Subject subject = new Subject("Mathematics");
//            Subject subject2 = new Subject("Science");
//            subjectList.add(subject);
//            subjectList.add(subject2);
//            List<Subject> questionList = questionsRepository.getQuestionsBySubject(subjectList);
//            System.out.println(questionList);

            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session, exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
        }
    }
}
