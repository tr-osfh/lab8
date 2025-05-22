import collection.CollectionManager;
import console.ConsoleManager;
import file.fileReader;
import seClasses.Dragon;
import connection.Server;

public class Main {
    private static final ConsoleManager consoleManager = new ConsoleManager();
    private final static Integer serverPort = 21214;
    public static void main(String[] args) {
        fileReader.read();
        Server server = new Server(serverPort, consoleManager);
        server.run();
    }
}
