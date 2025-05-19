package commands;

import connection.Response;

import java.io.Serial;
import java.io.Serializable;


public interface Command extends Serializable {
    @Serial
    long serialVersionUID = 12345L;
    Response execute();
    String getDescription();
    String getCommandName();
}
