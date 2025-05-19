package commands;


import connection.Response;
import connection.User;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Команда выполнения скрипта из файла. Читает и исполняет последовательность команд,
 * включая создание объектов Dragon с валидацией. Обрабатывает вложенные скрипты с защитой от рекурсии.
 */
public class ExecuteScriptCommand implements Command, Serializable {
    @Serial
    private final static long serialVersionUID  = 5L;
    private final ArrayList<Command> commandStack;
    private User user;

    public ExecuteScriptCommand(ArrayList<Command> commandStack, User user) {
        this.commandStack = commandStack;
        this.user = user;
    }
    @Override
    public Response execute() {
        return null;
    }

    @Override
    public String getDescription() {
        return "execute_script file_path : считать и исполнить скрипт из указанного файла. Поддерживает команды с объектами.";
    }
    @Override
    public String getCommandName() {
        return "execute_script";
    }
}