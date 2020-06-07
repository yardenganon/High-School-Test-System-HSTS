package il.ac.haifa.cs.HSTS.server.Services;

import il.ac.haifa.cs.HSTS.server.Entities.*;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileOutputStream;

public class TestToWordUnit {
    public TestToWordUnit(ReadyTest readyTest, Student student) throws Exception {
        String path = "C:/Users/yarde/";
        String fileName = (path + "testid_" + readyTest.getId() +
                "courseid_" + readyTest.getCourse().getId() +
                "teacher_" + readyTest.getModifierWriter().getUsername() +
                "student_" + student.getUsername() + ".docx");

        // Blank doc
        XWPFDocument document = new XWPFDocument();

        // Write the doc into a file
        FileOutputStream out = new FileOutputStream(new File(fileName));

        // Creating paragraph
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun run = paragraph.createRun();
        run.setBold(true);
        run.setText("Test number:  " + readyTest.getId() +
                "     Teacher:  " + readyTest.getModifierWriter().getFirst_name() +
                " " + readyTest.getModifierWriter().getLast_name() +
                "     Course number:  " + readyTest.getCourse().getId() +
                "     Student:  " + student.getFirst_name() + " " + student.getLast_name());
        run.addBreak();
        run.addBreak();
        run.setText(readyTest.getTest().getIntroduction());

        // Now paragraph for introduction
        paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);

        int i = 1;
        for (Question question : readyTest.getTest().getQuestionSet()) {
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            run = paragraph.createRun();
            run.setBold(false);
            run.addBreak();
            run.setText(i + ". " + question.getQuestion() + "\n");
            run.addBreak();
            run.setText("\t1.\t" + question.getAnswer(1) + "\n");
            run.addBreak();
            run.setText("\t2.\t" + question.getAnswer(2) + "\n");
            run.addBreak();
            run.setText("\t3.\t" + question.getAnswer(3) + "\n");
            run.addBreak();
            run.setText("\t4.\t" + question.getAnswer(4) + "\n\n");
            run.addBreak();
            i++;
        }
        run.addBreak();
        run.addBreak();
        run.setText(readyTest.getTest().getEpilogue());
        // Use firebase to upload manual tests


        // Out
        document.write(out);
        out.close();

    }

    public static void main(String[] args) {
        Subject sub = new Subject("Football");
        Teacher fl = new Teacher("Frank_Lampard", "1234", "fl@chelsea.com", "Frank", "Lampard", "Male");
        Course course = new Course("Football", sub, fl);
        ReadyTest r = new ReadyTest(new Test(fl, sub), "abcd", course, fl);

        Student st = new Student("Tami_ab", "1234", "tamiAb@chelsea.com", "Tami", "Abraham", "male");

        Teacher teacher2 = new Teacher("Joel_Nakaka", "1234", "ynak@gmail.com", "Joel", "Nakaka", "male");

        Subject subject2 = new Subject("Science");
        subject2.addTeacher(teacher2);

        Question question6 = new Question("What is the speed of light?", "~300000 KM/SEC", "~200000 KM/SEC", "~1080000000 KM/SEC", "~400000 KM/SEC", 1, teacher2, subject2);
        Question question7 = new Question("What is the name of the smallest body bone?", "wrist", "knee", "tail", "stapes", 3, teacher2, subject2);
        Question question8 = new Question("Simplify: 12a + 26b -4b – 16a", "4a+22b", "-4a+22b", "-9a+30b", "2a+3b", 2, teacher2, subject2);
        Question question9 = new Question("What is |-26|?", "-26", "26", "0", "-1", 1, teacher2, subject2);
        Question question10 = new Question("Which is bigger planet?", "Earth", "Sun", "Earth-moon", "Venus", 2, teacher2, subject2);
        Question question16 = new Question("Humans and chimpanzees share roughly how much DNA?", "90%", "98%", "85%", "88%", 2, teacher2, subject2);
        Question question17 = new Question("What is the most abundant gas in the Earth’s atmosphere?", "Nitrogen", "CO2", "Hainkenotrydomo", "Oxygen", 1, teacher2, subject2);
        Question question18 = new Question("Which famous British physicist wrote A Brief History of Time?", "Sir Arthur Stanley Eddington", "Oliver Heaviside", "Edward Victor Appleton", "Stephen Hawking", 4, teacher2, subject2);
        Question question19 = new Question("Find the value of 3 + 2 • (8 – 3):", "25", "13", "17", "24", 2, teacher2, subject2);
        Question question20 = new Question("Factor: 16w^3 – u^4 * w^3:", "w^3(4 + u^2)(2 + u)(2 - u)", "w^3(4 + u^2)(2 - u)", "w^3(4 + u^2)(2 + u)", "w^3(4 + u^2)(4 + u)", 1, teacher2, subject2);

        Student student = new Student("yoav_ben_haim", "1234", "yovavi@gmail.com", "Yoav", "Ben Haim", "Male");
        Student student1 = new Student("yarden_ganon", "1234", "yardenganon@gmail.com", "Yarden", "Ganon", "Male");
        Student student2 = new Student("daniel_levi", "1234", "levidaniel@gmail.com", "Daniel", "Levi", "Female");
        Student student3 = new Student("ohad_fridman", "1234", "ohadfridman@gmail.com", "Ohad", "Fridman", "Male");

        Course scienceADV = new Course("ScienceADV", subject2, teacher2);
        scienceADV.addStudent(student);

        Test test = new Test(teacher2, subject2);
        test.addQuestion(question6, 10);
        test.addQuestion(question7, 10);
        test.addQuestion(question8, 10);
        test.addQuestion(question9, 10);
        test.addQuestion(question10, 10);
        test.addQuestion(question16, 10);
        test.addQuestion(question17, 10);
        test.addQuestion(question18, 10);
        test.addQuestion(question19, 10);
        test.addQuestion(question20, 10);
        test.setCommentForTeachers("Test for idiots, very EZ");
        test.setEpilogue("Good Luck");
        test.setIntroduction("Answer all questions please, every question = 10 points");
        test.setTime(20);

        ReadyTest readyTest = new ReadyTest(test, "1234", scienceADV, teacher2);

        AnswerableTest answerableTest = new AnswerableTest(readyTest, student);

        try {
            TestToWordUnit testt = new TestToWordUnit(answerableTest.getTest(), student);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
