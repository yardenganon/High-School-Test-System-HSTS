package il.ac.haifa.cs.HSTS.server.CommandInterface;

public class LoginCommand extends CommandInterface {

    private String password;
    private String userName;

    public LoginCommand(){}

    public LoginCommand(String userName, String password){
        this.password = password;
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }
}
