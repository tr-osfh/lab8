package objectsCreation;

import connection.User;
import seClasses.*;

public class CreateDragonFromScr {

    public static Dragon createDragon(String[] fields, User user){
        if (fields.length == 16){
            String name = fields[0];
            Coordinates cords = new Coordinates(Float.parseFloat(fields[1]), Integer.parseInt(fields[2]));
            Long age = Long.parseLong(fields[3]);
            String description = fields[4];
            Long weight = Long.parseLong(fields[5]);
            DragonType type = DragonType.valueOf(fields[6]);
            String personName = fields[8];
            String passportId = fields[9];
            BrightColor eyeColor = BrightColor.ValueOf(fields[10]);
            NaturalColor hairColor = NaturalColor.valueOf(fields[11]);
            int x = Integer.parseInt(fields[12]);
            Integer y = Integer.parseInt(fields[13]);
            double z = Double.parseDouble(fields[14]);
            String locName = fields[15];
            return new Dragon(
                    name,
                    cords,
                    age,
                    description,
                    weight,
                    type,
                    new Person(
                            personName,
                            passportId,
                            eyeColor,
                            hairColor,
                            new Location(
                                    x,
                                    y,
                                    z,
                                    locName
                            )
                    ),
                    user.getLogin()
            );
        } else if (fields.length == 8) {
            String name = fields[0];
            Coordinates cords = new Coordinates(Float.parseFloat(fields[1]), Integer.parseInt(fields[2]));
            Long age = Long.parseLong(fields[3]);
            String description = fields[4];
            Long weight = Long.parseLong(fields[5]);
            DragonType type = DragonType.valueOf(fields[6]);
            return new Dragon(
                    name,
                    cords,
                    age,
                    description,
                    weight,
                    type,
                    user.getLogin()
            );
        }
        else {
            return null;
        }
    }
}
