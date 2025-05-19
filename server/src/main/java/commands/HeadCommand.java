package commands;


import collection.CollectionManager;
import connection.CommandResponse;
import connection.Response;
import connection.ResponseStatus;
import connection.User;

import java.io.Serial;
import java.io.Serializable;

/**
 * Команда вывода первого элемента коллекции.
 * Отображает первого элемент PriorityQueue без изменения коллекции.
 */
public class HeadCommand implements Command, Serializable {
    @Serial
    private final static long serialVersionUID  = 9L;

    private User user;
    public HeadCommand(User user) {
        this.user = user;
    }


    public User getUser() {
        return user;
    }


    @Override
    public Response execute() {
        return new Response(ResponseStatus.OK, CollectionManager.head(), CommandResponse.HEAD);
    }

    @Override
    public String getCommandName() {
        return "head";
    }
}