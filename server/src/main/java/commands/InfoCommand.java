package commands;

import collection.CollectionManager;
import connection.CommandResponse;
import connection.Response;
import connection.ResponseStatus;
import connection.User;

import java.io.Serial;
import java.io.Serializable;

/**
 * Команда вывода метаинформации о коллекции.
 * Отображает:
 * <li>Тип хранимых данных
 * <li>Дату и время инициализации
 * <li>Текущее количество элементов
 */
public class InfoCommand implements Command, Serializable {
    @Serial
    private final static long serialVersionUID  = 11L;

    private User user;
    public InfoCommand(User user) {
        this.user = user;

    }


    public User getUser() {
        return user;
    }

    @Override
    public Response execute() {
        return new Response(ResponseStatus.OK, CollectionManager.info(user), CommandResponse.INFO, user);
    }

    @Override
    public String getCommandName() {
        return "info";
    }

    public boolean requiresRefresh() {
        return false;
    }
}