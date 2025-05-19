package seClasses;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Класс Location представляет локацию с координатами и именем.
 * Координаты включают x (тип int), y (тип Integer, не может быть null) и z (тип double).
 * Имя локации может быть null.
 */
public class Location implements Serializable {

    @Serial
    private static final long serialVersionUID = 34L;

    private int x;
    private Integer y;
    private double z;
    private String name;

    /**
     * Конструктор класса Location.
     * @param x Координата X.
     * @param y Координата Y (не может быть null).
     * @param z Координата Z.
     * @param name Имя локации (может быть null).
     * @throws IllegalArgumentException Если координата Y равна null.
     */
    public Location(int x, Integer y, double z, String name) {
        if (y == null) {
            throw new IllegalArgumentException("Координата Y не может быть null.");
        } else {
            this.x = x;
            this.y = y;
            this.z = z;
            this.name = name;
        }
    }

    /**
     * Возвращает координату X.
     * @return Координата X.
     */
    public int getX() {
        return x;
    }

    /**
     * Устанавливает координату X.
     * @param x Координата X.
     */
    public void setX(int x) {
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
     * @param y Координата Y (не может быть null).
     * @throws IllegalArgumentException Если координата Y равна null.
     */
    public void setY(Integer y) {
        if (y == null) {
            throw new IllegalArgumentException("Координата Y не может быть null.");
        }
        this.y = y;
    }

    /**
     * Возвращает координату Z.
     * @return Координата Z.
     */
    public double getZ() {
        return z;
    }

    /**
     * Устанавливает координату Z.
     * @param z Координата Z.
     */
    public void setZ(double z) {
        this.z = z;
    }

    /**
     * Возвращает имя локации.
     * @return Имя локации.
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает имя локации.
     * @param name Имя локации (может быть null).
     */
    public void setName(String name) {
        this.name = name;
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
        Location location = (Location) object;
        return x == location.x &&
                Double.compare(z, location.z) == 0 &&
                Objects.equals(y, location.y) &&
                Objects.equals(name, location.name);
    }

    /**
     * Возвращает хэш-код объекта Location.
     * @return Хэш-код объекта.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, name);
    }

    /**
     * Возвращает строковое представление объекта Location.
     * @return Строка, представляющая объект Location.
     */
    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", name='" + name + '\'' +
                '}';
    }
}