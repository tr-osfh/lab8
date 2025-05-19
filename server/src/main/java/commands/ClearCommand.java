package commands;

import collection.CollectionManager;
import connection.CommandResponse;
import connection.Response;
import connection.ResponseStatus;
import connection.User;

import java.io.Serial;
import java.io.Serializable;

/**
 * Команда полной очистки коллекции.
 * Удаляет все элементы из коллекции без возможности восстановления.
 */
public class ClearCommand implements Command, Serializable {
    @Serial
    private final static long serialVersionUID  = 3L;
    private User user;

    public ClearCommand(CollectionManager manager, User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Response execute() {
        CollectionManager.clear(user);
        return new Response(ResponseStatus.OK, "Коллекция успешно удалена.", CommandResponse.CLEAR);
    }

    @Override
    public String getCommandName() {
        return "clear";
    }
}