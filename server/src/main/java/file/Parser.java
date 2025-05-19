package file;

import collection.ServerLogger;
import seClasses.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Класс Parser отвечает за преобразование объектов Dragon в строки и обратно.
 * Используется для сериализации и десериализации данных о драконах.
 */
public class Parser {

    public PriorityBlockingQueue<Dragon> ParseDbToDragons(ResultSet resultSet) {
        PriorityBlockingQueue<Dragon> dragons = new PriorityBlockingQueue<>();
        try {
            while (resultSet.next()) {
                Location killerLocation = new Location(
                        resultSet.getInt("killer_location_x"),
                        resultSet.getInt("killer_location_y"),
                        resultSet.getDouble("killer_location_z"),
                        resultSet.getString("killer_location_name")
                );

                Person killer = null;
                if (resultSet.getObject("killer_id") != null) {
                    String hairColorStr = resultSet.getString("killer_hair_color");
                    NaturalColor hairColor = (hairColorStr != null)
                            ? NaturalColor.valueOf(hairColorStr)
                            : null;

                    killer = new Person(
                            resultSet.getString("killer_name"),
                            resultSet.getString("killer_passport"),
                            BrightColor.valueOf(resultSet.getString("killer_eye_color")),
                            hairColor,
                            killerLocation
                    );
                }

                Long age = resultSet.getObject("dragon_age", Long.class);
                Long weight = resultSet.getObject("dragon_weight", Long.class);

                DragonType type = null;
                String typeStr = resultSet.getString("dragon_type");
                if (typeStr != null) {
                    try {
                        type = DragonType.valueOf(typeStr);
                    } catch (IllegalArgumentException e) {
                        ServerLogger.getLogger().warning("Неизвестный тип дракона: " + typeStr);
                    }
                }

                Dragon dragon = new Dragon(
                        resultSet.getLong("dragon_id"),
                        resultSet.getString("dragon_name"),
                        new Coordinates(
                                resultSet.getFloat("dragon_x"),
                                resultSet.getInt("dragon_y")
                        ),
                        resultSet.getTimestamp("dragon_creation_time").toLocalDateTime(),
                        age,
                        resultSet.getString("dragon_description"),
                        weight,
                        type,
                        killer,
                        resultSet.getString("dragon_owner")
                );

                dragons.add(dragon);

            }
        } catch (SQLException e){
            ServerLogger.getLogger().warning("Ошибка парсинга.");
        }
        return dragons;

    }
}