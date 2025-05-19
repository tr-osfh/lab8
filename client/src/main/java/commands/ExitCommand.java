package commands;


import connection.Response;

import java.io.Serial;
import java.io.Serializable;

/**
 * Команда завершения работы приложения.
 * Немедленно останавливает выполнение программы без сохранения данных.
 */
public class ExitCommand implements Command, Serializable {


    @Serial
    private static final long serialVersionUID  = 6L;

    public ExitCommand(){
    }

    @Override
    public Response execute() {
        return null;
    }

    @Override
    public String getDescription() {
        return "exit : завершить программу (без сохранения в файл)";
    }

    @Override
    public String getCommandName() {
        return "exit";
    }
}