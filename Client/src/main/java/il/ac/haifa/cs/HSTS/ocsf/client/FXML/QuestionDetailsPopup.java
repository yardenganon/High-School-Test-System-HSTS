package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import il.ac.haifa.cs.HSTS.ocsf.client.Services.Bundle;
import il.ac.haifa.cs.HSTS.server.Entities.Question;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class QuestionDetailsPopup implements Initializable {
    Question question;
    Bundle bundle = null;

    @FXML
    private Label idLabel, writerLabel, subjectLabel, questionLabel, answer1Label,
            answer2Label, answer3Label, answer4Label, text;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bundle = Bundle.getInstance();
        question = (Question) bundle.get("question");
        initLabels();
    }
    public void initLabels() {
        idLabel.setText(String.valueOf(question.getId()));
        writerLabel.setText(question.getWriter().getUsername());
        subjectLabel.setText(question.getSubject().getSubjectName());
        questionLabel.setText(question.getQuestion());
        answer1Label.setText(question.getAnswer(1));
        answer2Label.setText(question.getAnswer(2));
        answer3Label.setText(question.getAnswer(3));
        answer4Label.setText(question.getAnswer(4));
    }
}
