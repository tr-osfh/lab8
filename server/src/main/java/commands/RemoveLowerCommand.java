package commands;

import collection.CollectionManager;
import connection.CommandResponse;
import connection.Response;
import connection.ResponseStatus;
import connection.User;
import seClasses.Dragon;

import java.io.Serial;
import java.io.Serializable;

/**
 * Команда удаления элементов, меньших чем заданный.
 * Удаляет все элементы коллекции, значение координаты X которых меньше,
 * чем у указанного дракона. Требует интерактивного ввода параметров дракона.
 */
public class RemoveLowerCommand implements Command, Serializable {
    @Serial
    private final static long serialVersionUID  = 13L;
    private final Dragon dragon;
    private User user;
    public RemoveLowerCommand(Dragon dragon, User user){
        this.dragon = dragon;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Response execute() {
        return new Response(ResponseStatus.OK, CollectionManager.removeLower(dragon, user), CommandResponse.REMOVE_LOWER);
    }

    @Override
    public String getCommandName() {
        return "remove_lower";
    }
}