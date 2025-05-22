package seClasses;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Info implements Serializable {

    @Serial
    private static final long serialVersionUID = 66L;

    int numberOfDragons;
    String type;
    LocalDateTime dateOfInit;
    long yourDragons;

    public Info(int numberOfDragons, String type, LocalDateTime dateOfInit, long yourDragons) {
        this.numberOfDragons = numberOfDragons;
        this.type = type;
        this.dateOfInit = dateOfInit;
        this.yourDragons = yourDragons;
    }

    public int getNumberOfDragons() {
        return numberOfDragons;
    }

    public void setNumberOfDragons(int numberOfDragons) {
        this.numberOfDragons = numberOfDragons;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getDateOfInit() {
        return dateOfInit;
    }

    public void setDateOfInit(LocalDateTime dateOfInit) {
        this.dateOfInit = dateOfInit;
    }

    public long getYourDragons() {
        return yourDragons;
    }

    public void setYourDragons(int yourDragons) {
        this.yourDragons = yourDragons;
    }
}
