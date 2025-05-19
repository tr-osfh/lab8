package objectsCreation;

import console.ConsoleReader;
import console.ReadController;
import seClasses.Location;
import seClasses.Person;

public class CreatePerson extends Creation<Person>{
    private final ReadController rc;
    public CreatePerson(ReadController  console){
        this.rc = console;
    }

    @Override
    public Person create(){
        ConsoleReader consoleReader = new ConsoleReader(rc);
        return new Person(
                consoleReader.readName(),
                consoleReader.readPassportID(),
                consoleReader.readBrightColor(),
                consoleReader.readNaturalColor(),
                readLocation()
        );
    }

    private Location readLocation(){
        return new CreateLocation(rc).create();
    }
}
