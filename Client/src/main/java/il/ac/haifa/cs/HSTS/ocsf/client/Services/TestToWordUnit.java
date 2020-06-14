package il.ac.haifa.cs.HSTS.ocsf.client.Services;

import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClient;
import il.ac.haifa.cs.HSTS.server.Entities.*;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileOutputStream;

public class TestToWordUnit {
   private String filePath = null;
   private String fileName = null;


    public TestToWordUnit(ReadyTest readyTest, Student student) throws Exception {
        String path = new File("").getAbsolutePath();
        System.out.println(path);
        fileName = "testid_" + readyTest.getId() +
                "courseid_" + readyTest.getCourse().getId() +
                "teacher_" + readyTest.getModifierWriter().getUsername() +
                "student_" + student.getUsername()+ ".docx";

        filePath = path +"\\"+ fileName;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
