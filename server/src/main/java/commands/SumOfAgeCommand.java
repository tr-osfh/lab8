package commands;


import collection.CollectionManager;
import connection.CommandResponse;
import connection.Response;
import connection.ResponseStatus;
import connection.User;

import java.io.Serial;
import java.io.Serializable;

/**
 * Команда вычисления суммарного возраста элементов коллекции.
 * Суммирует значения поля age всех драконов, игнорируя элементы с неустановленным возрастом (null).
 */
public class SumOfAgeCommand implements Command, Serializable {
    @Serial
    private final static long serialVersionUID  = 15L;
    private User user;
    public SumOfAgeCommand(User user){
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Response execute() {
        return new Response(ResponseStatus.OK, CollectionManager.sumOfAge(), CommandResponse.SUM_OF_AGE);
    }

    @Override
    public String getCommandName() {
        return "sum_of_age";
    }
}
