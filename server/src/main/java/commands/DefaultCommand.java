package commands;

import connection.CommandResponse;
import connection.Response;
import connection.ResponseStatus;
import connection.User;

import java.io.Serial;
import java.io.Serializable;

public class DefaultCommand implements Command, Serializable {

    @Serial
    private static final long serialVersionUID = 4L;

    public User getUser() {
        return null;
    }

    @Override
    public Response execute() {
        return new Response(ResponseStatus.INFO, "Неизветная команда, воспользуйтесь help, чтобы посмотреть список доступных команд.", CommandResponse.DEFAULT);
    }
    @Override
    public String getCommandName() {
        return "";
    }
}
