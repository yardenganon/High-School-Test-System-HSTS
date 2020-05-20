package il.ac.haifa.cs.HSTS.server.Services;

import il.ac.haifa.cs.HSTS.server.Entities.*;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileOutputStream;

public class TestToWord {
    public TestToWord(ReadyTest readyTest, Student student) throws Exception {

        String fileName = ("C:/Users/yarde/testid_"+readyTest.getId()+
                "courseid_"+readyTest.getCourse().getId()+
                "teacher_"+readyTest.getModifierWriter().getUsername()+
                "student_"+student.getUsername()+".docx");

        // Blank doc
        XWPFDocument document = new XWPFDocument();

        // Write the doc into a file
        FileOutputStream out = new FileOutputStream(new File(fileName));

        // Creating paragraph
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun run = paragraph.createRun();
        run.setText("Test number:  "+readyTest.getId()+
                "     Teacher:  "+ readyTest.getModifierWriter().getFirst_name()+
                " " +readyTest.getModifierWriter().getLast_name()+
                "     Course number:  "+readyTest.getCourse().getId() +
                "     Student:  "+student.getFirst_name()+ " "+student.getLast_name());

        // Now paragraph for introduction
        paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);

        run = paragraph.createRun();
        run.setText(readyTest.getTest().getIntroduction());

        // Here for loop to set Questions

        // Here epilogue

        // Use firebase to upload manual tests




        // Out
        document.write(out);
        out.close();

    }
    public static void main(String[] args){
        Subject sub = new Subject("Football");
        Teacher fl = new Teacher("Frank_Lampard","1234","fl@chelsea.com","Frank","Lampard","Male");
        Course course = new Course(sub,fl);
        ReadyTest r = new ReadyTest(new Test(fl,sub),"abcd",course,fl);

        Student st = new Student("Tami_ab","1234","tamiAb@chelsea.com","Tami","Abraham","male");

        try {
            TestToWord test = new TestToWord(r,st);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
