package commands;

import connection.Response;
import connection.User;

import java.io.Serial;
import java.io.Serializable;

public class RegistrationCommand implements Command, Serializable {

    @Serial
    private static final long serialVersionUID  = 17L;

    private User user;

    public RegistrationCommand(User user){
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
        return "reg";
    }
}
