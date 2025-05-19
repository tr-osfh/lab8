package console;

import commands.*;
import connection.User;
import objectsCreation.CreateDragon;
import seClasses.Dragon;

public class CommandFactoryScript {

    private static final ReadController rc = new ReadController();

    public static Command createCommand(CommandsList command, String[] args, User user){
        return switch (command){
            case HELP -> new HelpCommand(user);
            case INFO -> new InfoCommand(user);
            case SHOW -> new ShowCommand(user);
            case ADD -> {
                Dragon dragon;
                CreateDragon dragonCreator = new CreateDragon(rc, user);
                dragon = dragonCreator.create();
                yield new AddCommand(dragon, user);}
            case UPDATE -> {
                if (args.length != 2){
                    rc.printLine("Не верное колличество аргументов.");
                    yield null;
                } try {
                    Long id = Long.parseLong(args[1]);
                    Dragon dragon;
                    CreateDragon dragonCreator = new CreateDragon(rc, user);
                    dragon = dragonCreator.create();
                    dragon.setId(id);
                    yield new UpdateIdCommand(id, dragon, user);
                } catch (NumberFormatException e){
                    rc.printLine("Проверьте ID.");
                    yield null;
                }
            }
            case REMOVE_BY_ID -> {
                if (args.length != 2){
                    rc.printLine("Не верное колличество аргументов.");
                    yield null;
                } try {
                    Long id = Long.parseLong(args[1]);
                    yield new RemoveByIdCommand(id, user);
                } catch (NumberFormatException e){
                    rc.printLine("Проверьте ID.");
                    yield null;
                }
            }
            case HEAD -> new HeadCommand(user);
            case ADD_IF_MIN -> {
                Dragon dragon;
                CreateDragon dragonCreator = new CreateDragon(rc, user);
                dragon = dragonCreator.create();
                yield new AddIfMinCommand(dragon, user);
            }
            case REMOVE_LOWER -> {
                Dragon dragon;
                CreateDragon dragonCreator = new CreateDragon(rc, user);
                dragon = dragonCreator.create();
                yield new RemoveLowerCommand(dragon, user);
            }
            case SUM_OF_AGE -> new SumOfAgeCommand(user);
            case FILTER_CONTAINS_NAME -> {
                if (args.length != 2){
                    rc.printLine("Не верное колличество аргументов.");
                    yield null;
                } try {
                    String link = args[1];
                    yield new FilterContainsNameCommand(link, user);
                } catch (Exception e){
                    rc.printLine("Проверьте ввод.");
                    yield null;
                }
            }
            case FILTER_STARTS_WITH_NAME -> {
                if (args.length != 2){
                    rc.printLine("Не верное колличество аргументов.");
                    yield null;
                } try {
                    String link = args[1];
                    yield new FilterStartsWithNameCommand(link, user);
                } catch (Exception e){
                    rc.printLine("Проверьте ввод.");
                    yield null;
                }
            }
            default -> {
                yield null;
            }
        };
    }
}
