package console;

import commands.Command;
import commands.CommandsList;

public class CommandDecoder {
    public static CommandsList getCommandType(String input){
        try {
            return CommandsList.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            return CommandsList.DEFAULT;
        }
    }
}
