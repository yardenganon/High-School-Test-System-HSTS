package il.ac.haifa.cs.HSTS.server.Controllers;

import il.ac.haifa.cs.HSTS.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.server.CommandInterface.LoginCommand;
import il.ac.haifa.cs.HSTS.server.CommandInterface.LogoutCommand;
import il.ac.haifa.cs.HSTS.server.Entities.User;
import il.ac.haifa.cs.HSTS.server.Repositories.UsersRepository;
import il.ac.haifa.cs.HSTS.server.CommandInterface.Response;
import il.ac.haifa.cs.HSTS.server.Status.Status;

import java.util.Date;

public class UserController implements ControllerInterface {

    final private UsersRepository usersRepository;

    public UserController() {
        this.usersRepository = new UsersRepository();
    }

    @Override
    public Response executeCommand(CommandInterface command) {
        Response responseMessage = new Response(null, command.getCommandName());
        User user = null;
        if (command instanceof LoginCommand) {
            System.out.println("Execute Command - Login");
            LoginCommand loginCommand = (LoginCommand) command;
            String userName = loginCommand.getUserName();
            String password = loginCommand.getPassword();

            user = usersRepository.login(userName, password);
            if (user !=null && user.getLoggedIn()) {
                responseMessage.setStatus(Status.Denied);
                responseMessage.setReturnedObject(null);
            }
            else if (user != null) {
                usersRepository.logoutLogin(user, true);
                responseMessage.setReturnedObject(user);
                responseMessage.setStatus(Status.Success);
            }
        } else {
            System.out.println("Execute Command - Logout");
            LogoutCommand logoutCommand = (LogoutCommand) command;
           user = logoutCommand.getUser();
            if (user !=null) {
                usersRepository.logoutLogin(user, false);
            }
        }

        responseMessage.setDateHandled(new Date());


        System.out.println("Command handled successfully");
        System.out.println("Return respond");
        return responseMessage;
    }
}
