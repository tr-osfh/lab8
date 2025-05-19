package commands;


import connection.Response;
import connection.User;
import seClasses.Dragon;

import java.io.Serial;
import java.io.Serializable;

/**
 * Команда добавления нового элемента в коллекцию.
 */
public class AddCommand implements Command, Serializable {

    @Serial
    private static final long serialVersionUID  = 1L;

    private Dragon dragon;
    private User user;
    public AddCommand(Dragon dragon, User user){
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
        return "add {element} : добавить новый элемент в коллекцию";
    }

    @Override
    public String getCommandName() {
        return "add";
    }

}