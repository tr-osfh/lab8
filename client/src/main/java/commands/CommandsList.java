package commands;

public enum CommandsList {
    HELP(HelpCommand.class, "help : вывести справку по доступным командам"),
    INFO(InfoCommand.class, "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)"),
    SHOW(ShowCommand.class, "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении"),
    ADD(AddCommand.class, "add {element}: добавить новый элемент в коллекцию"),
    UPDATE(UpdateIdCommand.class, "update id {element} : обновить значение элемента коллекции, id которого равен заданному"),
    REMOVE_BY_ID(RemoveByIdCommand.class, "remove_by_id id : удалить элемент из коллекции по его id"),
    CLEAR(ClearCommand.class, "clear : очистить коллекцию"),
    EXECUTE_SCRIPT(ExecuteScriptCommand.class, "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме."),
    EXIT(ExitCommand.class, "exit : завершить программу"),
    HEAD(HeadCommand.class, "head : вывести первый элемент коллекции"),
    ADD_IF_MIN(AddIfMinCommand.class, "add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции"),
    REMOVE_LOWER(RemoveLowerCommand.class, "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный"),
    SUM_OF_AGE(SumOfAgeCommand.class, "sum_of_age : вывести сумму значений поля age для всех элементов коллекции"),
    FILTER_CONTAINS_NAME(FilterContainsNameCommand.class, "filter_contains_name name : вывести элементы, значение поля name которых содержит заданную подстроку"),
    FILTER_STARTS_WITH_NAME(FilterStartsWithNameCommand.class, "filter_starts_with_name name : вывести элементы, значение поля name которых начинается с заданной подстроки"),
    REGISTRATION(RegistrationCommand.class, "registration: зарегестрироваться в приложении"),
    AUTHORIZATION(AuthorizationCommand.class, "authorization: войти в существующий аккаунт"),
    DEFAULT(DefaultCommand.class, "");

    CommandsList(Class<? extends Command> CommandClass, String description) {
    }
}
