package objectsCreation;

import console.ConsoleReader;
import console.ReadController;
import seClasses.Coordinates;

public class CreateCoordinates extends Creation<Coordinates>{
    private final ReadController rc;
    public CreateCoordinates(ReadController console){
        this.rc = console;
    }
    @Override
    public Coordinates create() {
        ConsoleReader consoleReader = new ConsoleReader(rc);
        return new Coordinates(
                consoleReader.readCoordinateX(),
                consoleReader.readCoordinateY()
        );
    }
}
