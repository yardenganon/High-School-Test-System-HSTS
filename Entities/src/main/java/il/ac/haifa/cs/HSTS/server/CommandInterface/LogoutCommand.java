package il.ac.haifa.cs.HSTS.server.CommandInterface;

import il.ac.haifa.cs.HSTS.server.Entities.User;

public class LogoutCommand extends CommandInterface {

    private User user;

    public LogoutCommand(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
