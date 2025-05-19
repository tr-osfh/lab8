package seClasses;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Класс Coordinates представляет координаты объекта в двумерном пространстве.
 * Координаты состоят из двух полей: x (тип Float) и y (тип Integer).
 * Оба поля не могут быть null.
 */
public class Coordinates implements Serializable {

    @Serial
    private static final long serialVersionUID = 31L;

    private Float x; // Координата X, не может быть null
    private Integer y; // Координата Y, не может быть null

    /**
     * Конструктор класса Coordinates.
     * @param x Координата X.
     * @param y Координата Y.
     * @throws IllegalArgumentException Если x или y равны null.
     */
    public Coordinates(Float x, Integer y) {
        if (x == null || y == null) {
            throw new IllegalArgumentException("Координаты x и y не могут быть null.");
        } else {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Возвращает координату X.
     * @return Координата X.
     */
    public Float getX() {
        return x;
    }

    /**
     * Устанавливает координату X.
     * @param x Координата X.
     * @throws IllegalArgumentException Если x равен null.
     */
    public void setX(Float x) {
        if (x == null) {
            throw new IllegalArgumentException("Координата X не может быть null.");
        }
        this.x = x;
    }

    /**
     * Возвращает координату Y.
     * @return Координата Y.
     */
    public Integer getY() {
        return y;
    }

    /**
     * Устанавливает координату Y.
     * @param y Координата Y.
     * @throws IllegalArgumentException Если y равен null.
     */
    public void setY(Integer y) {
        if (y == null) {
            throw new IllegalArgumentException("Координата Y не может быть null.");
        }
        this.y = y;
    }

    /**
     * Сравнивает текущий объект с другим объектом на равенство.
     * @param object Объект для сравнения.
     * @return true, если объекты равны, иначе false.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Coordinates that = (Coordinates) object;
        return Objects.equals(x, that.x) &&
                Objects.equals(y, that.y);
    }

    /**
     * Возвращает строковое представление объекта Coordinates.
     * @return Строка, представляющая объект Coordinates.
     */
    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    /**
     * Возвращает хэш-код объекта Coordinates.
     * @return Хэш-код объекта.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}