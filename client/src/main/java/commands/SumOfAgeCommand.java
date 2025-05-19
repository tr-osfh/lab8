package commands;


import connection.Response;
import connection.User;

import java.io.Serial;
import java.io.Serializable;

/**
 * Команда вычисления суммарного возраста элементов коллекции.
 * Суммирует значения поля age всех драконов, игнорируя элементы с неустановленным возрастом (null).
 */
public class SumOfAgeCommand implements Command, Serializable {


    @Serial
    private static final long serialVersionUID  = 15L;
    private User user;
    public SumOfAgeCommand(User user){
        this.user = user;
    }

    @Override
    public Response execute() {
        return null;
    }

    @Override
    public String getDescription() {
        return "sum_of_age : вывести сумму значений поля age для всех элементов коллекции (игнорирует null)";
    }

    @Override
    public String getCommandName() {
        return "sum_of_age";
    }
}
