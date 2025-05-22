package commands;

import connection.Response;
import connection.User;
import dataBase.DataBaseManager;

import java.io.Serial;
import java.io.Serializable;

public class RegistrationCommand implements Command, Serializable {

    @Serial
    private static final long serialVersionUID  = 17L;

    private User user;

    public RegistrationCommand(User user){
        this.user = user;
    }


    public User getUser() {
        return user;
    }

    @Override
    public Response execute() {
        DataBaseManager dbm = new DataBaseManager();
        return dbm.registration(user);
    }

    @Override
    public String getCommandName() {
        return "registration";
    }

    public boolean requiresRefresh() {
        return false;
    }
}
