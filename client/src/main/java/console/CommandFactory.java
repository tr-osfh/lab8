package console;

import commands.*;
import connection.User;
import objectsCreation.CreateDragon;
import objectsCreation.UserAuthorization;
import objectsCreation.UserRegistration;
import seClasses.Dragon;

public class CommandFactory {

    private static final ReadController rc = new ReadController();

    public static Command createCommand(CommandsList command, String[] args, User user){
        if (user != null){
            return switch (command) {
                case HELP -> new HelpCommand(user);
                case INFO -> new InfoCommand(user);
                case SHOW -> new ShowCommand(user);
                case ADD -> {
                    Dragon dragon;
                    CreateDragon dragonCreator = new CreateDragon(rc, user);
                    dragon = dragonCreator.create();
                    yield new AddCommand(dragon, user);
                }
                case UPDATE -> {
                    if (args.length != 2) {
                        rc.printLine("Не верное колличество аргументов. Введите команду: \n");
                        yield null;
                    }
                    try {
                        Long id = Long.parseLong(args[1]);
                        Dragon dragon;
                        CreateDragon dragonCreator = new CreateDragon(rc, user);
                        dragon = dragonCreator.create();
                        dragon.setId(id);
                        yield new UpdateIdCommand(id, dragon, user);
                    } catch (NumberFormatException e) {
                        rc.printLine("Проверьте ID. Введите команду: \n");
                        yield null;
                    }
                }
                case REMOVE_BY_ID -> {
                    if (args.length != 2) {
                        rc.printLine("Не верное колличество аргументов. Введите команду: \n");
                        yield null;
                    }
                    try {
                        Long id = Long.parseLong(args[1]);
                        yield new RemoveByIdCommand(id, user);
                    } catch (NumberFormatException e) {
                        rc.printLine("Проверьте ID. Введите команду: \n");
                        yield null;
                    }
                }
                case CLEAR -> new ClearCommand(user);
                case EXIT -> new ExitCommand();
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
                    if (args.length != 2) {
                        rc.printLine("Не верное колличество аргументов. Введите команду: \n");
                        yield null;
                    }
                    try {
                        String link = args[1];
                        yield new FilterContainsNameCommand(link, user);
                    } catch (Exception e) {
                        rc.printLine("Проверьте ввод. Введите команду: \n");
                        yield null;
                    }
                }
                case FILTER_STARTS_WITH_NAME -> {
                    if (args.length != 2) {
                        rc.printLine("Не верное колличество аргументов. Введите команду: \n");
                        yield null;
                    }
                    try {
                        String link = args[1];
                        yield new FilterStartsWithNameCommand(link, user);
                    } catch (Exception e) {
                        rc.printLine("Проверьте ввод. Введите команду: \n");
                        yield null;
                    }
                }
                default -> {
                    rc.printLine("Неизвестная команада! Введите команду: \n");
                    yield null;
                }
            };
        } else {
             return switch (command){
                 case REGISTRATION -> {
                     User userTMP;
                     UserRegistration userCreator = new UserRegistration(rc);
                     userTMP = userCreator.create();
                     yield new RegistrationCommand(userTMP);
                 }
                 case AUTHORIZATION -> {
                     User userTMP;
                     UserAuthorization userCreator = new UserAuthorization(rc);
                     userTMP = userCreator.create();
                     yield new AuthorizationCommand(userTMP);
                 } default -> {
                     rc.printLine("Сначала нужно войти в аккаунт. Используйте registration + login password или authorization + login password: \n");
                     yield null;
                 }
            };
        }
    }
}
