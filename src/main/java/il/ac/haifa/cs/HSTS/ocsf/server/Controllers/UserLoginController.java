package il.ac.haifa.cs.HSTS.ocsf.server.Controllers;

import il.ac.haifa.cs.HSTS.ocsf.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.ocsf.server.CommandInterface.LoginCommand;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.User;
import il.ac.haifa.cs.HSTS.ocsf.server.Repositories.UsersRepository;
import il.ac.haifa.cs.HSTS.ocsf.server.Services.Respond;

import java.util.Date;

public class UserLoginController implements ControllerInterface {

    final private UsersRepository usersRepository;

    public UserLoginController() {
        this.usersRepository = new UsersRepository();
    }

    @Override
    public Respond executeCommand(CommandInterface command) {
        System.out.println("Execute Command - Login");
        LoginCommand loginCommand = (LoginCommand) command;
        String userName = loginCommand.getUserName();
        String password = loginCommand.getPassword();

        User user = usersRepository.login(userName, password);

        Respond respondMessage = new Respond();
        respondMessage.setDateHandled(new Date());
        respondMessage.setReturnedObject(user);
        respondMessage.setRespondName("Login");
        respondMessage.setStatus("Success");
        System.out.println("Command handled successfully");
        System.out.println("Return respond");
        return respondMessage;
    }

    @Override
    public String getControllerName() {
        return "UserLoginController";
    }

}
