package commands;


import connection.Response;
import connection.User;

import java.io.Serial;
import java.io.Serializable;

/**
 * Команда вывода первого элемента коллекции.
 * Отображает первого элемент PriorityQueue без изменения коллекции.
 */
public class HeadCommand implements Command, Serializable {


    @Serial
    private static final long serialVersionUID  = 9L;

    private User user;
    public HeadCommand(User user) {
        this.user = user;
    }

    @Override
    public Response execute() {
        return null;
    }

    @Override
    public String getDescription() {
        return "head : вывести первый элемент коллекции";
    }

    @Override
    public String getCommandName() {
        return "head";
    }
}