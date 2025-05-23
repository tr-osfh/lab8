package commands;

public class CommandsList {

    public enum CommandType {
        HELP(HelpCommand.class, "helpHelp"),
        INFO(InfoCommand.class, "infoHelp"),
        SHOW(ShowCommand.class, ""),
        ADD(AddCommand.class, "addHelp"),
        UPDATE(UpdateIdCommand.class, "updateHelp"),
        REMOVE_BY_ID(RemoveByIdCommand.class, "remove_by_idHelp"),
        CLEAR(ClearCommand.class, "clearHelp"),
        EXECUTE_SCRIPT(ExecuteScriptCommand.class, "execute_scriptHelp"),
        EXIT(ExitCommand.class, "exitHelp"),
        HEAD(HeadCommand.class, "headHelp"),
        ADD_IF_MIN(AddIfMinCommand.class, "add_if_minHelp"),
        REMOVE_LOWER(RemoveLowerCommand.class, "remove_lowerHelp"),
        SUM_OF_AGE(SumOfAgeCommand.class, "sum_of_ageHelp"),
        FILTER_CONTAINS_NAME(FilterContainsNameCommand.class, "filter_contains_nameHelp"),
        FILTER_STARTS_WITH_NAME(FilterStartsWithNameCommand.class, "filter_starts_with_nameHelp"),
        REGISTRATION(RegistrationCommand.class, ""),
        AUTHORIZATION(AuthorizationCommand.class, ""),
        DEFAULT(DefaultCommand.class, "");


        private final Class<? extends Command> executableClass;
        private final String description;

        CommandType(Class<? extends Command> executableClass, String description) {
            this.executableClass = executableClass;
            this.description = description;
        }

        public Class<? extends Command> getExecutableClass() {
            return executableClass;
        }
        public String getDescription(){
            return description;
        }
    }
}
