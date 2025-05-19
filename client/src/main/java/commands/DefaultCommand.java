package commands;

import connection.Response;

import java.io.Serial;
import java.io.Serializable;

public class DefaultCommand implements Command, Serializable {

    @Serial
    private static final long serialVersionUID  = 4L;

    @Override
    public Response execute() {
        return null;
    }

    @Override
    public String getDescription() {
        return "";
    }
    @Override
    public String getCommandName() {
        return "";
    }
}
