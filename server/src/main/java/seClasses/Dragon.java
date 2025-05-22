package seClasses;



import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Класс Dragon представляет дракона с набором характеристик.
 * Включает такие поля, как имя, координаты, возраст, описание, вес, тип и убийца.
 */
public class Dragon implements Comparable<Dragon>, Serializable {

    @Serial
    private static final long serialVersionUID = 32L;

    private long id; // Уникальный идентификатор дракона, должен быть больше 0
    private String name; // Имя дракона, не может быть null или пустым
    private Coordinates coordinates; // Координаты дракона, не могут быть null
    private LocalDateTime creationDate; // Дата создания, генерируется автоматически
    private Long age; // Возраст дракона, должен быть больше 0, может быть null
    private String description; // Описание дракона, может быть null
    private Long weight; // Вес дракона, должен быть больше 0, может быть null
    private DragonType type; // Тип дракона, не может быть null
    private Person killer; // Убийца дракона, может быть null
    private String userLogin;

    /**
     * Конструктор для создания объекта Dragon с убийцей.
     * @param name Имя дракона.
     * @param coordinates Координаты дракона.
     * @param age Возраст дракона.
     * @param description Описание дракона.
     * @param weight Вес дракона.
     * @param type Тип дракона.
     * @param killer Убийца дракона.
     * @throws IllegalArgumentException Если переданы некорректные значения.
     */
    public Dragon(
            Long id,
            String name,
            Coordinates coordinates,
            LocalDateTime creationDate,
            Long age,
            String description,
            Long weight,
            DragonType type,
            Person killer,
            String userLogin
    ) {
        if (name == null || name.isEmpty() || coordinates == null || (age != null && age <= 0) || type == null) {
            throw new IllegalArgumentException("Введенная информация содержит недопустимые значения.");
        } else {
            this.id = id;
            this.name = name;
            this.coordinates = coordinates;
            this.creationDate = creationDate;
            this.age = age;
            this.description = description;
            this.weight = weight;
            this.type = type;
            this.killer = killer;
            this.userLogin = userLogin;
        }
    }

    /**
     * Конструктор для создания объекта Dragon без убийцы.
     * @param name Имя дракона.
     * @param coordinates Координаты дракона.
     * @param age Возраст дракона.
     * @param description Описание дракона.
     * @param weight Вес дракона.
     * @param type Тип дракона.
     * @throws IllegalArgumentException Если переданы некорректные значения.
     */
    public Dragon(
            long id,
            String name,
            Coordinates coordinates,
            LocalDateTime creationDate,
            Long age,
            String description,
            Long weight,
            DragonType type,
            String userLogin
    ) {
        if (name == null || name.isEmpty() || coordinates == null || (age != null && age <= 0) || weight <= 0 || type == null) {
            throw new IllegalArgumentException("В исходном файле ошибка.");
        } else {
            this.id = id;
            this.name = name;
            this.coordinates = coordinates;
            this.creationDate = creationDate;
            this.age = age;
            this.description = description;
            this.weight = weight;
            this.type = type;
            this.userLogin = userLogin;
        }
    }

    public static String getDragonTable(Dragon dragon) {
        String coordinates = "(null)";
        if (dragon.getCoordinates() != null) {
            coordinates = String.format("(%.1f, %d)",
                    dragon.getCoordinates().getX(),
                    dragon.getCoordinates().getY());
        }

        String killerInfo = "None";
        if (dragon.getKiller() != null) {
            killerInfo = String.format("%s (Passport: %s)",
                    dragon.getKiller().getName(),
                    dragon.getKiller().getPassportID());


            killerInfo += String.format("\nEye: %-6s Hair: %-5s",
                    dragon.getKiller().getEyeColor(),
                    dragon.getKiller().getHairColor() != null ?
                            dragon.getKiller().getHairColor() : "None");

            if (dragon.getKiller().getLocation() != null) {
                killerInfo += String.format("\nLocation: %s\n(x=%d, y=%d, z=%.1f)",
                        dragon.getKiller().getLocation().getName() != null ?
                                dragon.getKiller().getLocation().getName() : "Unnamed",
                        dragon.getKiller().getLocation().getX(),
                        dragon.getKiller().getLocation().getY(),
                        dragon.getKiller().getLocation().getZ());
            } else {
                killerInfo += "\nLocation: Unknown";
            }
        }

        StringBuilder table = new StringBuilder();

        table.append("+-----------------+-----------------------------------------------------+\n");

        table.append(String.format("| %-15s | %-51s |\n", "Dragon ID", dragon.getId()));
        table.append("+-----------------+-----------------------------------------------------+\n");

        table.append(String.format("| %-15s | %-51s |\n", "Name",
                dragon.getName() != null ? dragon.getName() : "Unnamed"));
        table.append(String.format("| %-15s | %-51s |\n", "Coordinates", coordinates));
        table.append(String.format("| %-15s | %-51s |\n", "Created",
                dragon.getCreationDate() != null ?
                        dragon.getCreationDate().toString().replace("T", " ") : "Unknown"));
        table.append(String.format("| %-15s | %-51s |\n", "Age",
                dragon.getAge() != null ? dragon.getAge() : "Unknown"));
        table.append(String.format("| %-15s | %-51s |\n", "Description",
                dragon.getDescription() != null && !dragon.getDescription().isEmpty() ?
                        dragon.getDescription() : "-"));
        table.append(String.format("| %-15s | %-51s |\n", "Weight",
                dragon.getWeight() != null ? dragon.getWeight() : "Unknown"));
        table.append(String.format("| %-15s | %-51s |\n", "Type",
                dragon.getType() != null ? dragon.getType() : "Unknown"));

        String[] killerLines = killerInfo.split("\n");
        table.append(String.format("| %-15s | %-51s |\n", "Killer", killerLines[0]));
        for (int i = 1; i < killerLines.length; i++) {
            table.append(String.format("| %-15s | %-51s |\n", "", killerLines[i]));
        }

        table.append(String.format("| %-15s | %-51s |\n", "Owner",
                dragon.getUserLogin() != null ? dragon.getUserLogin() : "Unknown"));

        table.append("+-----------------+-----------------------------------------------------+");

        return table.toString();
    }


