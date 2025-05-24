package connection;

import app.logic.DisconnectListener;
import commands.Command;
import commands.CommandsList;
import commands.ExecuteScriptCommand;
import console.*;
import file.ExecuteScript;
import seClasses.Dragon;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.concurrent.BlockingQueue;

public class Client {
    private final String serverAddress;
    private final int port;
    private SocketChannel socketChannel;
    private Selector selector;
    private ConsoleReader cr;
    private static Command  pendingCommand;
    private static boolean isConnected = false;
    private boolean isWaitingForResponse = false;
    private boolean running = true;
    private boolean connectionProblem = true;
    private static User user;
    private static String language = "Русский";
    private static volatile Response mainResponse;
    private static PriorityQueue<Dragon> dragons = new PriorityQueue<>();
    private static volatile boolean needRefresh = false;
    private static ArrayList<DisconnectListener> disconnectListeners = new ArrayList<>();
    public Client(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
        this.cr = new ConsoleReader(new ReadController());
    }

    public void connect() throws IOException {

        socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(serverAddress, port));

        selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
    }

    public void run() {
        if (connectionProblem && !isConnected){
            try {
                connect();
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    noConnectionHandler();

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        while (true) {
            try {
                while (running) {
                    int readyChannels = selector.select(100);
                    if (readyChannels == 0){
                        if (!socketChannel.isConnected()){
                            noConnectionHandler();
                        }
                    }

                    if (readyChannels > 0) {
                        Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

                        while (keys.hasNext()) {
                            SelectionKey key = keys.next();
                            keys.remove();
                            if (key.isConnectable()) {
                                successConnect(key);
                            } else if (key.isReadable()) {
                                read(key);
                            } else if (key.isWritable()) {
                                write(key);
                            }
                        }
                    }
                    processConsoleInput();
                }
                System.exit(0);


            } catch (IOException e) {
                try {
                    noConnectionHandler();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (ClassNotFoundException e) {
                System.out.println("Ошибка десериализации");
                break;
            }
        }
    }

    private void noConnectionHandler() throws IOException {
        pendingCommand = null;
        connectionProblem = false;
        isWaitingForResponse = false;

        SelectionKey key = socketChannel.keyFor(selector);

        try {
            Thread.sleep(5000);
            connect();
            for (DisconnectListener listener : disconnectListeners){
                listener.disconnect();
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    private void processConsoleInput() {
        if (isConnected && !isWaitingForResponse) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                if (reader.ready()) {
                    Command cmd = readCommand();
                    if (cmd != null) {
                        pendingCommand = cmd;
                        SelectionKey key = socketChannel.keyFor(selector);
                        if (key != null) key.interestOps(SelectionKey.OP_WRITE | SelectionKey.OP_READ);
                    }
                }
            } catch (IOException e) {
                cr.printLine("Ошибка ввода");
            }
        }
    }



    private void successConnect(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        if (channel.isConnectionPending()) {
            channel.finishConnect();
        }
        channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

        for (DisconnectListener listener : disconnectListeners){
            listener.connect();
        }

        isConnected = true;
        connectionProblem = false;
    }

    private void read(SelectionKey key) throws IOException, ClassNotFoundException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(8192 * 8192);
        int bytesRead = channel.read(buffer);

        if (bytesRead > 0) {
            buffer.flip();
            byte[] data = new byte[buffer.limit()];
            buffer.get(data);
            Response response = CommandSerializer.deserialize(data);
            System.out.println("Получен ответ от сервера: " + response.getResponseStatus());

            if (response.getResponseStatus().equals(ResponseStatus.REFRESH)){
                dragons.clear();
                dragons.addAll(response.getDragons());
                System.out.println("gbdjjasdfjl;ksadghjkol;asdgfhjkonl;adfgbnjkl;");
                for (Dragon dragon : response.getDragons()) {
                    System.out.println(dragon);
                }
                needRefresh = true;
            }
            else {
                Client.mainResponse = response;
            }
            key.interestOps(SelectionKey.OP_WRITE | SelectionKey.OP_READ);
            isWaitingForResponse = false;
                cr.printLine("Введите команду: \n");
        } else if (bytesRead == -1) {
            noConnectionHandler();
            isConnected = false;
        }
        buffer.clear();
    }

    private void write(SelectionKey key) throws IOException {
        if (pendingCommand != null) {
            ByteBuffer buffer = ByteBuffer.wrap(CommandSerializer.serialize(pendingCommand));
            SocketChannel channel = (SocketChannel) key.channel();
            channel.write(buffer);
            key.interestOps(SelectionKey.OP_WRITE | SelectionKey.OP_READ);
            pendingCommand = null;
            isWaitingForResponse = true;
        }
    }

    public static void setCommand(Command cmd){
        pendingCommand = cmd;
    }


    private Command readCommand() { //todo Обратить внимание
        String[] input = cr.readCommand();

        if (input == null || input.length == 0) return null;

        CommandsList type = CommandDecoder.getCommandType(input[0]);

        if (type == CommandsList.EXIT) {
            closeResources();
            running = false;
            return null;
        } else if (type == CommandsList.EXECUTE_SCRIPT && input.length > 1 && user != null) {
            ExecuteScript script = new ExecuteScript(new File(input[1]), user);
            script.readScript();
            return new ExecuteScriptCommand(script.getCommandQueue(), user);
        }
        return CommandFactory.createCommand(type, input, user);
    }

    private void closeResources() {
        try {
            if (socketChannel != null && socketChannel.isOpen()) {
                socketChannel.close();
            }
            if (selector != null && selector.isOpen()) {
                selector.close();
            }
        } catch (IOException e) {
            System.err.println("Ошибка при закрытии ресурсов: " + e.getMessage());
        }
        isConnected = false;
        connectionProblem = true;
    }

    public static PriorityQueue<Dragon> getDragons() {
        return dragons;
    }

    public static String getLanguage(){
        return language;
    }

    public static void setLanguage(String language) {
        Client.language = language;
    }

    public static Response getResponse() {
        Response rep = mainResponse;
        mainResponse = null;
        return rep;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Client.user = user;
    }

    public static boolean isNeedRefresh() {
        return needRefresh;
    }

    public static void setNeedRefresh(boolean needRefresh) {
        Client.needRefresh = needRefresh;
    }

    public static boolean isConnected() {
        return isConnected;
    }

    public static void addDisconnectListener(DisconnectListener listener){
        disconnectListeners.add(listener);
    }

    public static void removeDisconnectListener(DisconnectListener listener){
        disconnectListeners.remove(listener);
    }
}
