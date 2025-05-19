package commands;

import connection.Response;
import connection.User;

import java.io.Serial;
import java.io.Serializable;

/**
 * Команда полной очистки коллекции.
 * Удаляет все элементы из коллекции без возможности восстановления.
 */
public class ClearCommand implements Command, Serializable {

    @Serial
    private static final long serialVersionUID  = 3L;

    private User user;
    public ClearCommand(User user){
        this.user = user;
    }

    @Override
    public Response execute() {
        return null;
    }

    @Override
    public String getDescription() {
        return "clear : очистить коллекцию";
    }
    @Override
    public String getCommandName() {
        return "clear";
    }
}