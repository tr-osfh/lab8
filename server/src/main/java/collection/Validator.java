package collection;


import seClasses.Dragon;
import seClasses.DragonType;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Класс Validator отвечает за проверку корректности данных объекта Dragon.
 * Проверяет уникальность ID, корректность полей и типов данных.
 */
public class Validator {

    private ArrayList<Long> ids = new ArrayList<>();
    private ArrayList<DragonType> possibleDragonTypes = new ArrayList<>(Arrays.asList(
            DragonType.AIR,
            DragonType.UNDERGROUND,
            DragonType.WATER
    ));

    /**
     * Проверяет корректность всех полей объекта Dragon.
     * @param dragon Объект Dragon, который нужно проверить.
     * @return Объект Dragon, если все поля корректны, иначе null.
     */
    public Dragon getValid(Dragon dragon) {
        if (
                        dragon.getName().isBlank() ||
                        dragon.getName().isEmpty() ||
                        dragon.getName() == null ||
                        dragon.getCoordinates() == null ||
                        dragon.getCreationDate() == null ||
                        (dragon.getAge() != null && dragon.getAge() <= 0) ||
                        (dragon.getWeight() != null && dragon.getWeight() <= 0) ||
                        dragon.getType() == null ||
                        !possibleDragonTypes.contains(dragon.getType()) ||
                        dragon.getCoordinates().getX() == null ||
                        dragon.getCoordinates().getY() == null
        ) {
            return null;
        } else {
            if (dragon.getKiller() != null) {
                if (
                        dragon.getKiller().getName().isEmpty() ||
                                dragon.getKiller().getName().isBlank() ||
                                dragon.getKiller().getName() == null ||
                                dragon.getKiller().getPassportID().isBlank() ||
                                dragon.getKiller().getPassportID().isEmpty() ||
                                dragon.getKiller().getPassportID() == null ||
                                dragon.getKiller().getEyeColor() == null ||
                                dragon.getKiller().getHairColor() == null ||
                                dragon.getKiller().getLocation() == null ||
                                dragon.getKiller().getLocation().getY() == null
                ) {
                    return null;
                } else {
                    return dragon;
                }
            } else {
                return dragon;
            }
        }
    }
}