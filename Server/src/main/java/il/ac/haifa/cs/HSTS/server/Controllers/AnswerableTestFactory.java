package il.ac.haifa.cs.HSTS.server.Controllers;

import il.ac.haifa.cs.HSTS.HSTSServer;
import il.ac.haifa.cs.HSTS.server.Entities.AnswerableTest;
import il.ac.haifa.cs.HSTS.server.Entities.ReadyTest;
import il.ac.haifa.cs.HSTS.server.Entities.Student;
import il.ac.haifa.cs.HSTS.server.Services.Bundle;

public class AnswerableTestFactory {
    private Bundle bundle;
    private HSTSServer server;
    public AnswerableTestFactory() {
        bundle = Bundle.getInstance();
        server = (HSTSServer) bundle.get("server");
    }

 //   public Object makeAnswerableTestSession(String code, Student student) {
        /* 1. Have to check if ReadyTest with the given code is exists and is active (Status.InvalidCode \ Status.NotActive)
        *  2. Have to check if the student belongs to the course of that ReadyTest instance (Status.PermissionDenied)
        *  3. Have to check if there is an instance of AnswerableTest in DB belong to the student and if it's not finished (Status.OK)
        *  - True - return it
        *  - False - return new Instance */

        // 1.  ReadyTest readyTest = getReadyTestByCode(code);
        // 1.1 Boolean isActive = readyTest.isActive();
        // 1.2 Course course = getCourseFromReadyTest(readyTest);

        // 2.  Boolean permission = isStudentBelongsToCourse(student.getId(), course);

        // 3.  AnswerableTest answerableTest = getAnswerableTestByStudentAndReadyTest(readyTest, student);
        // 3.1 If (answerableTest == null) return pushAnswerableTest(readyTest, student);

   // }
}
