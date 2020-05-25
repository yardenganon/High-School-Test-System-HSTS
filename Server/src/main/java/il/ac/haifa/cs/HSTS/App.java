package il.ac.haifa.cs.HSTS;

import il.ac.haifa.cs.HSTS.server.Entities.*;
import il.ac.haifa.cs.HSTS.server.Repositories.QuestionsRepository;
import il.ac.haifa.cs.HSTS.server.Services.SessionFactoryGlobal;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.logging.Level;
import java.util.logging.Logger;


/* Try to avoid using this test file
 Run this file to create data in DB manually */

public class App {

    private static Session session;

    public static void main(String[] args) {

        QuestionsRepository questionsRepository = new QuestionsRepository();
        /*Question q = questionsRepository.getQuestionById(2);
        System.out.println(q);
        q.setAnswer(1,"g");
        questionsRepository.updateQuestion(q);
        Question q2 = questionsRepository.getQuestionById(2);
        System.out.println(q2);
        questionsRepository.deleteQuestion(q2);*/

        /*List<Question> list = questionsRepository.getQuestionsBySubject("Math");
        for (Question question : list)
            System.out.println(question.toString());*/
        try {
            Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
            SessionFactory sessionFactory = SessionFactoryGlobal.getSessionFactory();
            session = SessionFactoryGlobal.openSessionAndTransaction(session);

//            /* Insert data here */
//            Teacher teacher = new Teacher("Yaffa_Hamuza","1234","yaffa@gmail.com","Yaffa","Hamuza","female");
//            session.save(teacher);
//            Subject subject = new Subject("Mathematics");
//            subject.addTeacher(teacher);
//            session.save(subject);
//            Question question1 = new Question("Solve that equation: X+5=10","3","2","5","4.5",3,teacher,subject);
//            Question question2 = new Question("Solve that equation: X+7=17","3","2","10","4.5",3,teacher,subject);
//            Question question3 = new Question("Solve that equation: X-5+2X=16","4","7","-9","2.6",2,teacher,subject);
//            Question question4 = new Question("Solve that equation: X+7.5=22+2X","-14.5","2.7","-10","-4.5",1,teacher,subject);
//            Question question5 = new Question("Solve that equation: X+9-3X=2X+5","3","1","9","11",2,teacher,subject);
//
//            session.save(question1);
//            session.save(question2);
//            session.save(question3);
//            session.save(question4);
//            session.save(question5);
//
//            Teacher teacher2 = new Teacher("Joel_Nakaka","1234","ynak@gmail.com","Joel","Nakaka","male");
//            session.save(teacher2);
//            Subject subject2 = new Subject("Science");
//            subject.addTeacher(teacher2);
//            subject2.addTeacher(teacher2);
//            session.save(subject2);
//            Question question6 = new Question("What is the speed of light?","~300000 KM/SEC","~200000 KM/SEC","~1080000000 KM/SEC","~400000 KM/SEC",1,teacher2,subject2);
//            Question question7 = new Question("What is the name of the smallest body bone?","wrist","knee","tail","stapes",3,teacher2,subject2);
//            Question question8 = new Question("Simplify: 12a + 26b -4b – 16a","4a+22b","-4a+22b","-9a+30b","2a+3b",2,teacher2,subject);
//            Question question9 = new Question("What is |-26|?","-26","26","0","-1",1,teacher2,subject);
//            Question question10 = new Question("Which is bigger planet?","Earth","Sun","Earth-moon","Venus",2,teacher2,subject2);
//
//            session.save(question6);
//            session.save(question7);
//            session.save(question8);
//            session.save(question9);
//            session.save(question10);
//
//            questionsRepository= new QuestionsRepository();
//
//            Principle principle = new Principle("nitza_patiti","1234","nitza_patiti@gmail.com",
//                    "Nitza","Patiti","Female");
//            session.save(principle);
//
//            Student student = new Student("yoav_ben_haim","1234","yovavi@gmail.com","Yoav","Ben Haim","Male");
//            Student student1 = new Student("yarden_ganon","1234","yardenganon@gmail.com","Yarden","Ganon","Male");
//            Student student2 = new Student("daniel_levi","1234","levidaniel@gmail.com","Daniel","Levi","Female");
//            Student student3 = new Student("ohad_fridman","1234","ohadfridman@gmail.com","Ohad","Fridman","Male");
//
//            session.save(student);
//            session.save(student1);
//            session.save(student2);
//            session.save(student3);
//
//            Course mathADV = new Course(subject,teacher);
//            mathADV.addStudent(student);
//            mathADV.addStudent(student1);
//            mathADV.addStudent(student2);
//            mathADV.addStudent(student3);
//            session.save(mathADV);
//
//            Course scienceADV = new Course(subject2,teacher2);
//            scienceADV.addStudent(student2);
//            session.save(scienceADV);
//
//            Test test = new Test(teacher,subject);
//            test.addQuestion(question1,33);
//            test.addQuestion(question2,33);
//            test.addQuestion(question3,34);
//            test.setCommentForTeachers("Very Easy Questions (Shouldn't be visible to students");
//            test.setEpilogue("Good Luck");
//            test.setIntroduction("Answer all questions please");
//            test.setTime(20);
//            session.save(test);
//
//            Test test1 = new Test(teacher2,subject2);
//            test1.addQuestion(question6,33);
//            test1.addQuestion(question7,33);
//            test1.addQuestion(question8,34);
//            test1.setTime(15);
//            test1.setIntroduction("Answer all please");
//            test1.setCommentForTeachers("Test fot dumbs");
//            test1.setEpilogue("Good luck");
//            session.save(test1);



            //questionsRepository.getQuestionsByUser()
            /*List<Subject> subjectList = new ArrayList<Subject>();
            subjectList.add(subject);
            subjectList.add(subject2);
            List<Subject> questionList = questionsRepository.getQuestionsBySubject(subjectList);
            System.out.println(questionList);*/

            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session, exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
        }
    }
}
