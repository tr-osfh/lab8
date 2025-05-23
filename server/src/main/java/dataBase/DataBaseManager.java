package dataBase;
import collection.ServerLogger;
import connection.CommandResponse;
import connection.Response;
import connection.ResponseStatus;
import connection.User;
import file.Parser;
import file.fileReader;
import seClasses.Dragon;
import seClasses.Location;
import seClasses.Person;

import java.sql.*;
import java.util.concurrent.PriorityBlockingQueue;

public class DataBaseManager {
    Parser parser = new Parser();
    private QueryManager qm = new QueryManager();

    public static Connection connect() throws SQLException {
        try{
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/studs", fileReader.getUser(), fileReader.getPassword());
        } catch (SQLException e) {
            ServerLogger.getLogger().warning("Ошибка подключения к базе данных.");
            return null;
        }
        catch (ClassNotFoundException e) {
            ServerLogger.getLogger().warning("Драйвер для psql не найден.");
            return null;
        }
    }

    public Response registration(User user){
        try (
                Connection connection = connect();
                PreparedStatement findUser = connection.prepareStatement(qm.findUser)
                ){

            findUser.setString(1, user.getLogin());
            ResultSet resultSet = findUser.executeQuery();
            if (!resultSet.next()){
                String salt = PasswordKeeper.salt();
                PreparedStatement addUser = connection.prepareStatement(qm.addUser);
                addUser.setString(1, user.getLogin());
                addUser.setString(2, PasswordKeeper.hash(user.getPassword() + salt));
                addUser.setString(3, salt);
                addUser.execute();
                return new Response(ResponseStatus.OK, "Вы создали аккаунт " + user.getLogin(), CommandResponse.REGISTRATION);
            } else {
                return new Response(ResponseStatus.ERROR, "Пользователь с логином " + user.getLogin() + " уже существует.", CommandResponse.REGISTRATION);
            }
        } catch (SQLException | NullPointerException e) {
            ServerLogger.getLogger().warning("Ошибка подключения к базе данных. ");
            return new Response(ResponseStatus.ERROR, "Ошибка подключения к базе данных, попробуйте еще раз.", CommandResponse.REGISTRATION);
        }
    }

