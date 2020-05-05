package il.ac.haifa.cs.HSTS.ocsf.server.Controllers;

import il.ac.haifa.cs.HSTS.Command;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Question;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.User;
import il.ac.haifa.cs.HSTS.ocsf.server.Repositories.UsersRepository;

public class UsersController {
    private UsersRepository usersRepository;

    public UsersController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
    public Command UserHandler(Command command) {

        // CRUD - Create , Read , Update , Delete
        if (command != null) {
            switch (command.getCommand()) {
                case "login" :
                    Object parameter = command.getParameter(0);
                    String castedParam = (String.valueOf(parameter));
                    Object parameter1 = command.getParameter(1);
                    String castedParam1 = String.valueOf(parameter1);
                    //User q = usersRepository.login(castedParam,castedParam1);
                    //command.setReturnedObject(q);
                    break;
                default : command.setStatus("Command invalid");
                    System.out.println("Command invalid: "+command.getCommand());
                    return command;
            }
            command.notifySuccessfullyHandled();
            System.out.print("Command handled successfully ");
            command.printCommandDetails();
            return command;
        }
        return null;
    }
}
