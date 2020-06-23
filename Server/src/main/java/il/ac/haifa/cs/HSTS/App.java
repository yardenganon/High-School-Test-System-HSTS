package il.ac.haifa.cs.HSTS;

import il.ac.haifa.cs.HSTS.server.Entities.*;
import il.ac.haifa.cs.HSTS.server.Repositories.QuestionsRepository;
import il.ac.haifa.cs.HSTS.server.Services.SessionFactoryGlobal;
import il.ac.haifa.cs.HSTS.server.Status.Status;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


/* Try to avoid using this test file
 Run this file to create data in DB manually */

public class App {

    private static Session session;

    public static void main(String[] args) {

        QuestionsRepository questionsRepository = new QuestionsRepository();
        try {
            Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
            SessionFactory sessionFactory = SessionFactoryGlobal.getSessionFactory();
            session = SessionFactoryGlobal.openSessionAndTransaction(session);

            /*adding teachers*/
            Teacher dannyKeren = new Teacher("Danny Keren","03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4","danny@gmail.com","Danny","Keren","male");
            Teacher rachelKolodny = new Teacher("Rachel Kolodny","03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4","rachel@gmail.com","Rachel","Kolodny","female");
            Teacher orenVaiman = new Teacher("Oren Vaiman","03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4","oren@gmail.com","Oren","Vaiman","male");
            Teacher gadiLandau = new Teacher("Gadi Landau","03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4","gadi@gmail.com","Gadi","Landau","male");
            Teacher nogaRonTzvi = new Teacher("Noga RonTzvi","03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4","noga@gmail.com","Noga","RonTzvi","female");

            session.save(dannyKeren);
            session.save(rachelKolodny);
            session.save(orenVaiman);
            session.save(gadiLandau);
            session.save(nogaRonTzvi);

            /*adding subjects*/
            Subject math = new Subject("Math");
            Subject science = new Subject("Science");
            Subject history = new Subject("History");
            Subject sport = new Subject("Sport");

            session.save(math);
            session.save(science);
            session.save(history);
            session.save(sport);

            /*connect teacher-subject*/
            math.addTeacher(dannyKeren);
            math.addTeacher(rachelKolodny);
            math.addTeacher(orenVaiman);
            science.addTeacher(dannyKeren);
            science.addTeacher(nogaRonTzvi);
            science.addTeacher(gadiLandau);
            history.addTeacher(gadiLandau);
            history.addTeacher(rachelKolodny);
            sport.addTeacher(orenVaiman);
            sport.addTeacher(nogaRonTzvi);

            session.save(math);
            session.save(science);
            session.save(history);
            session.save(sport);

            /*adding questions and connecting them to subject - Math*/
            Question mathQuestion1 = new Question("Solve that equation: X+5=10","3","2","5","4.5",3,dannyKeren,math);
            Question mathQuestion2 = new Question("Solve that equation: X+7=17","3","2","10","4.5",3,dannyKeren,math);
            Question mathQuestion3 = new Question("Solve that equation: X-5+2X=16","4","7","-9","2.6",2,dannyKeren,math);
            Question mathQuestion4 = new Question("Solve that equation: X+7.5=22+2X","-14.5","2.7","-10","-4.5",1,orenVaiman,math);
            Question mathQuestion5 = new Question("Solve that equation: X+9-3X=2X+5","3","1","9","11",2,orenVaiman,math);
            Question mathQuestion6 = new Question("The value of x + x(x^x) when x = 2 is:","10","16","19","36",1,rachelKolodny,math);
            Question mathQuestion7 = new Question("If (0.2)^x = 2 and log 2 = 0.3010, then the value of x to the nearest tenth is:","-10","-0.5","-0.4","-0.2",3,rachelKolodny,math);
            Question mathQuestion8 = new Question("If 10^2y = 25, then 10^-y equals:","-1/5","1/5","1/9","1/625",2,rachelKolodny,math);
            Question mathQuestion9 = new Question("Simplify: (-9x - 5) - (-9x2 + x - 5)","x(9x-12)","x(9x-11)","x(9x – 10)","x(9x-13)",3,orenVaiman,math);
            Question mathQuestion10 = new Question("Simplify: (2x - 4) - (6x + 6)","-2(2x + 5)","-2(2x + 7)","-2(4x + 5)","-6(2x + 5)",1,dannyKeren,math);

            session.save(mathQuestion1);
            session.save(mathQuestion2);
            session.save(mathQuestion3);
            session.save(mathQuestion4);
            session.save(mathQuestion5);
            session.save(mathQuestion6);
            session.save(mathQuestion7);
            session.save(mathQuestion8);
            session.save(mathQuestion9);
            session.save(mathQuestion10);


            /*adding questions and connecting them to subject - Science*/
            Question scienceQuestion1 = new Question("What is the speed of light?","~300000 KM/SEC","~200000 KM/SEC","~1080000000 KM/SEC","~400000 KM/SEC",1,dannyKeren,science);
            Question scienceQuestion2 = new Question("What is the name of the smallest body bone?","wrist","knee","tail","stapes",3,dannyKeren,science);
            Question scienceQuestion3 = new Question("What is not considered as science?","Biology","Sports","Chemistry","Science",2,dannyKeren,science);
            Question scienceQuestion4 = new Question("The part of machine level instruction, which tells the central processor what has to be done, is","Operation code","Address","Locator","Flip-Flop",1,orenVaiman,science);
            Question scienceQuestion5 = new Question("Which is bigger planet?","Earth","Sun","Earth-moon","Venus",2,orenVaiman,science);
            Question scienceQuestion6 = new Question("Humans and chimpanzees share roughly how much DNA?","90%","98%","85%","88%",2,orenVaiman,science);
            Question scienceQuestion7 = new Question("What is the most abundant gas in the Earth’s atmosphere?","Nitrogen","CO2","Hainkenotrydomo","Oxygen",1,gadiLandau,science);
            Question scienceQuestion8 = new Question("Which famous British physicist wrote A Brief History of Time?","the address of the data is generated by the CPU","the address of the data is supplied by the users","there is no need for an address i.e. the data is used as an address","the data are accessed sequentially\n",3,gadiLandau,science);
            Question scienceQuestion9 = new Question("Which of the following refers to the associative memory?","25","13","17","24",2,gadiLandau,science);
            Question scienceQuestion10 = new Question("To avoid the race condition, the number of processes that may be simultaneously inside their critical section is","8","1","16","0",2,gadiLandau,science);


            session.save(scienceQuestion1);
            session.save(scienceQuestion2);
            session.save(scienceQuestion3);
            session.save(scienceQuestion4);
            session.save(scienceQuestion5);
            session.save(scienceQuestion6);
            session.save(scienceQuestion7);
            session.save(scienceQuestion8);
            session.save(scienceQuestion9);
            session.save(scienceQuestion10);

            /*adding questions and connecting them to subject - History*/
            Question historyQuestion1 = new Question("Which European country was the first to allow women to vote?","England","Germany","Finland","Spain",3,rachelKolodny,history);
            Question historyQuestion2 = new Question("What city was the first capital of the United States?","New York City","Washington, D.C.","New Jersey","Baltimore",1,rachelKolodny,history);
            Question historyQuestion3 = new Question("Who first discovered America?","Leif Erikson","Christopher Columbus","Jeffry Aphstein","Nansi Brandes",1,rachelKolodny,history);
            Question historyQuestion4 = new Question("Where did the pilgrims land in America?","Plymouth Rock","Aclaron Rock","Bulgarian Rock","Unknown",4,rachelKolodny,history);
            Question historyQuestion5 = new Question("What did Paul Revere shout on his midnight ride in 1775?","\"The British are coming!\"","\"The Israelies are coming!\"","\"The regulars are coming!\"","\"No one is coming!\"",3,rachelKolodny,history);
            Question historyQuestion6 = new Question("What were the names of Columbus' ships?","the Niña, the Pinta, and the Santa Maria","the Santa Maria, the Santa Clara, and unknown","the Santa Clara, The Cunetra and unknown","the Plurencia, the Boniflaya, the Santa Maria",2,gadiLandau,history);
            Question historyQuestion7 = new Question("How many stars does the American Flag have?","500","50","51","52",2,gadiLandau,history);
            Question historyQuestion8 = new Question("Ever since World War Two, what beverage’s equipment is furnished in British battle tanks?","Coffee","Water","Tea","Ball",3,gadiLandau,history);
            Question historyQuestion9 = new Question("Which actor top-billed the 1960’s television series Batman?","Adam West","Adam Sandler","Adam Raken","Adam Buli",1,gadiLandau,history);
            Question historyQuestion10 = new Question("Which Century did the French Revolution take place?","Eighteen Century","Ningteen Century","Seventeen Century","Sixteen Century",1,gadiLandau,history);

            session.save(historyQuestion1);
            session.save(historyQuestion2);
            session.save(historyQuestion3);
            session.save(historyQuestion4);
            session.save(historyQuestion5);
            session.save(historyQuestion6);
            session.save(historyQuestion7);
            session.save(historyQuestion8);
            session.save(historyQuestion9);
            session.save(historyQuestion10);


            /*adding questions and connecting them to subject - Sport*/
            Question sportQuestion1 = new Question("What is Maccabi Tel-Aviv nickname?","Germany's","Losers","Dushes","Yellow's",1,orenVaiman,sport);
            Question sportQuestion2 = new Question("which team is the real Maccabi?","Maccabi Haifa","Maccabi Tel-Aviv","Maccabi Ashdod","Maccabi Tivon",1,orenVaiman,sport);
            Question sportQuestion3 = new Question("which team rules Haifa?","Hapoel","Beitar","Maccabi","M.C Ahuza",1,orenVaiman,sport);
            Question sportQuestion4 = new Question("How many points did Liverpool record in the 2019/20 Premier League season before football was postponed?","82","36","72","48",1,orenVaiman,sport);
            Question sportQuestion5 = new Question("In which year did American Football star Tom Brady win his first Super Bowl with the New England Patriots?","2000","2001","2002","2003",3,orenVaiman,sport);
            Question sportQuestion6 = new Question("In which sport do teams compete to win the Stanley Cup?","Basketball","Football","BaseBall","Ice hockey (NHL)",4,nogaRonTzvi,sport);
            Question sportQuestion7 = new Question("Which international team currently sits top of the FIFA Men’s World Rankings? (April 2020)","Egypt","Israel","Germany","Belguim",4,nogaRonTzvi,sport);
            Question sportQuestion8 = new Question("What is Muhammad Ali’s real name?","Briany Clay","Colony Blurb","Train Bloog","Cassius Clay",4,nogaRonTzvi,sport);
            Question sportQuestion9 = new Question("In which sport is 180 deemed a perfect score?","Darts","Tennis","Baseball","Hockey",1,nogaRonTzvi,sport);
            Question sportQuestion10 = new Question("Where is the next Olympic Games set to take place?","Tokyo","Jerusalem","Berlin","London",1,nogaRonTzvi,sport);

            session.save(sportQuestion1);
            session.save(sportQuestion2);
            session.save(sportQuestion3);
            session.save(sportQuestion4);
            session.save(sportQuestion5);
            session.save(sportQuestion6);
            session.save(sportQuestion7);
            session.save(sportQuestion8);
            session.save(sportQuestion9);
            session.save(sportQuestion10);

            /*adding principle*/
            Principle principle = new Principle("ronitCohen","03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4","ronitCohen@gmail.com",
                    "Ronit","Cohen","Female");

            session.save(principle);

            /*adding students*/
            Student yoavBenHaim = new Student("yoavBenHaim","03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4","yovavi@gmail.com","Yoav","Ben Haim","Male");
            Student yardenGanon = new Student("yardenGanon","03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4","yardenganon@gmail.com","Yarden","Ganon","Male");
            Student danielLevi = new Student("danielLevi","03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4","levidaniel@gmail.com","Daniel","Levi","Female");
            Student ohadFridman = new Student("ohadFridman","03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4","ohadfridman@gmail.com","Ohad","Fridman","Male");
            Student opalSelek = new Student("opalSelek","03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4","opalSelek@gmail.com","Opal","Selek","Female");
            Student guyKigel = new Student("guyKigel","03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4","guyKigel@gmail.com","Guy","Kigel","Male");
            Student yuvalTamir = new Student("yuvalTamir","03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4","yuvalTamir@gmail.com","Yuval","Tamir","Male");
            Student linoyMoshe = new Student("linoyMoshe","03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4","linoyMoshe@gmail.com","Linoy","Moshe","Female");
            Student galitOks = new Student("GalitOks","03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4","GalitOks@gmail.com","Galit","Oks","Female");
            Student lielFridman = new Student("lielFridman","03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4","lielFridman@gmail.com","Liel","Fridman","Male");
            Student orAshkenazy = new Student("orAshkenazy","03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4","orAshkenazy@gmail.com","Or","Ashkenazy","Male");
            Student sagiShvili = new Student("sagiShvili","03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4","sagiShvili@gmail.com","Sagi","Shvili","Male");
            Student chenBezinian = new Student("chenBezinian","03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4","chenBezinian@gmail.com","Chen","Bezinian","Male");
            Student barak = new Student("barak","03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4","barak@gmail.com","Barak","Chen","Male");
            Student gal = new Student("gal","03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4","gal@gmail.com","Gal","Mirovski","Male");
            Student ido = new Student("ido","03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4","ido@gmail.com","Ido","Levi","Male");
            Student shani = new Student("shani","03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4","shani@gmail.com","Shani","Meni","Female");
            Student meshi = new Student("meshi","03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4","meshi@gmail.com","Meshi","Haim","Female");
            Student yakir = new Student("yakir","03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4","yakir@gmail.com","Yakir","Solomon","Male");

            session.save(yoavBenHaim);
            session.save(yardenGanon);
            session.save(danielLevi);
            session.save(ohadFridman);
            session.save(opalSelek);
            session.save(guyKigel);
            session.save(yuvalTamir);
            session.save(linoyMoshe);
            session.save(galitOks);
            session.save(lielFridman);
            session.save(sagiShvili);
            session.save(chenBezinian);
            session.save(orAshkenazy);
            session.save(barak);
            session.save(gal);
            session.save(shani);
            session.save(meshi);
            session.save(yakir);
            session.save(ido);


            /*adding courses and match them to subjects and teacher*/
            Course calculusA = new Course("CalculusA",math,dannyKeren);
            Course calculusB = new Course("CalculusB",math,dannyKeren);
            Course discreteMath = new Course("DiscreteMath",math,rachelKolodny);
            Course algebraA = new Course("AlgebraA",math,rachelKolodny);
            Course algebraB = new Course("AlgebraB",math,orenVaiman);

            Course physics = new Course("Physics" ,science,dannyKeren);
            Course scienceADV = new Course("ScienceADV", science,gadiLandau);
            Course biology = new Course("Biology" ,science,gadiLandau);
            Course computerScience = new Course("Computer Science" ,science,nogaRonTzvi);

            Course israelHistory = new Course("Israel History",history,rachelKolodny);
            Course historyA = new Course("historyA",history,gadiLandau);
            Course historyB = new Course("historyB",history,gadiLandau);

            Course basketball = new Course("Basketball",sport,nogaRonTzvi);
            Course football = new Course("Football",sport,orenVaiman);
            Course tennis = new Course("Tennis",sport,orenVaiman);

            session.save(calculusA);
            session.save(calculusB);
            session.save(discreteMath);
            session.save(algebraA);
            session.save(algebraB);
            session.save(physics);
            session.save(scienceADV);
            session.save(biology);
            session.save(computerScience);
            session.save(israelHistory);
            session.save(historyA);
            session.save(historyB);
            session.save(basketball);
            session.save(football);
            session.save(tennis);

            /*adding students to courses*/
            calculusA.addStudent(yoavBenHaim);
            calculusA.addStudent(danielLevi);
            calculusA.addStudent(ohadFridman);
            calculusA.addStudent(yardenGanon);
            calculusA.addStudent(yakir);
            calculusA.addStudent(barak);
            calculusA.addStudent(opalSelek);
            calculusA.addStudent(meshi);
            calculusA.addStudent(shani);
            calculusA.addStudent(gal);
            calculusA.addStudent(orAshkenazy);
            calculusA.addStudent(sagiShvili);
            calculusA.addStudent(guyKigel);
            calculusA.addStudent(yuvalTamir);
            calculusA.addStudent(galitOks);
            calculusA.addStudent(chenBezinian);
            session.save(calculusA);

            calculusB.addStudent(yoavBenHaim);
            calculusB.addStudent(danielLevi);
            calculusB.addStudent(ohadFridman);
            calculusB.addStudent(yardenGanon);
            calculusB.addStudent(yakir);
            calculusB.addStudent(barak);
            calculusB.addStudent(opalSelek);
            calculusB.addStudent(meshi);
            calculusB.addStudent(shani);
            calculusB.addStudent(gal);
            calculusB.addStudent(orAshkenazy);
            calculusB.addStudent(sagiShvili);
            calculusB.addStudent(guyKigel);
            calculusB.addStudent(yuvalTamir);
            calculusB.addStudent(galitOks);
            calculusB.addStudent(chenBezinian);
            session.save(calculusB);


            discreteMath.addStudent(yoavBenHaim);
            discreteMath.addStudent(yardenGanon);
            discreteMath.addStudent(danielLevi);
            discreteMath.addStudent(yakir);
            discreteMath.addStudent(ohadFridman);
            discreteMath.addStudent(galitOks);
            session.save(discreteMath);


            algebraA.addStudent(yoavBenHaim);
            algebraA.addStudent(yardenGanon);
            algebraA.addStudent(danielLevi);
            algebraA.addStudent(gal);
            algebraA.addStudent(shani);
            algebraA.addStudent(meshi);
            algebraA.addStudent(yakir);
            algebraA.addStudent(orAshkenazy);
            algebraA.addStudent(galitOks);
            algebraA.addStudent(chenBezinian);
            session.save(algebraA);

            algebraB.addStudent(yoavBenHaim);
            algebraB.addStudent(yardenGanon);
            algebraB.addStudent(danielLevi);
            algebraB.addStudent(gal);
            algebraB.addStudent(shani);
            algebraB.addStudent(meshi);
            algebraB.addStudent(yakir);
            algebraB.addStudent(orAshkenazy);
            algebraB.addStudent(galitOks);
            algebraB.addStudent(chenBezinian);
            session.save(algebraB);

            biology.addStudent(yoavBenHaim);
            biology.addStudent(danielLevi);
            biology.addStudent(ohadFridman);
            biology.addStudent(yardenGanon);
            biology.addStudent(yakir);
            biology.addStudent(barak);
            biology.addStudent(opalSelek);
            biology.addStudent(meshi);
            biology.addStudent(shani);
            biology.addStudent(gal);
            biology.addStudent(orAshkenazy);
            biology.addStudent(sagiShvili);
            biology.addStudent(guyKigel);
            biology.addStudent(yuvalTamir);
            biology.addStudent(galitOks);
            biology.addStudent(chenBezinian);
            session.save(biology);


            scienceADV.addStudent(yoavBenHaim);
            scienceADV.addStudent(danielLevi);
            scienceADV.addStudent(ohadFridman);
            scienceADV.addStudent(yardenGanon);
            scienceADV.addStudent(yakir);
            scienceADV.addStudent(barak);
            scienceADV.addStudent(opalSelek);
            scienceADV.addStudent(meshi);
            scienceADV.addStudent(shani);
            scienceADV.addStudent(gal);
            scienceADV.addStudent(orAshkenazy);
            scienceADV.addStudent(sagiShvili);
            scienceADV.addStudent(guyKigel);
            scienceADV.addStudent(yuvalTamir);
            scienceADV.addStudent(galitOks);
            scienceADV.addStudent(chenBezinian);
            session.save(scienceADV);


            israelHistory.addStudent(yoavBenHaim);
            israelHistory.addStudent(danielLevi);
            israelHistory.addStudent(ohadFridman);
            israelHistory.addStudent(yardenGanon);
            israelHistory.addStudent(yakir);
            israelHistory.addStudent(barak);
            israelHistory.addStudent(opalSelek);
            israelHistory.addStudent(meshi);
            israelHistory.addStudent(shani);
            israelHistory.addStudent(gal);
            israelHistory.addStudent(orAshkenazy);
            israelHistory.addStudent(sagiShvili);
            israelHistory.addStudent(guyKigel);
            israelHistory.addStudent(yuvalTamir);
            israelHistory.addStudent(galitOks);
            israelHistory.addStudent(chenBezinian);
            session.save(israelHistory);

            historyA.addStudent(yoavBenHaim);
            historyA.addStudent(danielLevi);
            historyA.addStudent(ohadFridman);
            session.save(historyA);

            historyB.addStudent(yardenGanon);
            historyB.addStudent(yakir);
            historyB.addStudent(barak);
            session.save(historyB);

            basketball.addStudent(opalSelek);
            basketball.addStudent(meshi);
            basketball.addStudent(shani);
            session.save(basketball);

            football.addStudent(orAshkenazy);
            football.addStudent(sagiShvili);
            football.addStudent(guyKigel);
            session.save(football);

            tennis.addStudent(yuvalTamir);
            tennis.addStudent(galitOks);
            tennis.addStudent(chenBezinian);
            session.save(tennis);

            /*adding tests and ready tests*/
            Test test = new Test(dannyKeren,math);
            test.addQuestion(mathQuestion1,25);
            test.addQuestion(mathQuestion2,25);
            test.addQuestion(mathQuestion3,25);
            test.addQuestion(mathQuestion4,25);
            test.setCommentForTeachers("Very Easy Questions");
            test.setEpilogue("Good Luck");
            test.setIntroduction("Please Answer the following questions");
            test.setTime(20);
            session.save(test);
            /*readyTest of the above test*/
            ReadyTest readyTest = new ReadyTest(test, "11AA", calculusA, dannyKeren);
            readyTest.setActive(true);
            readyTest.setModifiedTime(30);
            session.save(readyTest);


            test = new Test(dannyKeren,math);
            test.addQuestion(mathQuestion5,25);
            test.addQuestion(mathQuestion6,25);
            test.addQuestion(mathQuestion7,25);
            test.addQuestion(mathQuestion8,25);
            test.setCommentForTeachers("Do not publish to students");
            test.setEpilogue("Good Luck");
            test.setIntroduction("Please Answer the following questions");
            test.setTime(30);
            session.save(test);
            /*readyTest of the above test*/
            ReadyTest readyTest1 = new ReadyTest(test, "11BB", calculusB, dannyKeren);
            readyTest.setActive(false);
            session.save(readyTest1);


            test = new Test(rachelKolodny,math);
            test.addQuestion(mathQuestion5,33);
            test.addQuestion(mathQuestion9,33);
            test.addQuestion(mathQuestion10,34);
            test.setCommentForTeachers("I am the best Teacher");
            test.setEpilogue("Good Luck");
            test.setIntroduction("Please Answer the following questions");
            test.setTime(30);
            session.save(test);

            test = new Test(rachelKolodny,math);
            test.addQuestion(mathQuestion4,10);
            test.addQuestion(mathQuestion9,20);
            test.addQuestion(mathQuestion5,30);
            test.addQuestion(mathQuestion6,10);
            test.addQuestion(mathQuestion1,20);
            test.addQuestion(mathQuestion2,10);
            test.setCommentForTeachers("I am the best Teacher");
            test.setEpilogue("Good Luck");
            test.setIntroduction("Please Answer the following questions");
            test.setTime(60);
            session.save(test);

            test = new Test(orenVaiman,math);
            test.addQuestion(mathQuestion3,10);
            test.addQuestion(mathQuestion4,20);
            test.addQuestion(mathQuestion1,30);
            test.addQuestion(mathQuestion2,10);
            test.addQuestion(mathQuestion10,20);
            test.addQuestion(mathQuestion9,10);
            test.setCommentForTeachers("I am the prettiest teacher");
            test.setEpilogue("Good Luck");
            test.setIntroduction("Please Answer the following questions");
            test.setTime(60);
            session.save(test);

            test = new Test(orenVaiman,math);
            test.addQuestion(mathQuestion3,34);
            test.addQuestion(mathQuestion4,33);
            test.addQuestion(mathQuestion1,33);
            test.setCommentForTeachers("I am the prettiest");
            test.setEpilogue("Good Luck");
            test.setIntroduction("Please Answer the following questions");
            test.setTime(10);
            session.save(test);

            test = new Test(dannyKeren,science);
            test.addQuestion(scienceQuestion1,33);
            test.addQuestion(scienceQuestion2,33);
            test.addQuestion(scienceQuestion3,34);
            test.setCommentForTeachers("I am the head of the Hug");
            test.setEpilogue("Good Luck");
            test.setIntroduction("Please Answer the following questions");
            test.setTime(20);
            session.save(test);

            test = new Test(dannyKeren,science);
            test.addQuestion(scienceQuestion4,20);
            test.addQuestion(scienceQuestion5,20);
            test.addQuestion(scienceQuestion3,20);
            test.addQuestion(scienceQuestion6,20);
            test.addQuestion(scienceQuestion8,20);
            test.setCommentForTeachers("I am the head of the Hug");
            test.setEpilogue("Good Luck");
            test.setIntroduction("Please Answer the following questions");
            test.setTime(15);
            session.save(test);

            test = new Test(nogaRonTzvi,science);
            test.addQuestion(scienceQuestion4,20);
            test.addQuestion(scienceQuestion5,20);
            test.addQuestion(scienceQuestion3,20);
            test.addQuestion(scienceQuestion6,20);
            test.addQuestion(scienceQuestion8,20);
            test.setCommentForTeachers("I am new in the Hug");
            test.setEpilogue("Good Luck");
            test.setIntroduction("Please Answer the following questions");
            test.setTime(90);
            session.save(test);

            test = new Test(gadiLandau,history);
            test.addQuestion(historyQuestion3,33);
            test.addQuestion(historyQuestion4,33);
            test.addQuestion(historyQuestion5,34);
            test.setCommentForTeachers("I am the oldest in the Hug");
            test.setEpilogue("You shell all fail");
            test.setIntroduction("Please Answer the following questions");
            test.setTime(5);
            session.save(test);

            test = new Test(gadiLandau,history);
            test.addQuestion(historyQuestion10,50);
            test.addQuestion(historyQuestion3,5);
            test.addQuestion(historyQuestion2,20);
            test.addQuestion(historyQuestion1,25);
            test.setCommentForTeachers("I am the oldest in the Hug");
            test.setEpilogue("You shell all fail");
            test.setIntroduction("Please Answer the following questions");
            test.setTime(18);
            session.save(test);

            test = new Test(rachelKolodny,history);
            test.addQuestion(historyQuestion4,40);
            test.addQuestion(historyQuestion5,10);
            test.addQuestion(historyQuestion2,25);
            test.addQuestion(historyQuestion7,25);
            test.setCommentForTeachers("I am from Haifa city");
            test.setEpilogue("You shell all fail");
            test.setIntroduction("Please Answer the following questions");
            test.setTime(18);
            session.save(test);

            test = new Test(nogaRonTzvi,sport);
            test.addQuestion(sportQuestion1,40);
            test.addQuestion(sportQuestion4,10);
            test.addQuestion(sportQuestion2,25);
            test.addQuestion(sportQuestion6,25);
            test.setCommentForTeachers("I am from Tivon");
            test.setEpilogue("You shell all fail");
            test.setIntroduction("Please Answer the following questions");
            test.setTime(18);
            session.save(test);

            test = new Test(orenVaiman,sport);
            test.addQuestion(sportQuestion3,40);
            test.addQuestion(sportQuestion10,10);
            test.addQuestion(sportQuestion2,20);
            test.addQuestion(sportQuestion2,10);
            test.addQuestion(sportQuestion2,20);
            test.setCommentForTeachers("I wish i was living in Tivon");
            test.setEpilogue("You shell all fail");
            test.setIntroduction("Please Answer the following questions");
            test.setTime(18);
            session.save(test);

            SessionFactoryGlobal.closeTransaction(session);
        } catch (Exception exception) {
            SessionFactoryGlobal.exceptionCaught(session, exception);
        } finally {
            SessionFactoryGlobal.closeSession(session);
        }
    }
}
