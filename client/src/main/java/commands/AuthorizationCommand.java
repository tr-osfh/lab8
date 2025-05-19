package commands;

import connection.Response;
import connection.User;

import java.io.Serial;
import java.io.Serializable;

public class AuthorizationCommand implements Command, Serializable {


    @Serial
    private static final long serialVersionUID  = 18L;

    private User user;

    public AuthorizationCommand(User user){
        this.user = user;
    }


    @Override
    public Response execute() {
        return null;
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public String getCommandName() {
        return "authorization";
    }
}
