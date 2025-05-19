package commands;

import connection.Response;
import connection.User;
import dataBase.DataBaseManager;

import java.io.Serial;
import java.io.Serializable;

public class AuthorizationCommand implements Command, Serializable {


    @Serial
    private static final long serialVersionUID  = 18L;

    private User user;

    public AuthorizationCommand(User user){

        this.user = user;
    }
    public User getUser() {
        return user;
    }

    @Override
    public Response execute() {
        DataBaseManager dbm = new DataBaseManager();
        return dbm.authorisation(user);
    }
    @Override
    public String getCommandName() {
        return "authorization";
    }
}
