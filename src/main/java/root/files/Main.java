package root.files;

import root.files.collection.CollectionManager;
import root.files.commands.CommandManager;
import root.files.console.ConsoleManager;
import root.files.file.FileManager;

/**
 * Главный класс приложения. Отвечает за инициализацию основных компонентов:
 * - Загрузку данных из CSV-файла
 * - Настройку менеджеров для работы с коллекцией, командами и консольным вводом/выводом
 * - Запуск основного цикла обработки команд
 *
 * <p>Путь к файлу данных задаётся через переменную окружения DB_FILE_PATH.</p>
 *
 * <p>При возникновении ошибок работы с файлом выводит соответствующее сообщение и трассировку стека.</p>
 */
public class Main {
    /**
     * Точка входа в приложение. Выполняет:
     * 1. Инициализацию FileManager для работы с файлом
     * 2. Создание и связывание менеджеров коллекции, команд, консоли
     * 3. Загрузку начальных данных из CSV-файла
     * 4. Запуск бесконечного цикла обработки пользовательских команд
     *
     * @param args Аргументы командной строки (не используются)
     * @throws NullPointerException Если не удаётся найти файл по указанному пути
     * @throws Exception При возникновении непредвиденных ошибок выполнения
     */
    public static void main(String[] args) {
        try {
            String filePath = System.getenv("DB_FILE_PATH");
            FileManager fm = new FileManager(filePath);
            CollectionManager receiver = new CollectionManager();
            ConsoleManager consoleManager = new ConsoleManager();
            CommandManager cm = new CommandManager(receiver, consoleManager);
            receiver.setCommandManager(cm);
            receiver.setFileManager(fm);
            receiver.setDragons(fm.loadCSV());
            for (; ; ) {
                cm.executeCommand();
            }
        } catch (NullPointerException e) {
            System.out.println("Файл не найден.");
        } catch (Exception e){
            System.out.println("Неизвестная ошибка");
        }
    }
}