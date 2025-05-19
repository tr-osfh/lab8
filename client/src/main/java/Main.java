import connection.Client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;

public class Main {
    private static InetAddress host;
    private static final int serverPort = 21213;

    public static void main(String[] args) {
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        Client client = new Client(host.getHostName(), serverPort);
        try {
            System.out.println("Для получения справки по доступным командам введите help");
            client.run();
        } catch (NoSuchElementException e) {
            System.out.println("Что-то пошло не так...");
        }
    }
}