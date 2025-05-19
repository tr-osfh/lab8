package commands;

import connection.Response;
import connection.User;
import seClasses.Dragon;

import java.io.Serial;
import java.io.Serializable;

/**
 * Команда удаления элементов, меньших чем заданный.
 * Удаляет все элементы коллекции, значение координаты X которых меньше,
 * чем у указанного дракона. Требует интерактивного ввода параметров дракона.
 */
public class RemoveLowerCommand implements Command, Serializable  {

    @Serial
    private static final long serialVersionUID  = 13L;

    private Dragon dragon;
    private User user;

    public RemoveLowerCommand(Dragon dragon, User user){
        this.dragon = dragon;
        this.user = user;
    }

    @Override
    public Response execute() {
        return null;
    }

    /**
     * Возвращает описание команды для справки
     * @return Форматированная строка с синтаксисом и назначением
     */
    @Override
    public String getDescription() {
        return "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный";
    }

    @Override
    public String getCommandName() {
        return "remove_lower";
    }
}