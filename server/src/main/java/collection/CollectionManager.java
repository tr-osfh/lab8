package collection;

import commands.CommandManager;
import connection.Refresher;
import connection.User;
import console.ConsoleManager;
import dataBase.DataBaseManager;
import seClasses.Dragon;
import seClasses.Info;

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
                return "AlreadyExists";
            }

            if (validator.getValid(dragon) == null) {
                return "NotValid";
            }

            Integer id = dbm.addDragon(dragon, user);
            if(id != null){
                dragon.setId(id.longValue());
                dragons.add(dragon);
            } else {
                return "DBeror";
            }
        } finally {
            lock.unlock();
        }
        Refresher.setDragons(dragons);
        return "SuccessAdd";
    }

    public static Info info(User user) {
        lock.lock();
        try{
            return new Info( dragons.size(), "Dragon", creationDate, dragons.stream().filter(d -> d.getUserLogin().equals(user.getLogin())).count());
        } finally {
            lock.unlock();
        }
    }


    public static String updateById(Long dragonId, Dragon updatedDragon, User user) {
        lock.lock();
        try{
            if (validator.getValid(updatedDragon) == null) {
                return "NotValid.";
            }

            Optional<Dragon> existingDragon = dragons.stream()
                    .filter(d -> d.getId() == (dragonId) && d.getUserLogin().equals(user.getLogin()))
                    .findFirst();

            if (existingDragon.isPresent()) {
                if (dbm.updateDragon(dragonId, updatedDragon, user)){
                    dragons.remove(existingDragon.get());
                    updatedDragon.setId(dragonId);
                    dragons.add(updatedDragon);

                    Refresher.setDragons(dragons);
                    return "DragonDataUpdated";
                } else {
                    return "DBerror";
                }
            } else {
                return "NoSuchDragon";
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
                    Refresher.setDragons(dragons);
                    return "DBerror";
                }

                Refresher.setDragons(dragons);
                return "Deleted";
            } else {
                Refresher.setDragons(dragons);
                return "NoSuchDragon";
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
            if (toRemove.isEmpty()) {
                Refresher.setDragons(dragons);
                return "NoOneToClear";
            }
            if (dbm.clear(user)){
                dragons.removeAll(toRemove);
                Refresher.setDragons(dragons);
                    return "SuccessClear";
            } else {
                return "DBerror";
            }
        } finally {
            lock.unlock();
        }

}

    public static PriorityBlockingQueue<Dragon> head() {
        lock.lock();
        try{
            PriorityBlockingQueue<Dragon> d = new PriorityBlockingQueue<>();
            Dragon dragon = dragons.stream().findFirst().orElse(null);
            d.add(dragon);
            return d;
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
                return "AlreadyExists";
            }

            if (validator.getValid(dragon) == null) {
                return "NotValid";
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
                    return "DBerror";
                }
                Refresher.setDragons(dragons);
                return "SuccessAdd";
            } else {
                Refresher.setDragons(dragons);
                return "NotMinimal";
            }
        } finally {
            lock.unlock();
        }

    }


    public static String removeLower(Dragon dragon, User user) {
        lock.lock();
        try{
            if (dragons.isEmpty()) {
                Refresher.setDragons(dragons);
                return "EmptyCollection";
            }

            List<Dragon> toRemove = dragons.stream()
                    .filter(d -> d.getCoordinates().getX() < dragon.getCoordinates().getX() && d.getUserLogin().equals(user.getLogin()))
                    .collect(Collectors.toList());

            if (!toRemove.isEmpty()) {
                if (dbm.removeLower(dragon, user)){
                    dragons.removeAll(toRemove);
                } else {
                    Refresher.setDragons(dragons);
                    return "DBerror";
                }
                Refresher.setDragons(dragons);
                return "AllDelited";
            } else {
                Refresher.setDragons(dragons);
                return "NoLower";
            }
        } finally {
            lock.unlock();
        }

    }


    public static String sumOfAge() {

        if (dragons.isEmpty()) {
            Refresher.setDragons(dragons);
            return "no";
        }

        lock.lock();
        try{
            Long sum = dragons.stream()
                    .map(Dragon::getAge)
                    .filter(Objects::nonNull)
                    .mapToLong(Long::longValue)
                    .sum();

            return String.valueOf(sum);
        } finally {
            lock.unlock();
        }

    }


    public static PriorityBlockingQueue filterContainsName(String name) {
        lock.lock();
        try{
            PriorityBlockingQueue<Dragon> result = dragons.stream()
                    .filter(dragon -> dragon.getName() != null && dragon.getName().contains(name))
                    .collect(Collectors.toCollection(PriorityBlockingQueue::new));
            return result;
        } finally {
            lock.unlock();
        }

    }

    public static PriorityBlockingQueue<Dragon> filterStartsWithName(String name) {
        lock.lock();
        try{
            int len = name.length();

            PriorityBlockingQueue<Dragon> result = dragons.stream()
                    .filter(dragon -> dragon.getName().length() >= len)
                    .filter(dragon -> dragon.getName().substring(0, len).equals(name))
                    .collect(Collectors.toCollection(PriorityBlockingQueue::new));

            return result;
        }finally {
            lock.unlock();
        }
    }

}