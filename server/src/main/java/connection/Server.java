package connection;

import collection.ServerLogger;
import console.ConsoleManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class Server {
    private final ResponseSender responseSender = new ResponseSender();
    private final ConsoleManager consoleManager;
    private final int port;
    private volatile boolean running = true;
    private ServerSocket serverSocket;

    private final ForkJoinPool readPool = new ForkJoinPool();

    public Server(int port, ConsoleManager consoleManager) {
        this.port = port;
        this.consoleManager = consoleManager;
    }

    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(100);
            ServerLogger.getLogger().info("Сервер запущен на порту " + port);

            while (running) {
                handleClientConnection();
                checkConsoleInput();
            }

            System.exit(0);

        } catch (IOException e) {
            ServerLogger.getLogger().severe("Ошибка в работе сервера");
        } finally {
            closeResources();
        }
    }

    private void handleClientConnection() {
        try {
            Socket clientSocket = serverSocket.accept();
            ServerLogger.getLogger().info("Подключен клиент: " + clientSocket.getRemoteSocketAddress());

            readPool.execute(() -> {
                try (
                        InputStream input = clientSocket.getInputStream();
                ) {
                    byte[] buffer = new byte[8196 * 8196];
                    int bytesRead;
                    while ((bytesRead = input.read(buffer)) != -1) {
                        final byte[] requestData = Arrays.copyOf(buffer, bytesRead);

                        ProcessCommand processCommand = new ProcessCommand(requestData, clientSocket, responseSender);
                        new Thread(processCommand).start();

                        Arrays.fill(buffer, (byte) 0);
                    }
                } catch (IOException e) {
                    ServerLogger.getLogger().warning("Ошибка клиента");
                } finally {
                    ServerLogger.getLogger().info("Клиент отключился: " + clientSocket.getRemoteSocketAddress());
                    closeClientResources(clientSocket);
                }
            });
        } catch (SocketTimeoutException ignored) {
        } catch (IOException e) {
            ServerLogger.getLogger().warning("Ошибка подключения");
        }
    }

    private void checkConsoleInput() {
        try {
            if (System.in.available() > 0) {
                String line = consoleManager.read().trim().toLowerCase();
                switch (line) {
                    case "exit":
                        running = false;
                        break;
                    default:
                        consoleManager.write("Доступны только команды save, exit");
                }
            }
        } catch (IOException e) {
            ServerLogger.getLogger().warning("Ошибка ввода");
        }
    }

    private void closeClientResources(Socket clientSocket) {
        try {
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
                ServerLogger.getLogger().warning("Клиент отключился");
            }
        } catch (IOException e) {
            ServerLogger.getLogger().warning("Ошибка закрытия клиента");
        }
    }

    private void closeResources() {
        try {
            running = false;
            readPool.shutdownNow();
            responseSender.shutdown();
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                ServerLogger.getLogger().info("Сервер остановлен");
            }
        } catch (IOException e) {
            ServerLogger.getLogger().warning("Ошибка закрытия сервера");
        }
    }
}