package il.ac.haifa.cs.HSTS.ocsf.server.Controllers;

import il.ac.haifa.cs.HSTS.Command;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.User;
import il.ac.haifa.cs.HSTS.ocsf.server.Repositories.UsersRepository;

public class UsersController {
    final private UsersRepository usersRepository;

    public UsersController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Command UserHandler(Command command) {
        if (command != null) {
            switch (command.getCommand()) {
                case "login":
                    Object userNameParameter = command.getParameter(0);
                    String userName = (String.valueOf(userNameParameter));
                    Object passwordParameter = command.getParameter(1);
                    String password = String.valueOf(passwordParameter);
                    User user = usersRepository.login(userName,password);
                    command.setReturnedObject(user);
                    break;
                default:
                    command.setStatus("Command invalid");
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
