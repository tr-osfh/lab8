package file;

import collection.ServerLogger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class fileReader {
    private static String password;
    private static String user;

    public static void read() {
        String filePath = "C:/Users/Sch/Desktop/.pass";
        //System.getenv("PASS_PATH")

        if (filePath == null || filePath.isBlank()) {
            ServerLogger.getLogger().warning("Переменная окружения не найдена, подключение к базе данных невозможно!");
            System.exit(0);
        }

        try {
            Path path = Paths.get(filePath);

            if (!Files.exists(path)) {
                ServerLogger.getLogger().warning("Файл не найден: " + filePath + " \n подключение к базе данных невозможно!");
                System.exit(0);
            }
            String[] tmp = Files.readString(path, StandardCharsets.UTF_8).split(" ");

            if (tmp.length < 2) {
                ServerLogger.getLogger().warning("Неверный формат файла с паролем");
                System.exit(0);
            }

            user = tmp[0].trim();
            password = tmp[1].trim();

        } catch (IOException e) {
            ServerLogger.getLogger().warning("Ошибка чтения файла");
            System.exit(0);
        }
    }

    public static String getPassword() {
        return password;
    }

    public static String getUser(){
        return user;
    }
}