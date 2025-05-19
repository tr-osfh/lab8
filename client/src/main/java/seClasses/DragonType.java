package seClasses;

import java.io.Serial;
import java.io.Serializable;

/**
 * Перечисление DragonType представляет типы драконов, которые могут быть использованы в программе.
 * Поддерживаемые типы: WATER, UNDERGROUND, AIR.
 */
public enum DragonType implements Serializable {
    WATER,
    UNDERGROUND,
    AIR;
    @Serial
    private static final long serialVersionUID = 33L;
    /**
     * Преобразует строку в значение перечисления DragonType.
     * @param name Строка, представляющая тип дракона.
     * @return Значение перечисления DragonType, соответствующее строке.
     *         Если строка не соответствует ни одному значению, возвращает null.
     */
    public static DragonType ValueOf(String name) {
        try {
            return DragonType.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: Неверный тип дракона: " + name);
            return null;
        }
    }
}