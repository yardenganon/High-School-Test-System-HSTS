package il.ac.haifa.cs.HSTS.ocsf.server.Entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "questions")
public class Question implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
    Subject subject;
    String question;
    String answer1;
    String answer2;
    String answer3;
    String answer4;
    int correctAnswer;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "writer_id")
    Teacher writer;

    public Question() {
    }

    public Question(String question, String answer1, String answer2, String answer3, String answer4, int correctAnswer,
                    Teacher writer, Subject subject) {
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.correctAnswer = correctAnswer;
        this.writer = writer;
        this.subject = subject;

        // making Bi-Directional bond
        setWriter(writer);
        setSubject(subject);
    }



    public void setAnswer(int num, String answer) {
        switch (num) {
            case 1:
                answer1 = answer;
                break;
            case 2:
                answer2 = answer;
                break;
            case 3:
                answer3 = answer;
                break;
            case 4:
                answer4 = answer;
                break;
        }
    }

    public String getAnswer(int num) {
        switch (num) {
            case 1:
                return (answer1);
            case 2:
                return (answer2);
            case 3:
                return (answer3);
        }
        return answer4;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(Teacher writer) {
        this.writer = writer;
        writer.addQuestion(this);
    }

    public int getId() {
        return id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
        subject.addQuestion(this);
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }


    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", answer1='" + answer1 + '\'' +
                ", answer2='" + answer2 + '\'' +
                ", answer3='" + answer3 + '\'' +
                ", answer4='" + answer4 + '\'' +
                ", correctAnswer=" + correctAnswer +
                '}';
    }
}
