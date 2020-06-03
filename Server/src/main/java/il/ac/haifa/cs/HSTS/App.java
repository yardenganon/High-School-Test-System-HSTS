package il.ac.haifa.cs.HSTS;

import il.ac.haifa.cs.HSTS.server.Entities.*;
import il.ac.haifa.cs.HSTS.server.Repositories.QuestionsRepository;
import il.ac.haifa.cs.HSTS.server.Services.SessionFactoryGlobal;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Date;
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

            /* Insert data here */
            Teacher teacher = new Teacher("Yaffa_Hamuza","1234","yaffa@gmail.com","Yaffa","Hamuza","female");
            session.save(teacher);
            Subject subject = new Subject("Mathematics");
            subject.addTeacher(teacher);
            session.save(subject);
            Question question1 = new Question("Solve that equation: X+5=10","3","2","5","4.5",3,teacher,subject);
            Question question2 = new Question("Solve that equation: X+7=17","3","2","10","4.5",3,teacher,subject);
            Question question3 = new Question("Solve that equation: X-5+2X=16","4","7","-9","2.6",2,teacher,subject);
            Question question4 = new Question("Solve that equation: X+7.5=22+2X","-14.5","2.7","-10","-4.5",1,teacher,subject);
            Question question5 = new Question("Solve that equation: X+9-3X=2X+5","3","1","9","11",2,teacher,subject);
            Question question11 = new Question("The value of x + x(x^x) when x = 2 is:","10","16","19","36",1,teacher,subject);
            Question question12 = new Question("If (0.2)^x = 2 and log 2 = 0.3010, then the value of x to the nearest tenth is:","-10","-0.5","-0.4","-0.2",3,teacher,subject);
            Question question13 = new Question("If 10^2y = 25, then 10^-y equals:","-1/5","1/5","1/9","1/625",2,teacher,subject);
            Question question14 = new Question("Simplify: (-9x - 5) - (-9x2 + x - 5)","x(9x-12)","x(9x-11)","x(9x – 10)","x(9x-13)",3,teacher,subject);
            Question question15 = new Question("Simplify: (2x - 4) - (6x + 6)","-2(2x + 5)","-2(2x + 7)","-2(4x + 5)","-6(2x + 5)",1,teacher,subject);

            session.save(question1);
            session.save(question2);
            session.save(question3);
            session.save(question4);
            session.save(question5);
            session.save(question11);
            session.save(question12);
            session.save(question13);
            session.save(question14);
            session.save(question15);

                Teacher teacher2 = new Teacher("Joel_Nakaka","1234","ynak@gmail.com","Joel","Nakaka","male");
            session.save(teacher2);
            Subject subject2 = new Subject("Science");
            subject.addTeacher(teacher2);
            subject2.addTeacher(teacher2);
            session.save(subject2);
            Question question6 = new Question("What is the speed of light?","~300000 KM/SEC","~200000 KM/SEC","~1080000000 KM/SEC","~400000 KM/SEC",1,teacher2,subject2);
            Question question7 = new Question("What is the name of the smallest body bone?","wrist","knee","tail","stapes",3,teacher2,subject2);
            Question question8 = new Question("Simplify: 12a + 26b -4b – 16a","4a+22b","-4a+22b","-9a+30b","2a+3b",2,teacher2,subject);
            Question question9 = new Question("What is |-26|?","-26","26","0","-1",1,teacher2,subject);
            Question question10 = new Question("Which is bigger planet?","Earth","Sun","Earth-moon","Venus",2,teacher2,subject2);
            Question question16 = new Question("Humans and chimpanzees share roughly how much DNA?","90%","98%","85%","88%",2,teacher2,subject2);
            Question question17 = new Question("What is the most abundant gas in the Earth’s atmosphere?","Nitrogen","CO2","Hainkenotrydomo","Oxygen",1,teacher2,subject2);
            Question question18 = new Question("Which famous British physicist wrote A Brief History of Time?","Sir Arthur Stanley Eddington","Oliver Heaviside","Edward Victor Appleton","Stephen Hawking",4,teacher2,subject2);
            Question question19 = new Question("Find the value of 3 + 2 • (8 – 3):","25","13","17","24",2,teacher2,subject);
            Question question20 = new Question("Factor: 16w^3 – u^4 * w^3:","w^3(4 + u^2)(2 + u)(2 - u)","w^3(4 + u^2)(2 - u)","w^3(4 + u^2)(2 + u)","w^3(4 + u^2)(4 + u)",1,teacher2,subject);


            session.save(question6);
            session.save(question7);
            session.save(question8);
            session.save(question9);
            session.save(question10);
            session.save(question16);
            session.save(question17);
            session.save(question18);
            session.save(question19);
            session.save(question20);

            questionsRepository= new QuestionsRepository();

            Principle principle = new Principle("nitza_patiti","1234","nitza_patiti@gmail.com",
                    "Nitza","Patiti","Female");
            session.save(principle);

            Student student = new Student("yoav_ben_haim","1234","yovavi@gmail.com","Yoav","Ben Haim","Male");
            Student student1 = new Student("yarden_ganon","1234","yardenganon@gmail.com","Yarden","Ganon","Male");
            Student student2 = new Student("daniel_levi","1234","levidaniel@gmail.com","Daniel","Levi","Female");
            Student student3 = new Student("ohad_fridman","1234","ohadfridman@gmail.com","Ohad","Fridman","Male");

            session.save(student);
            session.save(student1);
            session.save(student2);
            session.save(student3);

            Course mathADV = new Course(subject,teacher);
            mathADV.addStudent(student);
            mathADV.addStudent(student1);
            mathADV.addStudent(student2);
            mathADV.addStudent(student3);
            session.save(mathADV);

            Course mathDiscrete = new Course(subject,teacher);
            mathADV.addStudent(student);
            mathADV.addStudent(student1);
            session.save(mathDiscrete);

            Course calculus = new Course(subject,teacher);
            mathADV.addStudent(student);
            mathADV.addStudent(student3);
            session.save(mathDiscrete);

            Course algebra = new Course(subject,teacher);
            mathADV.addStudent(student2);
            mathADV.addStudent(student);
            session.save(mathDiscrete);

            Course scienceADV = new Course(subject2,teacher2);
            scienceADV.addStudent(student2);
            //scienceADV.setCourseName("Algo");
            session.save(scienceADV);

            Course biology = new Course(subject2,teacher2);
            scienceADV.addStudent(student);
            scienceADV.addStudent(student2);
            scienceADV.addStudent(student3);
            scienceADV.addStudent(student1);
            session.save(scienceADV);

            Course physics = new Course(subject2,teacher2);
            scienceADV.addStudent(student);
            scienceADV.addStudent(student2);
            scienceADV.addStudent(student1);
            session.save(scienceADV);

            Course probability = new Course(subject,teacher2);
            scienceADV.addStudent(student3);
            scienceADV.addStudent(student2);
            scienceADV.addStudent(student1);
            session.save(scienceADV);

            Test test = new Test(teacher,subject);
            test.addQuestion(question1,33);
            test.addQuestion(question2,33);
            test.addQuestion(question3,34);
            test.setCommentForTeachers("Very Easy Questions (Shouldn't be visible to students");
            test.setEpilogue("Good Luck");
            test.setIntroduction("Answer all questions please");
            test.setTime(20);
            session.save(test);
            ReadyTest readyTest = new ReadyTest(test, "1234", calculus, teacher);
            session.save(readyTest);
            AnswerableTest answerTest = new AnswerableTest(readyTest, student);
            session.save(answerTest);
            answerTest = new AnswerableTest(readyTest, student3);
            session.save(answerTest);

            test = new Test(teacher,subject);
            test.addQuestion(question4,33);
            test.addQuestion(question5,33);
            test.addQuestion(question11,34);
            test.setCommentForTeachers("Do not publish to students");
            test.setEpilogue("Good Luck");
            test.setIntroduction("Answer all questions please");
            test.setTime(20);
            session.save(test);
            readyTest = new ReadyTest(test, "4321", calculus, teacher);
            session.save(readyTest);
            answerTest = new AnswerableTest(readyTest, student3);
            session.save(answerTest);

            test = new Test(teacher,subject);
            test.addQuestion(question12,33);
            test.addQuestion(question13,33);
            test.addQuestion(question14,34);
            test.setCommentForTeachers("New questions in math");
            test.setEpilogue("Good Luck");
            test.setIntroduction("Answer all questions please");
            test.setTime(20);
            session.save(test);
            readyTest = new ReadyTest(test, "12345", algebra, teacher);
            session.save(readyTest);
            answerTest = new AnswerableTest(readyTest, student);
            session.save(answerTest);
            answerTest = new AnswerableTest(readyTest, student2);
            session.save(answerTest);

            test = new Test(teacher,subject);
            test.addQuestion(question15,33);
            test.addQuestion(question5,33);
            test.addQuestion(question3,34);
            test.setCommentForTeachers("Medium level");
            test.setEpilogue("Good Luck");
            test.setIntroduction("Answer all questions please");
            test.setTime(30);
            session.save(test);
            readyTest = new ReadyTest(test, "54321", algebra, teacher);
            session.save(readyTest);

            test = new Test(teacher,subject);
            test.addQuestion(question14,33);
            test.addQuestion(question12,33);
            test.addQuestion(question1,34);
            test.setCommentForTeachers("High level");
            test.setEpilogue("Good Luck");
            test.setIntroduction("Answer all questions please");
            test.setTime(20);
            session.save(test);
            readyTest = new ReadyTest(test, "123456", mathADV, teacher);
            session.save(readyTest);

            test = new Test(teacher,subject);
            test.addQuestion(question2,33);
            test.addQuestion(question11,33);
            test.addQuestion(question15,34);
            test.setCommentForTeachers("Bad test. consider");
            test.setEpilogue("Good Luck");
            test.setIntroduction("Answer all questions please");
            test.setTime(20);
            session.save(test);
            readyTest = new ReadyTest(test, "654321", mathDiscrete, teacher);
            session.save(readyTest);

            test = new Test(teacher,subject);
            test.addQuestion(question12,33);
            test.addQuestion(question5,33);
            test.addQuestion(question4,34);
            test.setCommentForTeachers("Test for students with difficulties");
            test.setEpilogue("Good Luck");
            test.setIntroduction("Answer all questions please");
            test.setTime(20);
            session.save(test);

            test = new Test(teacher,subject);
            test.addQuestion(question11,33);
            test.addQuestion(question15,33);
            test.addQuestion(question13,34);
            test.setCommentForTeachers("should be edited");
            test.setEpilogue("Good Luck");
            test.setIntroduction("Answer all questions please");
            test.setTime(20);
            session.save(test);

            Test test1 = new Test(teacher2,subject2);
            test1.addQuestion(question6,33);
            test1.addQuestion(question7,33);
            test1.addQuestion(question16,34);
            test1.setTime(15);
            test1.setIntroduction("Answer all please");
            test1.setCommentForTeachers("Test fot dumbs");
            test1.setEpilogue("Good luck");
            session.save(test1);
            ReadyTest readyTest1 = new ReadyTest(test1, "j1234", physics, teacher2);
            session.save(readyTest1);
            AnswerableTest answerTest1 = new AnswerableTest(readyTest1, student);
            session.save(answerTest1);
            answerTest1 = new AnswerableTest(readyTest1, student2);
            session.save(answerTest1);
            answerTest1 = new AnswerableTest(readyTest1, student1);
            session.save(answerTest1);

            test1 = new Test(teacher2,subject2);
            test1.addQuestion(question7,33);
            test1.addQuestion(question10,33);
            test1.addQuestion(question16,34);
            test1.setTime(15);
            test1.setIntroduction("Answer all please");
            test1.setCommentForTeachers("Easy test");
            test1.setEpilogue("Good luck");
            session.save(test1);
            readyTest1 = new ReadyTest(test1, "j12345", physics, teacher2);
            session.save(readyTest1);

            test1 = new Test(teacher2,subject2);
            test1.addQuestion(question17,33);
            test1.addQuestion(question18,33);
            test1.addQuestion(question16,34);
            test1.setTime(15);
            test1.setIntroduction("Answer all please");
            test1.setCommentForTeachers("Need to edit");
            test1.setEpilogue("Good luck");
            session.save(test1);
            readyTest1 = new ReadyTest(test1, "j123456", biology, teacher2);
            session.save(readyTest1);
            answerTest1 = new AnswerableTest(readyTest1, student);
            session.save(answerTest1);
            answerTest1 = new AnswerableTest(readyTest1, student2);
            session.save(answerTest1);
            answerTest1 = new AnswerableTest(readyTest1, student3);
            session.save(answerTest1);


            test1 = new Test(teacher2,subject2);
            test1.addQuestion(question18,33);
            test1.addQuestion(question6,33);
            test1.addQuestion(question7,34);
            test1.setTime(15);
            test1.setIntroduction("Answer all please");
            test1.setCommentForTeachers("Medium test");
            test1.setEpilogue("Good luck");
            session.save(test1);
            readyTest1 = new ReadyTest(test1, "j1234567", biology, teacher2);
            session.save(readyTest1);

            test1 = new Test(teacher2,subject2);
            test1.addQuestion(question18,33);
            test1.addQuestion(question6,33);
            test1.addQuestion(question10,34);
            test1.setTime(15);
            test1.setIntroduction("Answer all please");
            test1.setCommentForTeachers("Hard test");
            test1.setEpilogue("Good luck");
            session.save(test1);

            test1 = new Test(teacher2,subject);
            test1.addQuestion(question8,33);
            test1.addQuestion(question9,33);
            test1.addQuestion(question19,34);
            test1.setTime(15);
            test1.setIntroduction("Answer all please");
            test1.setCommentForTeachers("Consider replace questions");
            test1.setEpilogue("Good luck");
            session.save(test1);
            readyTest1 = new ReadyTest(test1, "j12345678", mathDiscrete, teacher2);
            session.save(readyTest1);

            test1 = new Test(teacher2,subject);
            test1.addQuestion(question19,33);
            test1.addQuestion(question20,33);
            test1.addQuestion(question8,34);
            test1.setTime(15);
            test1.setIntroduction("Answer all please");
            test1.setCommentForTeachers("Add a bonus question");
            test1.setEpilogue("Good luck");
            session.save(test1);


            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session, exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
        }
    }
}
