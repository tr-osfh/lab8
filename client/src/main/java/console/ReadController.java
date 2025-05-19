package console;

import java.util.Objects;
import java.util.Scanner;

/**
 * Класс ReadController отвечает за чтение данных из консоли.
 * Предоставляет методы для чтения различных типов данных, таких как целые числа, числа с плавающей точкой, строки и т.д.
 */
public class ReadController {

    /**
     * Читает целое число из консоли.
     * @return Прочитанное целое число или null, если ввод пустой.
     * @throws NumberFormatException Если введенные данные не являются целым числом.
     */
    public Integer readInteger() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        try {
            if (input.isEmpty()) {
                return null;
            }
            return Integer.valueOf(input.trim());
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }

    /**
     * Читает число с плавающей точкой (Float) из консоли.
     * @return Прочитанное число типа Float.
     * @throws NumberFormatException Если введенные данные не являются числом с плавающей точкой.
     */
    public Float readFloat() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        try {
            return Float.valueOf(input.trim());
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }

    /**
     * Читает длинное целое число (Long) из консоли.
     * @return Прочитанное число типа Long или null, если ввод пустой.
     * @throws NumberFormatException Если введенные данные не являются длинным целым числом.
     */
    public Long readLong() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.isEmpty()) {
            return null;
        }
        try {
            return Long.valueOf(input.trim());
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }

    /**
     * Читает число с плавающей точкой (Double) из консоли.
     * @return Прочитанное число типа Double или null, если ввод пустой.
     * @throws NumberFormatException Если введенные данные не являются числом с плавающей точкой.
     */
    public Double readDouble() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.isEmpty()) {
            return null;
        }
        try {
            return Double.parseDouble(input.trim());
        } catch (NumberFormatException e){
            throw new NumberFormatException();
        }
    }

    /**
     * Читает строку из консоли.
     * @return Прочитанная строка с заменой запятых на точки и удалением лишних пробелов.
     */
    public String readLine() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().replace(',', '.').trim();
    }

    /**
     * Выводит объект в консоль.
     * @param str Объект для вывода.
     */
    public void printLine(Object str){
        System.out.print(str);
    }
}