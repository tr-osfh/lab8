package commands;


import connection.Response;
import connection.User;

import java.io.Serial;
import java.io.Serializable;

/**
 * Команда фильтрации элементов коллекции по началу имени.
 * Выводит все элементы, значение поля name которых начинается с указанной подстроки.
 */
public class FilterStartsWithNameCommand implements Command, Serializable {


    @Serial
    private final static long serialVersionUID  = 8L;

    private final String namePart;
    private User user;

    public FilterStartsWithNameCommand(String namePart, User user){
        this.namePart = namePart;
        this.user = user;
    }
    @Override
    public Response execute() {
        return null;
    }

    @Override
    public String getDescription() {
        return "filter_starts_with_name name : вывести элементы, значение поля name которых начинается с заданной подстроки";
    }

    @Override
    public String getCommandName() {
        return "filter_starts_with_name";
    }
}