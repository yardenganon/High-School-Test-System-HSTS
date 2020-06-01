package il.ac.haifa.cs.HSTS.server;

import java.util.Date;

public class AppResult {
    int id;
    String teacherWriter;
    String subject;

    public AppResult(int id, String teacherWriter, String subject) {
        this.id = id;
        this.teacherWriter = teacherWriter;
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "AppResult{" +
                "id=" + id +
                ", teacherWriter='" + teacherWriter + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
