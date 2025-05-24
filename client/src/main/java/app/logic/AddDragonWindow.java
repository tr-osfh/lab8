package app.logic;

import connection.Client;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import seClasses.*;

import java.util.ArrayList;
import java.util.HashMap;

public class AddDragonWindow {


    private Runnable callback;
    private Localizer localizer = MainApp.getLocalizer();
    private DragonType type;
    private BrightColor curEyeColor;
    private NaturalColor curHairColor;
    private Dragon dragon;
    private Stage stage;

    private final HashMap<String, DragonType> typeHashMap = new HashMap<>();

    private final HashMap<String, BrightColor> eyeColorHashMap = new HashMap<>();

    private final HashMap<String, NaturalColor> hairColorHashMap = new HashMap<>();


    @FXML
    private Label titleTxt;

    @FXML
    private Label nameTxt;

    @FXML
    private Label yTxt;

    @FXML
    private Label xTxt;

    @FXML
    private Label ageTxt;

    @FXML
    private Label descriptionTxt;

    @FXML
    private Label weightTxt;

    @FXML
    private Label typeTxt;

    @FXML
    private Label addKillerTxt;

    @FXML
    private TextField nameField;

    @FXML
    private TextField xField;

    @FXML
    private TextField yField;

    @FXML
    private TextField ageField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField weightField;

    @FXML
    private ComboBox typeComboBox;

    @FXML
    private CheckBox addKillerCheckBox;

    @FXML
    private GridPane killerCont;

    @FXML
    private Label killerData;



    @FXML
    private Label killerNameTxt;

    @FXML
    private Label killerPassportTxt;

    @FXML
    private Label eyeColorTxt;

    @FXML
    private Label hairColorTxt;

    @FXML
    private Label locationXTxt;

    @FXML
    private Label locationYTxt;

    @FXML
    private Label locationZTxt;

    @FXML
    private Label placeTxt;

    @FXML
    private TextField killerNameField;

    @FXML
    private TextField passportField;

    @FXML
    private TextField locationXField;

    @FXML
    private TextField locationYField;

    @FXML
    private TextField placeField;

    @FXML
    private TextField locationZField;

    @FXML
    private ComboBox eyeColorComboBox;

    @FXML
    private ComboBox hairColorComboBox;


