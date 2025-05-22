package commands;


import collection.CollectionManager;
import connection.CommandResponse;
import connection.Response;
import connection.ResponseStatus;
import connection.User;
import seClasses.Dragon;

import java.io.Serial;
import java.io.Serializable;
import java.util.concurrent.PriorityBlockingQueue;

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
        String response;
        PriorityBlockingQueue<Dragon> dragons = CollectionManager.filterStartsWithName(namePart);

        if(dragons.isEmpty()){
            response = "isEmpty";
        }else {
            response = "Success";
        }

        return new Response(ResponseStatus.OK, response, dragons, CommandResponse.FILTER_STARTS_WITH_NAME);

    }

    @Override
    public String getCommandName() {
        return "filter_starts_with_name";
    }

    public boolean requiresRefresh() {
        return false;
    }
}