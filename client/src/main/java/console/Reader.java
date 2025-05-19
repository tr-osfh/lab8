package console;


import seClasses.BrightColor;
import seClasses.DragonType;
import seClasses.NaturalColor;

public interface Reader {

    public String readName();

    public Float readCoordinateX();

    public Integer readCoordinateY();

    public Long readAge();

    public String readDescription();

    public Long readWeight();

    public String readPassportID();

    public DragonType readType();

    public BrightColor readBrightColor();

    public NaturalColor readNaturalColor();

    public int readLocationX();

    public Integer readLocationY();

    public double readLocationZ();

    public boolean readChoice();

    public String readLocationName();
}
