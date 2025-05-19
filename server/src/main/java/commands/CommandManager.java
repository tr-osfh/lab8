package commands;

import collection.ServerLogger;
import commands.Command;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;

public class CommandManager {

    /**
     * Обработка команды от клиента через TCP-соединение.
     * @param clientSocket Сокет подключенного клиента.
     */
    public static void handleClientCommand(Socket clientSocket) {
        try (ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream())) {

            Command command = (Command) input.readObject();
            InetSocketAddress sender = (InetSocketAddress) clientSocket.getRemoteSocketAddress();

            ServerLogger.getLogger().log(Level.INFO,
                    "Получена команда %s от %s".formatted(command.getCommandName().toUpperCase(), sender));

            String response = command.execute().getResponse();
            output.writeObject(response);
            output.flush();

        } catch (ClassNotFoundException e) {
            ServerLogger.getLogger().log(Level.SEVERE, "Ошибка десериализации команды: " + e.getMessage());
        } catch (IOException e) {
            ServerLogger.getLogger().log(Level.WARNING, "Ошибка соединения: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                ServerLogger.getLogger().log(Level.SEVERE, "Ошибка закрытия сокета: " + e.getMessage());
            }
        }
    }
}
