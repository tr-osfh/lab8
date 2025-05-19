package collection;

import commands.CommandManager;
import connection.User;
import console.ConsoleManager;
import dataBase.DataBaseManager;
import seClasses.Dragon;

import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * Менеджер коллекции драконов.
 */
public class CollectionManager {

    static ReentrantLock lock = new ReentrantLock();
    private static CommandManager commandManager;
    private static final Validator validator = new Validator();
    private static final java.time.LocalDateTime creationDate = java.time.LocalDateTime.now();
    private static final DataBaseManager dbm = new DataBaseManager();
    private static PriorityBlockingQueue<Dragon> dragons = dbm.selectDragons();


    public static PriorityBlockingQueue<Dragon> getDragons() {
        return dragons;
    }


    public CommandManager getCommandManager() {
        return commandManager;
    }


    public static String show() {
        lock.lock();
        try{
            return (dragons.isEmpty()
                    ? "Коллекция пуста."
                    : dragons.stream()
                    .map(Dragon::toString)
                    .collect(Collectors.joining("\n")) + "\n");
        } finally {
            lock.unlock();
        }

    }


    public static String add(Dragon dragon, User user) {

        lock.lock();
        try{
            boolean alreadyExists = dragons.stream()
                    .anyMatch(d -> d.equals(dragon));

            if (alreadyExists) {
                return "Этот дракон уже есть в коллекции.\n";
            }

            if (validator.getValid(dragon) == null) {
                return "Параметры дракона не верны.\n";
            }

            Integer id = dbm.addDragon(dragon, user);
            if(id != null){
                dragon.setId(id.longValue());
                dragons.add(dragon);
            } else {
                return "Ошибка при добавлении дракона в базу данных.";
            }
        } finally {
            lock.unlock();
        }

        return "Дракон успешно добавлен.\n";
    }

    public static String info(User user) {
        lock.lock();
        try{
            return "Тип хранимых данных в коллекции: Dragon\n" +
                    "Дата и время инициализации: " + creationDate + "\n" +
                    "Колличество элементов в коллеции: " + dragons.size() + "\n" +
                    "Драконов принадлежащих вам: " + dragons.stream().filter(d -> d.getUserLogin().equals(user.getLogin())).count();
        } finally {
            lock.unlock();
        }

    }


    public static String updateById(Long dragonId, Dragon updatedDragon, User user) {
        lock.lock();
        try{
            if (validator.getValid(updatedDragon) == null) {
                return "Параметры дракона не верны.\n";
            }

            Optional<Dragon> existingDragon = dragons.stream()
                    .filter(d -> d.getId() == (dragonId) && d.getUserLogin().equals(user.getLogin()))
                    .findFirst();

            if (existingDragon.isPresent()) {
                if (dbm.updateDragon(dragonId, updatedDragon, user)){
                    dragons.remove(existingDragon.get());
                    updatedDragon.setId(dragonId);
                    dragons.add(updatedDragon);
                    return "Данные дракона успешно обновлены.\n";
                } else {
                    return "Ошибка при добавлении данных в базу данных.";
                }
            } else {
                return "Дракона с ID " + dragonId + " нет в коллекции или он вам не принадлежит.\n";
            }
        } finally {
            lock.unlock();
        }

    }

    public static String removeById(Long dragonId, User user) {
        lock.lock();
        try{
            Optional<Dragon> dragonToRemove = dragons.stream()
                    .filter(d -> d.getId() == (dragonId) && d.getUserLogin().equals(user.getLogin()))
                    .findFirst();

            if (dragonToRemove.isPresent()) {
                if (dbm.removeDragon(dragonId, user)){
                    dragons.remove(dragonToRemove.get());
                } else {
                    return "Ошибка при добавлении данных в базу данных.";
                }

                return "Дракон с ID " + dragonId + " успешно удален.\n";
            } else {
                return "Дракона с ID " + dragonId + " нет в коллекции или он вам не принадлежит.\n";
            }
        } finally {
            lock.unlock();
        }

    }


    public static void exit() {
        System.exit(0);
    }

