package console;

import seClasses.BrightColor;
import seClasses.DragonType;
import seClasses.NaturalColor;

/**
 * Класс ConsoleManager управляет вводом и выводом данных через консоль.
 * Используется для чтения различных типов данных, таких как строки, числа, цвета и т.д.
 */
public class ConsoleReader implements Reader{
    private static ReadController rc;

    public ConsoleReader(ReadController readController){
        this.rc = readController;
    }

    /**
     * Читает имя, введенное пользователем.
     * @return Введенное имя, если оно не пустое.
     */
    public String readName(){
        rc.printLine("Введите имя: ");
        String name;
        for (;;){
            name = rc.readLine();
            System.out.println();
            if (name.isEmpty() || name.isBlank()){
                rc.printLine("Строка не может быть пустой! Введите имя: ");
            } else {
                return name;
            }
        }
    }

    /**
     * Читает координату X, введенную пользователем.
     * @return Введенная координата X, если она корректна.
     */
    public Float readCoordinateX() {
        rc.printLine("Введите координату X: ");
        Float coordinateX;

        for (;;) {
            try {
                coordinateX = rc.readFloat();
                if (coordinateX == null) {
                    rc.printLine("Строка не может быть пустой. Введите координату X: ");
                }else{
                    return coordinateX;
                }
            } catch (NumberFormatException e) {
                rc.printLine("Число введено неверно. Введите координату X: ");
            }
        }
    }

    /**
     * Читает координату Y, введенную пользователем.
     * @return Введенная координата Y, если она корректна.
     */
    public Integer readCoordinateY(){
        rc.printLine("Введите координату Y: ");
        Integer coordinateY;
        for (;;){
            try {
                coordinateY = rc.readInteger();
                if (coordinateY == null) {
                    rc.printLine("Строка не может быть пустой. Введите координату Y: ");
                } else {
                    return coordinateY;
                }
            } catch (NumberFormatException e) {
                rc.printLine("Число введено неверно. Введите координату Y: ");
            }
        }
    }

    /**
     * Читает возраст дракона, введенный пользователем.
     * @return Введенный возраст, если он больше 0.
     */
    public Long readAge(){
        rc.printLine("Введите возраст дракона: ");
        Long age;
        for (;;){
            try {
                age = rc.readLong();
                if (age == null) {
                    return null;
                } else if (age <= 0) {
                    rc.printLine("Возраст должен быть больше 0. Введите возраст дракона: ");
                } else {
                    return age;
                }
            } catch (NumberFormatException e) {
                rc.printLine("Число введено неверно. Введите возраст дракона: ");
            }
        }
    }

    /**
     * Читает описание дракона, введенное пользователем.
     * @return Введенное описание.
     */
    public String readDescription(){
        rc.printLine("Введите описание дракона: ");
        String line = rc.readLine();
        return line;
    }

    /**
     * Читает вес дракона, введенный пользователем.
     * @return Введенный вес, если он больше 0.
     */
    public Long readWeight() {
        rc.printLine("Введите вес дракона: ");
        Long weight;
        for (;;) {
            try {
                weight = rc.readLong();
                if (weight == null) {
                    return weight;
                } else if (weight < 0) {
                    rc.printLine("Вес должен быть больше 0. Введите вес дракона: ");
                } else {
                    return weight;
                }
            } catch (NumberFormatException e) {
                rc.printLine("Число введено неверно. Введите вес дракона: ");
            }
        }
    }

    /**
     * Читает идентификатор человека, введенный пользователем.
     * @return Введенный идентификатор, если он не пустой.
     */
    public String readPassportID(){
        rc.printLine("Введите идентификатор человека: ");
        String passportId = null;
        for (;;){
            passportId = rc.readLine();
            if (passportId.isEmpty()){
                System.out.println("Идентификатор не может быть пустым. Введите идентификатор человека: ");
            } else {
                return passportId;
            }
        }
    }

    /**
     * Читает тип дракона, введенный пользователем.
     * @return Введенный тип дракона (WATER, UNDERGROUND, AIR).
     */
    public DragonType readType() {
        String type;
        for (; ; ) {
            rc.printLine("Выбирите один из трех типов (WATER, UNDERGROUND, AIR). Введите тип дракона: ");
            type = rc.readLine();
            System.out.println();
            switch (type) {
                case "WATER":
                    return DragonType.WATER;
                case "UNDERGROUND":
                    return DragonType.UNDERGROUND;
                case "AIR":
                    return DragonType.AIR;
                default:
                    rc.printLine("Проверьте введенные данные.");
            }
        }
    }

    /**
     * Читает цвет глаз дракона, введенный пользователем.
     * @return Введенный цвет глаз (GREEN, BLACK, BLUE, YELLOW, ORANGE).
     */
    public BrightColor readBrightColor(){
        String color;
        for (;;) {
            rc.printLine("Выберите один из 5 цветов глаз (GREEN, BLACK, BLUE, YELLOW, ORANGE) :");
            color = rc.readLine();
            switch (color){
                case "GREEN":
                    return BrightColor.GREEN;
                case "BLACK":
                    return BrightColor.BLACK;
                case "BLUE":
                    return BrightColor.BLUE;
                case "YELLOW":
                    return BrightColor.YELLOW;
                case "ORANGE":
                    return BrightColor.ORANGE;
                default:
                    rc.printLine("Проверьте введенные данные.");
            }
        }
    }

