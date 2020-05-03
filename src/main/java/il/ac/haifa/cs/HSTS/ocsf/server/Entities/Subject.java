package il.ac.haifa.cs.HSTS.ocsf.server.Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subject")
public class Subject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    int subjectCode;
    String subjectName;
    @OneToMany
    List<Question> subjectQuestions;
    int numberOfQuestions;

    public Subject(){}
    public Subject(int subjectCode, String subjectName){
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.subjectQuestions = new ArrayList<Question>();
        this.numberOfQuestions = 0;
    }

    public int getId() {
        return id;
    }

    public int getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectcode(int subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return this.subjectName;
    }

    public void setSubject_name(String subjectName) {
        this.subjectName = subjectName;
    }

    public List<Question> getSubjectQuestions() {
        return subjectQuestions;
    }

    public void setSubjectQuestions(List<Question> subjectQuestions) {
        this.subjectQuestions = subjectQuestions;
    }
}