    public static String clear(User user) {

        lock.lock();
        try{
            List<Dragon> toRemove = dragons.stream()
                    .filter(d -> d.getUserLogin().equals(user.getLogin()))
                    .collect(Collectors.toList());

            if (dbm.clear(user)){
                dragons.removeAll(toRemove);
                return "Коллекция очищена.\n";
            } else {
                return "Ошибка при обновлении данных.";
            }
        } finally {
            lock.unlock();
        }

}

    public static String head() {
        lock.lock();
        try{
            return dragons.stream()
                    .findFirst()
                    .map(Dragon::toString)
                    .orElse("Коллекция пуста.\n");
        } finally {
            lock.unlock();
        }

    }

    public static String addIfMin(Dragon dragon, User user) {
        lock.lock();
        try{
            boolean alreadyExists = dragons.stream()
                    .anyMatch(d -> d.equals(dragon));

            if (alreadyExists) {
                return "Этот дракон уже есть в коллекции.\n";
            }

            if (validator.getValid(dragon) == null) {
                return "Параметры дракона не верны.\n";
            }

            boolean isMin = dragons.isEmpty() ||
                    dragons.stream()
                            .allMatch(d -> dragon.getCoordinates().getX() < d.getCoordinates().getX());

            if (isMin) {
                Integer id = dbm.addDragon(dragon, user);
                if(id != null){
                    dragon.setId(id.longValue());
                    dragons.add(dragon);
                } else {
                    return "Ошибка при добавлении дракона в базу данных.";
                }

                return "Дракон успешно добавлен.\n";
            } else {
                return "Данный дракон не имеет минимального значения.\n";
            }
        } finally {
            lock.unlock();
        }

    }


    public static String removeLower(Dragon dragon, User user) {
        lock.lock();
        try{
            if (dragons.isEmpty()) {
                return "Коллекция драконов пуста.\n";
            }

            List<Dragon> toRemove = dragons.stream()
                    .filter(d -> d.getCoordinates().getX() < dragon.getCoordinates().getX() && d.getUserLogin().equals(user.getLogin()))
                    .collect(Collectors.toList());

            if (!toRemove.isEmpty()) {
                if (dbm.removeLower(dragon, user)){
                    dragons.removeAll(toRemove);
                } else {
                    return "Ошибка в базе данных";
                }
                return String.format("Удалено %d драконов, меньших чем заданный.\n", toRemove.size());
            } else {
                return "Драконов меньше, чем заданный, нет в коллекции или они вам не принадлежат.\n";
            }
        } finally {
            lock.unlock();
        }

    }


    public static String sumOfAge() {

        lock.lock();
        try{
            if (dragons.isEmpty()) {
                return "В коллекции нет драконов.\n";
            }

            long sum = dragons.stream()
                    .map(Dragon::getAge)
                    .filter(Objects::nonNull)
                    .mapToLong(Long::longValue)
                    .sum();

            return sum == 0L
                    ? "Нет данных о возрасте драконов.\n"
                    : "Суммарный возраст всех драконов: " + sum + "\n";
        } finally {
            lock.unlock();
        }

    }


    public static String filterContainsName(String name) {
        lock.lock();
        try{
            if (dragons.isEmpty()) {
                return "Поиск не дал результатов.\n";
            }

            String result = dragons.stream()
                    .filter(dragon -> dragon.getName().contains(name))
                    .map(Dragon::toString)
                    .collect(Collectors.joining("\n"));

            return result.isEmpty()
                    ? "Поиск не дал результатов.\n"
                    : result + "\n";
        } finally {
            lock.unlock();
        }

    }

    public static String filterStartsWithName(String name) {
        lock.lock();
        try{
            int len = name.length();

            if (dragons.isEmpty()) {
                return "Поиск не дал результатов.\n";
            }

            String result = dragons.stream()
                    .filter(dragon -> dragon.getName().length() >= len)
                    .filter(dragon -> dragon.getName().substring(0, len).equals(name))
                    .map(Dragon::toString)
                    .collect(Collectors.joining("\n"));

            return result.isEmpty()
                    ? "Поиск не дал результатов.\n"
                    : result + "\n";
        }finally {
            lock.unlock();
        }
    }
}