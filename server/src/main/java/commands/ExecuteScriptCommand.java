package commands;

import connection.CommandResponse;
import connection.Response;
import connection.ResponseStatus;
import connection.User;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExecuteScriptCommand implements Command, Serializable {
    @Serial
    private final static long serialVersionUID  = 5L;
    private final ArrayList<Command> commandStack;
    private User user;
    public ExecuteScriptCommand(ArrayList<Command> commandStack, User user) {
        this.commandStack = commandStack;
        this.user = user;
    }

    public User getUser() {
        return user;
    }


    @Override
    public Response execute() {
        ArrayList<Response> responseList = new ArrayList<>();
        for (Command command : commandStack){
            Response response = command.execute();
            responseList.add(response);
        }
        System.out.println(commandStack);
        return new Response(ResponseStatus.OK, responseList, CommandResponse.EXECUTE_SCRIPT);

    }

    @Override
    public String getCommandName() {
        return "execute_script";
    }

    public boolean requiresRefresh() {
        return true;
    }
}