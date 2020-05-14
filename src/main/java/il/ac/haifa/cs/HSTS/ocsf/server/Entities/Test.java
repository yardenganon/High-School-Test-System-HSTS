package il.ac.haifa.cs.HSTS.ocsf.server.Entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Test {
    int id;
    String introduction;
    List<Question> questionList;
    HashMap<Question,Integer> points;
    Teacher writer;
    String epilogue;

    public Test(String introduction, List<Question> questionList, Teacher writer) {
        this.introduction = introduction;
        this.questionList = new ArrayList<Question>();
        this.points = new HashMap<Question,Integer>(); // Stores points of a question
        this.writer = writer;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public Teacher getWriter() {
        return writer;
    }

    public void setWriter(Teacher writer) {
        this.writer = writer;
    }

    public String getEpilogue() {
        return epilogue;
    }

    public void setEpilogue(String epilogue) {
        this.epilogue = epilogue;
    }

    public void addQuestion(Question question, Integer points)
    {
        this.questionList.add(question);
        this.points.put(question,points);
    }
}
