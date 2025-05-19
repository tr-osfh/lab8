package commands;


import collection.CollectionManager;
import connection.CommandResponse;
import connection.Response;
import connection.ResponseStatus;
import connection.User;

import java.io.Serial;
import java.io.Serializable;

/**
 * Команда удаления элемента коллекции по уникальному идентификатору (ID).
 * Осуществляет поиск элемента с указанным ID и его удаление при наличии.
 */
public class RemoveByIdCommand implements Command, Serializable {
    @Serial
    private final static long serialVersionUID  = 12L;
    private Long id;
    private User user;
    public RemoveByIdCommand(Long id, User user) {
        this.id = id;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Response execute() {
        return new Response(ResponseStatus.OK, CollectionManager.removeById(id, user), CommandResponse.REMOVE_BY_ID);
    }


    @Override
    public String getCommandName() {
        return "remove_by_id";
    }
}