    @FXML
    public void confirm(){
        var errors = new ArrayList<String>();

        String dragonName = nameField.getText().trim();
        if (nameField.getText().isEmpty() || nameField.getText().isBlank()){
            dragonName = null;
            errors.add(localizer.getKeyString("Name") + " " + localizer.getKeyString("NameError"));
        }

        Float x = null;
        try {
            if (xField.getText().isEmpty() || xField.getText().isBlank()){
                x = null;
                errors.add(localizer.getKeyString("xCoordinate") + " " + localizer.getKeyString("XError"));
            }
            else {
                x = Float.parseFloat(xField.getText().trim());
            }
        } catch (NumberFormatException e){
            errors.add(localizer.getKeyString("xCoordinate") + " " + localizer.getKeyString("XError"));
        }

        Integer y = null;
        try {
            if (yField.getText().isEmpty() || yField.getText().isBlank()){
                y = null;
                errors.add(localizer.getKeyString("yCoordinate") + " " + localizer.getKeyString("YError"));
            }
            else{
                y = Integer.parseInt(yField.getText().trim());
            }
        } catch (NumberFormatException e){
            errors.add(localizer.getKeyString("yCoordinate") + " " + localizer.getKeyString("YError"));
        }

        Long age = null;
        try {
            if (!ageField.getText().isEmpty() && !ageField.getText().isBlank()) {
                age = Long.parseLong(ageField.getText().trim());
                if (age <= 0) {
                    errors.add(localizer.getKeyString("Age") + " " + localizer.getKeyString("AgeError"));
                }
            }
        } catch (NumberFormatException e) {
            errors.add(localizer.getKeyString("Age") + " " + localizer.getKeyString("AgeError"));
        }

        String description = descriptionField.getText().trim();
        if (description.isEmpty() || description.isBlank()) {
            description = null;
        }

        Long weight = null;
        try {
            if (!weightField.getText().isEmpty() && !weightField.getText().isBlank()) {
                weight = Long.parseLong(weightField.getText().trim());
                if (weight <= 0) {
                    errors.add(localizer.getKeyString("Weight") + " " + localizer.getKeyString("WeightError"));
                }
            }
        } catch (NumberFormatException e) {
            errors.add(localizer.getKeyString("Weight") + " " + localizer.getKeyString("WeightError"));
        }

        if (type == null){
            errors.add(localizer.getKeyString("Type") + " " + localizer.getKeyString("TypeError"));
        }

        if (addKillerCheckBox.isSelected()){

            String killerName = killerNameField.getText().trim();
            if (killerName.isEmpty() || killerName.isBlank()){
                killerName = null;
                errors.add(localizer.getKeyString("KillerName") + " " + localizer.getKeyString("KillerNameError"));
            }

            String passport = passportField.getText().trim();
            if (passport.isEmpty() || passport.isBlank()){
                passport = null;
                errors.add(localizer.getKeyString("KillerPassport") + " " + localizer.getKeyString("KillerPassportError"));
            }

            if (curEyeColor == null){
                errors.add(localizer.getKeyString("EyeColor") + " " + localizer.getKeyString("EyeColorError"));
            }

            if (curHairColor == null){
                errors.add(localizer.getKeyString("HairColor") + " " + localizer.getKeyString("HairColorError"));
            }

            Integer locationX = null;
            try {
                if (locationXField.getText().isEmpty() || locationXField.getText().isBlank()){
                    locationX = null;
                    errors.add(localizer.getKeyString("LocationX") + " " + localizer.getKeyString("LocationXError"));
                }
                else {
                    locationX = Integer.parseInt(locationXField.getText().trim());
                }

            } catch (NumberFormatException e){
                errors.add(localizer.getKeyString("LocationX") + " " + localizer.getKeyString("LocationXError"));
            }

            Integer locationY = null;
            try {
                if (locationYField.getText().isEmpty() || locationYField.getText().isBlank()){
                    locationY = null;
                    errors.add(localizer.getKeyString("LocationY") + " " + localizer.getKeyString("LocationYError"));
                }
                else {
                    locationY = Integer.parseInt(locationYField.getText().trim());
                }
            } catch (NumberFormatException e){
                errors.add(localizer.getKeyString("LocationY") + " " + localizer.getKeyString("LocationYError"));
            }

            Double locationZ = null;
            try {
                if (locationZField.getText().isEmpty() || locationZField.getText().isBlank()){
                    locationZ = null;
                    errors.add(localizer.getKeyString("LocationZ") + " " + localizer.getKeyString("LocationZError"));
                }
                else {
                    locationZ = Double.parseDouble(locationZField.getText().trim());
                }
            } catch (NumberFormatException e){
                errors.add(localizer.getKeyString("LocationZ") + " " + localizer.getKeyString("LocationZError"));
            }

            String place = placeField.getText();
            if (place.isEmpty() || place.isBlank()){
                place = null;
            } else {
                place = place.trim();
            }

            if (errors.isEmpty()){
                dragon = new Dragon(
                        dragonName,
                        new Coordinates(x, y),
                        age,
                        description,
                        weight,
                        type,
                        new Person(
                                killerName,
                                passport,
                                curEyeColor,
                                curHairColor,
                                new Location(
                                        locationX,
                                        locationY,
                                        locationZ,
                                        place
                                )),
                        Client.getUser().getLogin()
                );
                stage.close();
            } else {
                DialogManager.alert(errors, localizer);
            }

        } else {
            if (errors.isEmpty()){
            dragon = new Dragon(
                    dragonName,
                    new Coordinates(x, y),
                    age,
                    description,
                    weight,
                    type,
                    null,
                    Client.getUser().getLogin()
            );
            stage.close();
            } else {
                System.out.println(123);
                DialogManager.alert(errors, localizer);
            }
        }
    }