    /**
     * Читает цвет волос дракона, введенный пользователем.
     * @return Введенный цвет волос (RED, BLACK, YELLOW, WHITE, BROWN).
     */
    public NaturalColor readNaturalColor(){
        String color;
        for (;;) {
            rc.printLine("Выберите один из 5 цветов волос (RED, BLACK, YELLOW, WHITE, BROWN): ");
            color = rc.readLine();
            switch (color){
                case "BLACK":
                    return NaturalColor.BLACK;
                case "RED":
                    return NaturalColor.RED;
                case "YELLOW":
                    return NaturalColor.YELLOW;
                case "WHITE":
                    return NaturalColor.WHITE;
                case "BROWN":
                    return NaturalColor.BROWN;
                default:
                    rc.printLine("Проверьте введенные данные.");
            }
        }
    }

    /**
     * Читает координату X локации, введенную пользователем.
     * @return Введенная координата X, если она корректна.
     */
    public int readLocationX(){
        rc.printLine("Введите координату X: ");
        Integer x;
        for (;;){
            try{
                x = rc.readInteger();
                System.out.println();
                if (x == null){
                    rc.printLine("Строка не может быть пустой. Введите координату X: ");
                } else {
                    return x;
                }
            } catch (NumberFormatException e) {
                rc.printLine("Число введено неверно. Введите координату X: ");
            }
        }
    }

    /**
     * Читает координату Y локации, введенную пользователем.
     * @return Введенная координата Y, если она корректна.
     */
    public Integer readLocationY(){
        rc.printLine("Введите координату Y: ");
        Integer y;
        for (;;){
            try {
                y = rc.readInteger();
                System.out.println();
                if (y == null){
                    rc.printLine("Строка не может быть пустой. Введите координату Y: ");
                } else {
                    return y;
                }
            } catch (NumberFormatException e) {
                rc.printLine("Число введено неверно. Введите координату Y: ");
            }
        }
    }

    /**
     * Читает координату Z локации, введенную пользователем.
     * @return Введенная координата Z, если она корректна.
     */
    public double readLocationZ(){
        rc.printLine("Введите координату Z: ");
        Double z;
        for (;;){
            try {
                z = rc.readDouble();
                System.out.println();
                if (z == null){
                    rc.printLine("Строка не может быть пустой. Введите координату Z: ");
                } else {
                    return z;
                }
            } catch (NumberFormatException e) {
                rc.printLine("Число введено неверно. Введите координату Z: ");
            }
        }
    }

    /**
     * Читает выбор пользователя (y/n) для добавления параметра.
     * @return true, если пользователь выбрал "y", иначе false.
     */
    public boolean readChoice(){
        String yn;
        for (;;) {
            rc.printLine("Вы хотите добавить убийцу? y/n: ");
            yn = rc.readLine();
            if (yn.equals("y")){
                return true;
            } else if (yn.equals("n")) {
                return false;
            } else {
                System.out.println("Проверьте введенные данные.");
            }
        }
    }

    /**
     * Читает название локации, введенное пользователем.
     * @return Введенное название локации, если оно не пустое.
     */
    public String readLocationName(){
        rc.printLine("Введите название локации: ");
        String name = rc.readLine();
        if (name.isEmpty()){
            return null;
        }
        else {
            return name;
        }
    }

    /**
     * Читает идентификатор дракона, введенный пользователем.
     * @return Введенный идентификатор, если он корректный.
     */
    public long readId(){
        rc.printLine("Введите id дракона: ");
        Long id = null;
        while (true) {
            try {
                id = rc.readLong();
                if (id == null) {
                    rc.printLine("Строка не может быть пустой. Введите id дракона: ");
                } else {
                    return id;
                }
            } catch (NumberFormatException e) {
                rc.printLine("Число введено неверно. Введите id дракона: ");
            }
        }
    }

    /**
     * Читает ссылку на скрипт, введенную пользователем.
     * @return Введенная ссылка.
     */
    public String readLink(){
        rc.printLine("Введите ссылку на скрипт: ");
        String line = rc.readLine();
        return line;
    }

    /**
     * Читает подстроку, введенную пользователем.
     * @return Введенная подстрока, если она не пустая.
     */
    public String readNamePart(){
        String name;
        for (;;){
            rc.printLine("Введите подстроку: ");
            name = rc.readLine();
            if (name.isEmpty() || name.isBlank()){
                System.out.println("Строка не может быть пустой!");
            } else {
                return name;
            }
        }
    }

    public String readLogin(){
        String login;
        for(;;){
            rc.printLine("Введите логин: ");
            login = rc.readLine();
            if (login.isEmpty() || login.isBlank()){
                System.out.println("Строка не может быть пустой!");
            } else {
                return login;
            }
        }
    }

    public String readPasswordAuthorization(){
        String password;
        for(;;){
            rc.printLine("Пароль пароль: ");
            password = rc.readLine();
            if (password.isEmpty() || password.isBlank()){
                System.out.println("Строка не может быть пустой!");
            }
            return password;

        }
    }

    public String readPasswordRegistration(){
        String password1;
        String password2;
        for(;;){
            rc.printLine("Пароль пароль: ");
            password1 = rc.readLine();
            if (password1.isEmpty() || password1.isBlank()){
                System.out.println("Строка не может быть пустой!");
                continue;
            }
            rc.printLine("Пароль пароль повторно: ");
            password2 = rc.readLine();
            if (password2.isEmpty() || password2.isBlank()){
                System.out.println("Строка не может быть пустой!");
                continue;
            }
            if (password1.equals(password2)){
                return password1;
            } else {
                System.out.println("Пароли не совпадают!");
            }

        }
    }


    /**
     * Читает команду, введенную пользователем.
     * @return Массив строк, содержащий команду и ее аргументы.
     */
    public String[] readCommand(){
        String command = rc.readLine();
        String[] args = command.split(" ");
        return args;
    }

    /**
     * Выводит строку в консоль.
     * @param str Строка для вывода.
     */
    public void printLine(Object str){
        rc.printLine(str);
    }
}