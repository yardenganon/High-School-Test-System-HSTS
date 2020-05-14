package il.ac.haifa.cs.HSTS.ocsf.server.Controllers;

import il.ac.haifa.cs.HSTS.ocsf.server.CommandInterface.CommandInterface;
import il.ac.haifa.cs.HSTS.ocsf.server.CommandInterface.LoginCommand;
import il.ac.haifa.cs.HSTS.ocsf.server.Entities.User;
import il.ac.haifa.cs.HSTS.ocsf.server.Repositories.UsersRepository;
import il.ac.haifa.cs.HSTS.ocsf.server.Services.Response;

import java.util.Date;

public class UserLoginController implements ControllerInterface {

    final private UsersRepository usersRepository;

    public UserLoginController() {
        this.usersRepository = new UsersRepository();
    }

    @Override
    public Response executeCommand(CommandInterface command) {
        System.out.println("Execute Command - Login");
        LoginCommand loginCommand = (LoginCommand) command;
        String userName = loginCommand.getUserName();
        String password = loginCommand.getPassword();

        User user = usersRepository.login(userName, password);

        Response responseMessage = new Response();
        responseMessage.setDateHandled(new Date());
        responseMessage.setReturnedObject(user);
        responseMessage.setRespondName("Login");
        responseMessage.setStatus("Success");
        System.out.println("Command handled successfully");
        System.out.println("Return respond");
        return responseMessage;
    }

    @Override
    public String getControllerName() {
        return "UserLoginController";
    }

}