    /**
     * Возвращает идентификатор дракона.
     * @return Идентификатор дракона.
     */
    public long getId() {
        return id;
    }

    /**
     * Устанавливает идентификатор дракона.
     * @param id Идентификатор дракона.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Возвращает имя дракона.
     * @return Имя дракона.
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает имя дракона.
     * @param name Имя дракона.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Возвращает координаты дракона.
     * @return Координаты дракона.
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Устанавливает координаты дракона.
     * @param coordinates Координаты дракона.
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Возвращает дату создания дракона.
     * @return Дата создания дракона.
     */
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Устанавливает дату создания дракона.
     * @param creationDate Дата создания дракона.
     */
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Возвращает возраст дракона.
     * @return Возраст дракона.
     */
    public Long getAge() {
        return age;
    }

    /**
     * Устанавливает возраст дракона.
     * @param age Возраст дракона.
     */
    public void setAge(Long age) {
        this.age = age;
    }

    /**
     * Возвращает описание дракона.
     * @return Описание дракона.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Устанавливает описание дракона.
     * @param description Описание дракона.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Возвращает вес дракона.
     * @return Вес дракона.
     */
    public Long getWeight() {
        return weight;
    }

    /**
     * Устанавливает вес дракона.
     * @param weight Вес дракона.
     */
    public void setWeight(Long weight) {
        this.weight = weight;
    }

    /**
     * Возвращает тип дракона.
     * @return Тип дракона.
     */
    public DragonType getType() {
        return type;
    }

    /**
     * Устанавливает тип дракона.
     * @param type Тип дракона.
     */
    public void setType(DragonType type) {
        this.type = type;
    }

    /**
     * Возвращает убийцу дракона.
     * @return Убийца дракона.
     */
    public Person getKiller() {
        return killer;
    }

    /**
     * Устанавливает убийцу дракона.
     * @param killer Убийца дракона.
     */
    public void setKiller(Person killer) {
        this.killer = killer;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
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
        Dragon dragon = (Dragon) object;
        return id == dragon.id &&
                Objects.equals(name, dragon.name) &&
                Objects.equals(coordinates, dragon.coordinates) &&
                Objects.equals(creationDate, dragon.creationDate) &&
                Objects.equals(age, dragon.age) &&
                Objects.equals(description, dragon.description) &&
                Objects.equals(weight, dragon.weight) &&
                type == dragon.type &&
                Objects.equals(killer, dragon.killer);
    }

    /**
     * Возвращает хэш-код объекта Dragon.
     * @return Хэш-код объекта.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, age, description, weight, type, killer);
    }

    /**
     * Возвращает строковое представление объекта Dragon.
     * @return Строка, представляющая объект Dragon.
     */
    @Override
    public String toString() {
        return "Dragon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", age=" + age +
                ", description='" + description + '\'' +
                ", weight=" + weight +
                ", type=" + type +
                ", killer=" + killer +
                ", user=" + userLogin +
                '}';
    }

    /**
     * Сравнивает текущий объект Dragon с другим объектом Dragon по координате X.
     * @param o Объект Dragon для сравнения.
     * @return 1, если текущий объект больше, -1, если меньше, и 0, если равны.
     */
    @Override
    public int compareTo(Dragon o) {
        if (this.getCoordinates().getX() > o.getCoordinates().getX()) {
            return 1;
        } else if (this.getCoordinates().getX() < o.getCoordinates().getX()) {
            return -1;
        } else {
            return 0;
        }
    }
}