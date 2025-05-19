package commands;


import connection.Response;
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
    private static final long serialVersionUID  = 16L;

    private Long id;
    private Dragon dragon;
    private User user;

    public UpdateIdCommand(Long id, Dragon dragon, User user) {
        this.id = id;
        this.dragon = dragon;
        this.user = user;
    }

    @Override
    public Response execute() {
        return null;
    }

    @Override
    public String getDescription(){
        return "update id {element} : обновить значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public String getCommandName() {
        return "update";
    }
}