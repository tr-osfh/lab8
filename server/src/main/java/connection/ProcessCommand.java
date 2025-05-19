package connection;

import collection.ServerLogger;
import commands.Command;
import commands.CommandSerializer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ProcessCommand implements Runnable {
    private final Socket clientSocket;
    private final byte[] requestData;
    private final ResponseSender responseSender;

    public ProcessCommand(byte[] requestData, Socket clientSocket, ResponseSender responseSender) {
        this.clientSocket = clientSocket;
        this.requestData = requestData;
        this.responseSender = responseSender;
    }

    @Override
    public void run() {
        try {
            OutputStream output = clientSocket.getOutputStream();
            Command command = CommandSerializer.deserialize(requestData);
            ServerLogger.getLogger().info(
                    "Получена команда: " + command.getCommandName() +
                            "\nОт пользователя: " + command.getUser()
            );
            Response response = command.execute();

            responseSender.sendResponse(output, CommandSerializer.serialize(response));
        } catch (ClassNotFoundException | IOException e) {
            ServerLogger.getLogger().warning("Ошибка обработки команды.");
        }
    }
}
