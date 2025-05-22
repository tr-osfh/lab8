package commands;

import connection.Response;
import connection.User;

import java.io.Serial;
import java.io.Serializable;

public interface Command extends Serializable{
    @Serial
    long serialVersionUID = 12345L;

    User getUser();
    Response execute();
    String getCommandName();
    boolean requiresRefresh();
}
