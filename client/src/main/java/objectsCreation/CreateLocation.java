package objectsCreation;

import console.ConsoleReader;
import console.ReadController;
import seClasses.Coordinates;
import seClasses.Location;

import java.util.Locale;

public class CreateLocation extends Creation<Location>{
    private final ReadController rc;

    public CreateLocation(ReadController console){
        this.rc = console;
    }

    @Override
    public Location create(){
        ConsoleReader consoleReader = new ConsoleReader(rc);
        return new Location(
                consoleReader.readLocationX(),
                consoleReader.readLocationY(),
                consoleReader.readLocationZ(),
                consoleReader.readLocationName()
        );
    }
}