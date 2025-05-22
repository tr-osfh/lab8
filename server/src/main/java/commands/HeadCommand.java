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
 * Команда вывода первого элемента коллекции.
 * Отображает первого элемент PriorityQueue без изменения коллекции.
 */
public class HeadCommand implements Command, Serializable {
    @Serial
    private final static long serialVersionUID  = 9L;

    private User user;
    public HeadCommand(User user) {
        this.user = user;
    }


    public User getUser() {
        return user;
    }


    @Override
    public Response execute() {
        String response;
        PriorityBlockingQueue<Dragon> dragons = CollectionManager.head();

        if(dragons.isEmpty()){
            response = "isEmpty";
        }else {
            response = "Success";
        }

        return new Response(ResponseStatus.OK, response, dragons, CommandResponse.HEAD);

    }

    @Override
    public String getCommandName() {
        return "head";
    }

    public boolean requiresRefresh() {
        return false;
    }
}