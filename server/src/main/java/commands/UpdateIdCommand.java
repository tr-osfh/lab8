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
 * Команда обновления элемента коллекции по ID.
 * Заменяет данные существующего элемента новыми значениями, полученными через интерактивный ввод.
 */
public class UpdateIdCommand implements Command, Serializable {

    @Serial
    private final static long serialVersionUID  = 16L;
    private Long id;
    private Dragon dragon;
    private User user;
    public UpdateIdCommand(Long id, Dragon dragon, User user) {
        this.id = id;
        this.dragon = dragon;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Response execute() {
        return new Response(ResponseStatus.OK, CollectionManager.updateById(id, dragon, user), CommandResponse.UPDATE);
    }

    @Override
    public String getCommandName(){
        return "update";
    }
}