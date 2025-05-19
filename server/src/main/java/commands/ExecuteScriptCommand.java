package commands;

import connection.CommandResponse;
import connection.Response;
import connection.ResponseStatus;
import connection.User;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

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
        StringBuilder output = new StringBuilder();
        commandStack.forEach(command -> output.append(command.execute().getResponse()).append("\n"));
        System.out.println(commandStack);
        return new Response(ResponseStatus.OK, output.substring(0, output.length() - 1), CommandResponse.EXECUTE_SCRIPT);

    }

    @Override
    public String getCommandName() {
        return "execute_script";
    }
}