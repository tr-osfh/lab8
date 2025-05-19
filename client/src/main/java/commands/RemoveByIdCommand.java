package commands;


import connection.Response;
import connection.User;

import java.io.Serial;
import java.io.Serializable;

/**
 * Команда удаления элемента коллекции по уникальному идентификатору (ID).
 * Осуществляет поиск элемента с указанным ID и его удаление при наличии.
 */
public class RemoveByIdCommand implements Command, Serializable {

    @Serial
    private static final long serialVersionUID  = 12L;
    private User user;
    private Long id;

    public RemoveByIdCommand(Long id, User user) {
        this.id = id;
        this.user = user;
    }

    @Override
    public Response execute() {
        return null;
    }

    @Override
    public String getDescription() {
        return "remove_by_id id : удалить элемент из коллекции по его id";
    }

    @Override
    public String getCommandName() {
        return "remove_by_id";
    }
}