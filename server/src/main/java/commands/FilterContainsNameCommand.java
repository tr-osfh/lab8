package commands;


import collection.CollectionManager;
import connection.CommandResponse;
import connection.Response;
import connection.ResponseStatus;
import connection.User;
import seClasses.Dragon;

import java.io.Serial;
import java.io.Serializable;
import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Команда фильтрации элементов коллекции по содержанию подстроки в имени.
 * Выводит все элементы, значение поля name которых содержит указанную подстроку.
 */
public class FilterContainsNameCommand implements Command, Serializable {

    @Serial
    private final static long serialVersionUID  = 7L;

    private String namePart;

    private User user;
    public FilterContainsNameCommand(String namePart, User user){
        this.namePart = namePart;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Response execute() {
        String response;
        PriorityBlockingQueue<Dragon> dragons = CollectionManager.filterContainsName(namePart);

        if(dragons.isEmpty()){
            response = "isEmpty";
        }else {
            response = "Success";
        }

        return new Response(ResponseStatus.OK, response, dragons, CommandResponse.FILTER_CONTAINS_NAME);

    }

    @Override
    public String getCommandName() {
        return "filter_contains_name";
    }

    public boolean requiresRefresh() {
        return false;
    }
}