    public Response authorisation(User user) {
        try (            Connection connection = connect();
                         PreparedStatement findUser = connection.prepareStatement(qm.findUser)){

            findUser.setString(1, user.getLogin());
            ResultSet resultSet = findUser.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getString("password").equals(PasswordKeeper.hash(user.getPassword() + resultSet.getString("salt")))) {
                    return new Response(ResponseStatus.OK, "Вы вошли в аккаунт.", CommandResponse.AUTHORIZATION, user);
                }
                return new Response(ResponseStatus.ERROR, "Не верный пароль. Попробуйте еще раз.", CommandResponse.AUTHORIZATION);
            } else {
                return new Response(ResponseStatus.ERROR, "Такого пользователя не существует.", CommandResponse.AUTHORIZATION);
            }
        } catch (SQLException | NullPointerException e) {
            ServerLogger.getLogger().warning("Ошибка подключения к базе данных. ");
            return new Response(ResponseStatus.ERROR, "Ошибка подключения к базе данных, попробуйте еще раз.", CommandResponse.AUTHORIZATION);
        }
    }

    public boolean removeDragon(long id, User user){
        try (
                Connection connection = connect();
                PreparedStatement remove = connection.prepareStatement(qm.removeDragon);
                ){

            remove.setLong(1, id);
            remove.setString(2, user.getLogin());
            int rowsDeleted = remove.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            ServerLogger.getLogger().warning("Ошибка удаления данных. ");
        }
        return false;
    }

    public boolean updateDragon(Long id, Dragon dragon, User user){
        try (
                Connection connection = connect();
                PreparedStatement update = connection.prepareStatement(qm.updateDragon)
                ){


            update.setString(1, dragon.getName());
            update.setFloat(2, dragon.getCoordinates().getX());
            update.setInt(3, dragon.getCoordinates().getY());
            if (dragon.getAge() == null){
                update.setNull(4, Types.BIGINT);
            } else {
                update.setLong(4, dragon.getAge());
            }
            if (dragon.getDescription() == null){
                update.setNull(5, Types.VARCHAR);
            } else {
                update.setString(5, dragon.getDescription());
            }

            if (dragon.getWeight() == null){
                  update.setNull(6, Types.BIGINT);
            } else {
                update.setLong(6, dragon.getWeight());
            }
            update.setString(7, dragon.getType().toString());

            String curKiller = findKiller(id);
            if (curKiller != null){
                removeKiller(curKiller);
            }

            if (dragon.getKiller() != null){
                Integer killerId = addKillerToDragon(dragon, user);
                if (killerId != null){
                    update.setInt(8, killerId);
                } else {
                    return false;
                }
            } else {
                update.setNull(8, Types.INTEGER);
            }
            update.setString(9, user.getLogin());
            update.setLong(10, id);
            int updatedRows = update.executeUpdate();
            return updatedRows > 0;

        } catch (SQLException e) {
            ServerLogger.getLogger().warning("Ошибка обновления данных данных. ");
        }
        return false;
    }


    public Integer addDragon(Dragon dragon, User user){
        try (
                Connection connection = connect();
                PreparedStatement add = connection.prepareStatement(qm.addDragon);
                ){


            add.setString(1, dragon.getName());
            add.setFloat(2, dragon.getCoordinates().getX());
            add.setInt(3, dragon.getCoordinates().getY());
            if (dragon.getAge() == null){
                add.setNull(4, Types.BIGINT);
            } else {
                add.setLong(4, dragon.getAge());
            }
            if (dragon.getDescription() == null){
                add.setNull(5, Types.VARCHAR);
            } else {
                add.setString(5, dragon.getDescription());
            }
            if (dragon.getWeight() == null){
                add.setNull(6, Types.BIGINT);
            } else {
                add.setLong(6, dragon.getWeight());
            }
            add.setString(7, dragon.getType().toString());



            if (dragon.getKiller() != null){ //todo добавить удаление старого киллера
                Integer killerId = addKillerToDragon(dragon, user);
                if (killerId != null){
                    add.setInt(8, killerId);
                } else {
                    return null;
                }
            } else {
                add.setNull(8, Types.INTEGER);
            }
            add.setString(9, user.getLogin());
            try (ResultSet rs = add.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }

        } catch (SQLException e) {
            ServerLogger.getLogger().warning("Ошибка добавления данных о драконе. ");
        }
        return null;
    }

    public Integer addKillerToDragon(Dragon dragon, User user) {
        try (Connection connection = connect();
                         PreparedStatement addKiller = connection.prepareStatement(qm.addKiller)){


            Person killer = dragon.getKiller();
            Location location = killer.getLocation();

            addKiller.setString(1, killer.getName());
            addKiller.setString(2, killer.getPassportID());
            addKiller.setString(3, killer.getEyeColor().toString());
            addKiller.setString(4, killer.getHairColor() != null ? killer.getHairColor().toString() : null);
            addKiller.setInt(5, location.getX());
            addKiller.setInt(6, location.getY());
            addKiller.setDouble(7, location.getZ());
            if (location.getName() == null){
                addKiller.setNull(8, Types.VARCHAR);
            } else{
                addKiller.setString(8, location.getName());
            }


            try (ResultSet rs = addKiller.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            ServerLogger.getLogger().warning("Ошибка добавления данных об убийце.");
        }
        return null;
    }

    public boolean clear(User user){
        try(
                Connection connection = connect();
                PreparedStatement clear = connection.prepareStatement(qm.clear);
                ){

            clear.setString(1, user.getLogin());
            int deletedRows = clear.executeUpdate();
            return deletedRows > 0;
        } catch (SQLException e) {
            ServerLogger.getLogger().warning("Ошибка добавления данных. ");
        }
        return false;
    }

    public boolean removeLower(Dragon dragon, User user){
        try (Connection connection = connect();
             PreparedStatement removeLower = connection.prepareStatement(qm.removeLower)){

            removeLower.setFloat(1, dragon.getCoordinates().getX());
            removeLower.setString(2, user.getLogin());
            int deletedRows = removeLower.executeUpdate();
            return deletedRows > 0;
        } catch (SQLException e) {
            ServerLogger.getLogger().warning("Ошибка удаления данных. ");
        }
        return false;
    }
    public PriorityBlockingQueue<Dragon> selectDragons(){
        try(
            Connection connection = connect();
            PreparedStatement selectDragons = connection.prepareStatement(qm.selectDragons)
        ) {
            return parser.ParseDbToDragons(selectDragons.executeQuery());
        } catch (SQLException e) {
            ServerLogger.getLogger().warning("Ошибка выборки данных. ");
        }
        return null;
    }

    public String findKiller(long id) {
        String query = qm.findKiller;
        try (Connection connection = connect();
             PreparedStatement findKiller = connection.prepareStatement(query)) {

            findKiller.setLong(1, id);

            try (ResultSet resultSet = findKiller.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString(1);
                }
                return null;
            }
        } catch (SQLException e) {
            ServerLogger.getLogger().warning("Ошибка выборки данных. ");
            return null;
        }
    }

    public boolean removeKiller(String id){
        try(Connection connection = connect();
                PreparedStatement rmKiller = connection.prepareStatement(qm.deleteKiller);){
            rmKiller.setString(1, id);
            int deletedRows = rmKiller.executeUpdate();
            return deletedRows > 0;
        } catch (SQLException e) {
            ServerLogger.getLogger().warning("Ошибка удаления данных. ");
        }
        return false;
    }


}
