package objectsCreation;

import connection.User;
import console.ConsoleReader;
import console.ReadController;

public class UserRegistration extends Creation<User>{

    private final ReadController rc;
    public UserRegistration(ReadController console){
        this.rc = console;
    }

    @Override
    public User create() {
        ConsoleReader consoleReader = new ConsoleReader(rc);
        return new User(consoleReader.readLogin(), consoleReader.readPasswordRegistration());
    }
}
