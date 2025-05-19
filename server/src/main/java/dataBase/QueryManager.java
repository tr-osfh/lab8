package dataBase;

public class QueryManager {

    public String findUser = "SELECT * FROM users WHERE login = ?";

    public String addUser = "INSERT INTO users (login, password, salt) VALUES (?, ?, ?);";

    public String findKiller = "SELECT k.passportid " +
            "FROM dragons d " +
            "LEFT JOIN killers k ON d.killer = k.id " +
            "WHERE d.id = ?";

    public String deleteKiller = "DELETE FROM killers WHERE passportID = ?;";

    public String addDragon = "INSERT INTO dragons (\n" +
            "    name,\n" +
            "    coordinatex,\n" +
            "    coordinatey,\n" +
            "    age,\n" +
            "    description,\n" +
            "    weight,\n" +
            "    type,\n" +
            "    killer,\n" +
            "    user_login\n" +
            ") VALUES (\n" +
            "    ?, \n" +
            "    ?, \n" +
            "    ?, \n" +
            "    ?, \n" +
            "    ?, \n" +
            "    ?, \n" +
            "    ?, \n" +
            "    ?, \n" +
            "    ?  \n" +
            ")" +
            "RETURNING id;";

    public String addKiller = "INSERT INTO killers (name, passportID, eyeColor, hairColor, locationX, locationY, locationZ, location_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING id;";
    public String clear = "DELETE FROM dragons WHERE user_login = ?;";

    public String updateDragon = "UPDATE dragons \n" +
            "SET \n" +
            "    name = ?,\n" +
            "    coordinatex = ?,\n" +
            "    coordinatey = ?,\n" +
            "    age = ?,\n" +
            "    description = ?,\n" +
            "    weight = ?,\n" +
            "    type = ?,\n" +
            "    killer = ? \n" +
            "WHERE \n" +
            "    user_login = ? AND id = ?;";
    public String removeDragon = "DELETE FROM dragons WHERE id = ? AND user_login = ?;";

    public String removeLower = "DELETE FROM dragons WHERE coordinateX < ? AND user_login = ?;";

    public String selectDragons = "SELECT \n" +
            "    d.id AS dragon_id,\n" +
            "    d.name AS dragon_name,\n" +
            "    d.coordinateX AS dragon_x,\n" +
            "    d.coordinateY AS dragon_y,\n" +
            "    d.creationTime AS dragon_creation_time,\n" +
            "    d.age AS dragon_age,\n" +
            "    d.description AS dragon_description,\n" +
            "    d.weight AS dragon_weight,\n" +
            "    d.type AS dragon_type,\n" +
            "    d.user_login AS dragon_owner,\n" +
            "    \n" +
            "    k.id AS killer_id,\n" +
            "    k.name AS killer_name,\n" +
            "    k.passportID AS killer_passport,\n" +
            "    k.eyeColor AS killer_eye_color,\n" +
            "    k.hairColor AS killer_hair_color,\n" +
            "    k.locationX AS killer_location_x,\n" +
            "    k.locationY AS killer_location_y,\n" +
            "    k.locationZ AS killer_location_z,\n" +
            "    k.location_name AS killer_location_name\n" +
            "    \n" +
            "FROM dragons d\n" +
            "LEFT JOIN killers k ON d.killer = k.id\n" +
            "ORDER BY d.creationTime DESC;";
}
