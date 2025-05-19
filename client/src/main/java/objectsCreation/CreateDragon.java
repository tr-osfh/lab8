package objectsCreation;

import connection.User;
import console.ConsoleReader;
import console.ReadController;
import seClasses.Coordinates;
import seClasses.Dragon;
import seClasses.Person;

public class CreateDragon extends Creation<Dragon>{
    private final ReadController rc;
    private final User user;

    public CreateDragon(ReadController console, User user){
        this.rc = console;
        this.user = user;
    }

    @Override
    public Dragon create() {
        ConsoleReader consoleReader = new ConsoleReader(rc);
        if (consoleReader.readChoice()){
            return new Dragon(consoleReader.readName(), readCoordinates(), consoleReader.readAge(), consoleReader.readDescription(), consoleReader.readWeight(), consoleReader.readType(), readPerson(), user.getLogin());
        }
        return new Dragon(consoleReader.readName(), readCoordinates(), consoleReader.readAge(), consoleReader.readDescription(), consoleReader.readWeight(), consoleReader.readType(), user.getLogin());
    }
    private Coordinates readCoordinates(){
        return new CreateCoordinates(rc).create();
    }

    private Person readPerson(){
        return new CreatePerson(rc).create();
    }
}
