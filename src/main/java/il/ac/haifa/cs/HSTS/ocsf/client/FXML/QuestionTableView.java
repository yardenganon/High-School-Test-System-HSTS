package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

public class QuestionTableView {
    private String id;
    private String question;
    private String author;
    private String subject;

    public QuestionTableView(String id, String question, String author, String subject) {
        this.id = id;
        this.question = question;
        this.author = author;
        this.subject = subject;
    }

    public String getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAuthor() {
        return author;
    }

    public String getSubject() {
        return subject;
    }
}
