package commands;


import collection.CollectionManager;
import connection.CommandResponse;
import connection.Response;
import connection.ResponseStatus;
import connection.User;
import seClasses.Dragon;

import java.io.Serial;
import java.io.Serializable;

import static commands.CommandsList.CommandType.ADD;

/**
 * Команда добавления нового элемента в коллекцию.
 */
public class AddCommand implements Command, Serializable {

    @Serial
    private final static long serialVersionUID  = 1L;
    private Dragon dragon;
    private User user;
    public AddCommand(Dragon dragon, User user){
        this.dragon = dragon;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Response execute(){
        return new Response(
                ResponseStatus.OK,
                CollectionManager.add(dragon, user),
                CommandResponse.ADD,
                user
        );
    }

    @Override
    public String getCommandName() {
        return "add";
    }

    public boolean requiresRefresh() {
        return true;
    }
}