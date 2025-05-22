package commands;

import collection.CollectionManager;
import connection.CommandResponse;
import connection.Response;
import connection.ResponseStatus;
import connection.User;

import java.io.Serial;
import java.io.Serializable;

/**
 * Команда завершения работы приложения.
 * Немедленно останавливает выполнение программы без сохранения данных.
 */
public class ExitCommand implements Command, Serializable {
    @Serial
    private final static long serialVersionUID  = 6L;
    public ExitCommand(){
    }


    public User getUser() {
        return null;
    }

    @Override
    public Response execute() {
        CollectionManager.exit();
        return new Response(ResponseStatus.OK, "Работа приложение завершена.", CommandResponse.EXIT);
    }

    @Override
    public String getCommandName() {
        return "exit";
    }

    public boolean requiresRefresh() {
        return false;
    }
}