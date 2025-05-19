package commands;


import connection.Response;
import connection.User;
import seClasses.Dragon;

import java.io.Serial;
import java.io.Serializable;

/**
 * Команда условного добавления элемента в коллекцию.
 * Добавляет новый элемент только если его значение (по координате X)
 * меньше минимального значения в текущей коллекции.
 */
public class AddIfMinCommand implements Command, Serializable {

    @Serial
    private static final long serialVersionUID  = 2L;

    private Dragon dragon;
    private User user;

    public AddIfMinCommand(Dragon dragon, User user) {
        this.dragon = dragon;
        this.user = user;
    }
    public User getUser() {
        return user;
    }

    @Override
    public Response execute() {
        return null;
    }

    @Override
    public String getDescription() {
        return "add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
    }

    @Override
    public String getCommandName() {
        return "add_if_min";
    }
}