    @FXML
    void initialize(){
        killerCont.setManaged(false);
        killerData.setManaged(false);

        typeHashMap.put(localizer.getKeyString("WaterType"), DragonType.WATER);
        typeHashMap.put(localizer.getKeyString("AirType"), DragonType.AIR);
        typeHashMap.put(localizer.getKeyString("UndergroundType"), DragonType.UNDERGROUND);


        eyeColorHashMap.put(localizer.getKeyString("Green"), BrightColor.GREEN);
        eyeColorHashMap.put(localizer.getKeyString("Black"), BrightColor.BLACK);
        eyeColorHashMap.put(localizer.getKeyString("Blue"), BrightColor.BLUE);
        eyeColorHashMap.put(localizer.getKeyString("Yellow"), BrightColor.YELLOW);
        eyeColorHashMap.put(localizer.getKeyString("Orange"), BrightColor.ORANGE);

        hairColorHashMap.put(localizer.getKeyString("Red"), NaturalColor.RED);
        hairColorHashMap.put(localizer.getKeyString("Black"), NaturalColor.BLACK);
        hairColorHashMap.put(localizer.getKeyString("Yellow"), NaturalColor.YELLOW);
        hairColorHashMap.put(localizer.getKeyString("White"), NaturalColor.WHITE);
        hairColorHashMap.put(localizer.getKeyString("Brown"), NaturalColor.BROWN);


        typeComboBox.setItems(FXCollections.observableArrayList(typeHashMap.keySet()));
        typeComboBox.setValue(localizer.getKeyString("None"));
        typeComboBox.setStyle("-fx-font: 12px \"Arial\";");
        typeComboBox.setOnAction(event -> {
            DragonType curType = typeHashMap.get(typeComboBox.getValue());
            this.type = curType;
        });

        hairColorComboBox.setItems(FXCollections.observableArrayList(
                hairColorHashMap.keySet()));
        hairColorComboBox.setValue(localizer.getKeyString("None"));
        hairColorComboBox.setStyle("-fx-font: 12px \"Arial\";");
        hairColorComboBox.setOnAction(event -> {
            curHairColor = hairColorHashMap.get(hairColorComboBox.getValue());
        });

        eyeColorComboBox.setItems(FXCollections.observableArrayList(eyeColorHashMap.keySet()));
        eyeColorComboBox.setValue(localizer.getKeyString("None"));
        eyeColorComboBox.setStyle("-fx-font: 12px \"Arial\";");
        eyeColorComboBox.setOnAction(event -> {
            curEyeColor = eyeColorHashMap.get(eyeColorComboBox.getValue());
        });

        addKillerCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            killerData.setVisible(newValue);
            killerCont.setVisible(newValue);
            killerData.setManaged(newValue);
            killerCont.setManaged(newValue);


            if (stage != null) {
                System.out.println("Бублик с дыркой");
                stage.sizeToScene();
            }
        });



        changeLanguage();
    }




    public void changeLanguage(){
        nameTxt.setText(localizer.getKeyString("Name"));
        yTxt.setText(localizer.getKeyString("yCoordinate"));
        xTxt.setText(localizer.getKeyString("xCoordinate"));
        ageTxt.setText(localizer.getKeyString("Age"));
        descriptionTxt.setText(localizer.getKeyString("Description"));
        weightTxt.setText(localizer.getKeyString("Weight"));
        typeTxt.setText(localizer.getKeyString("Type"));
        addKillerTxt.setText(localizer.getKeyString("AddKiller"));

        killerNameTxt.setText(localizer.getKeyString("KillerName"));
        killerPassportTxt.setText(localizer.getKeyString("KillerPassport"));
        eyeColorTxt.setText(localizer.getKeyString("EyeColor"));
        hairColorTxt.setText(localizer.getKeyString("HairColor"));
        locationXTxt.setText(localizer.getKeyString("LocationX"));
        locationYTxt.setText(localizer.getKeyString("LocationY"));
        locationZTxt.setText(localizer.getKeyString("LocationZ"));
        placeTxt.setText(localizer.getKeyString("Place"));

        killerData.setText(localizer.getKeyString("KillerData"));
        titleTxt.setText(localizer.getKeyString("AddDragon"));
    }

    public void clearFields(){
        nameField.clear();
        xField.clear();
        yField.clear();
        ageField.clear();
        descriptionField.clear();
        weightField.clear();
        typeComboBox.setValue(localizer.getKeyString("None"));
        addKillerCheckBox.setSelected(false);
        killerNameField.clear();
        passportField.clear();
        locationXField.clear();
        locationYField.clear();
        locationZField.clear();
        placeField.clear();
        eyeColorComboBox.setValue(localizer.getKeyString("None"));
        hairColorComboBox.setValue(localizer.getKeyString("None"));
        killerData.setVisible(false);
        killerData.setManaged(false);
        killerCont.setVisible(false);
        killerCont.setManaged(false);
    }

    public void fill(Dragon dragon){
        nameField.setText(dragon.getName());
        xField.setText(String.valueOf(dragon.getCoordinates().getX()));
        yField.setText(String.valueOf(dragon.getCoordinates().getY()));
        ageField.setText(String.valueOf(dragon.getAge()));
        descriptionField.setText(dragon.getDescription());
        weightField.setText(String.valueOf(dragon.getWeight()));
        typeComboBox.setValue(localizer.getKeyString(dragon.getType().toString()));
        this.type = dragon.getType();
        if (dragon.getKiller() != null){
            addKillerCheckBox.setSelected(true);
            killerNameField.setText(dragon.getKiller().getName());
            passportField.setText(dragon.getKiller().getPassportID());
            locationXField.setText(String.valueOf(dragon.getKiller().getLocation().getX()));
            locationYField.setText(String.valueOf(dragon.getKiller().getLocation().getY()));
            locationZField.setText(String.valueOf(dragon.getKiller().getLocation().getZ()));
            placeField.setText(dragon.getKiller().getLocation().getName());
            eyeColorComboBox.setValue(localizer.getKeyString(dragon.getKiller().getEyeColor().toString()));
            curEyeColor = dragon.getKiller().getEyeColor();
            hairColorComboBox.setValue(localizer.getKeyString(dragon.getKiller().getHairColor().toString()));
            curHairColor = dragon.getKiller().getHairColor();
        }
        else {
            addKillerCheckBox.setSelected(false);
            killerNameField.clear();
            passportField.clear();
            locationXField.clear();
            locationYField.clear();
            locationZField.clear();
            placeField.clear();
            eyeColorComboBox.setValue(localizer.getKeyString("None"));
            hairColorComboBox.setValue(localizer.getKeyString("None"));
        }
        titleTxt.setText(localizer.getKeyString("Update"));
    }

    public Dragon getDragon(){
        return this.dragon;
    }

    public void show(){
        this.localizer = MainApp.getLocalizer();
        changeLanguage();
        if (!stage.isShowing()){
            stage.showAndWait();
        }

    }


    public void setStage(Stage stage){
        this.stage = stage;
    }

    public Localizer getLocalizer() {
        return localizer;
    }

    public void setLocalizer(Localizer localizer) {
        this.localizer = localizer;
    }

    public Runnable getCallback() {
        return callback;
    }

    public void setCallback(Runnable callback) {
        this.callback = callback;
    }

}
