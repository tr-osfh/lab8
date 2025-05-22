package commands;


import collection.CollectionManager;
import connection.CommandResponse;
import connection.Response;
import connection.ResponseStatus;
import connection.User;

import java.io.Serial;
import java.io.Serializable;

/**
 * Команда вывода всех элементов коллекции в строковом представлении.
 * Отображает полный список элементов коллекции или сообщение о пустой коллекции.
 */
public class ShowCommand implements Command, Serializable {
    @Serial
    private final static long serialVersionUID  = 14L;

    private User user;
    public ShowCommand(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Response execute() {
        return new Response(ResponseStatus.OK, CollectionManager.show(), CommandResponse.SHOW);
    }

    @Override
    public String getCommandName() {
        return "show";
    }

    public boolean requiresRefresh() {
        return false;
    }
}