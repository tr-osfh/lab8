package commands;


import collection.CollectionManager;
import connection.CommandResponse;
import connection.Response;
import connection.ResponseStatus;
import connection.User;

import java.io.Serial;
import java.io.Serializable;

/**
 * Команда фильтрации элементов коллекции по началу имени.
 * Выводит все элементы, значение поля name которых начинается с указанной подстроки.
 */
public class FilterStartsWithNameCommand implements Command, Serializable {

    @Serial
    private final static long serialVersionUID  = 8L;

    private final String namePart;
    private User user;

    public FilterStartsWithNameCommand(String namePart, User user){
        this.namePart = namePart;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Response execute() {
        return new Response(ResponseStatus.OK, CollectionManager.filterStartsWithName(namePart), CommandResponse.FILTER_STARTS_WITH_NAME);
    }

    @Override
    public String getCommandName() {
        return "filter_starts_with_name";
    }
}