package seClasses;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Класс Person представляет человека с набором характеристик.
 * Включает такие поля, как имя, идентификатор паспорта, цвет глаз, цвет волос и локацию.
 */
public class Person implements Serializable {

    @Serial
    private static final long serialVersionUID = 36L;

    private String name;
    private String passportID;
    private BrightColor eyeColor;
    private NaturalColor hairColor;
    private Location location;

    /**
     * Конструктор класса Person.
     * @param name Имя человека.
     * @param passportID Идентификатор паспорта.
     * @param eyeColor Цвет глаз.
     * @param hairColor Цвет волос.
     * @param location Локация.
     * @throws IllegalArgumentException Если переданы некорректные значения.
     */
    public Person(String name, String passportID, BrightColor eyeColor, NaturalColor hairColor, Location location) {
        if (name == null || name.isEmpty() || passportID == null || passportID.isEmpty() || eyeColor == null || location == null) {
            throw new IllegalArgumentException("Некорректные значения для создания объекта Person.");
        } else {
            this.name = name;
            this.passportID = passportID;
            this.eyeColor = eyeColor;
            this.hairColor = hairColor;
            this.location = location;
        }
    }

    /**
     * Возвращает идентификатор паспорта.
     * @return Идентификатор паспорта.
     */
    public String getPassportID() {
        return passportID;
    }

    /**
     * Устанавливает идентификатор паспорта.
     * @param id Идентификатор паспорта.
     */
    public void setPassportID(String id) {
        this.passportID = id;
    }

    /**
     * Возвращает цвет глаз.
     * @return Цвет глаз.
     */
    public BrightColor getEyeColor() {
        return eyeColor;
    }

    /**
     * Устанавливает цвет глаз.
     * @param eyeColor Цвет глаз.
     */
    public void setEyeColor(BrightColor eyeColor) {
        this.eyeColor = eyeColor;
    }

    /**
     * Возвращает цвет волос.
     * @return Цвет волос.
     */
    public NaturalColor getHairColor() {
        return hairColor;
    }

    /**
     * Устанавливает цвет волос.
     * @param hairColor Цвет волос.
     */
    public void setHairColor(NaturalColor hairColor) {
        this.hairColor = hairColor;
    }

    /**
     * Возвращает локацию.
     * @return Локация.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Устанавливает локацию.
     * @param location Локация.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Устанавливает локацию с помощью координат и имени.
     * @param x Координата X.
     * @param y Координата Y.
     * @param z Координата Z.
     * @param name Имя локации.
     */
    public void setLocation(int x, Integer y, double z, String name) {
        this.location = new Location(x, y, z, name);
    }

    /**
     * Возвращает имя человека.
     * @return Имя человека.
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает имя человека.
     * @param name Имя человека.
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
        Person person = (Person) object;
        return Objects.equals(name, person.name) &&
                Objects.equals(passportID, person.passportID) &&
                Objects.equals(location, person.location) &&
                eyeColor == person.eyeColor &&
                hairColor == person.hairColor;
    }

    /**
     * Возвращает хэш-код объекта Person.
     * @return Хэш-код объекта.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, passportID, eyeColor, hairColor, location);
    }

    /**
     * Возвращает строковое представление объекта Person.
     * @return Строка, представляющая объект Person.
     */
    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", passportID='" + passportID + '\'' +
                ", eyeColor=" + eyeColor +
                ", hairColor=" + hairColor +
                ", location=" + location +
                '}';
    }
}