package commands;

import collection.CollectionManager;
import connection.CommandResponse;
import connection.Response;
import connection.ResponseStatus;
import connection.User;
import seClasses.Dragon;

import java.io.Serial;
import java.io.Serializable;

public class AddIfMinCommand implements Command, Serializable {

    @Serial
    private final static long serialVersionUID  = 2L;
    private Dragon dragon;
    private User user;
    public AddIfMinCommand(Dragon dragon, User user) {
        this.user = user;
        this.dragon = dragon;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Response execute() {
        try {
            return new Response(ResponseStatus.OK, CollectionManager.addIfMin(dragon, user), CommandResponse.ADD_IF_MIN);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }

    }

    @Override
    public String getCommandName() {
        return "add_if_min";
    }

    public boolean requiresRefresh() {
        return true;
    }
}