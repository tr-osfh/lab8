package app.logic;

import java.io.File;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Comparator;
import java.util.stream.Collectors;

import commands.*;
import connection.*;
import file.ExecuteScript;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Window;
import seClasses.Dragon;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainWindow extends Window implements DisconnectListener, RefreshCollectionListener{
    private SceneSwitchObserver listener;
    private AddDragonWindow addDragonWindow;
    private ConnectionErrorWindow connectionErrorWindow;
    private Timeline refreshTimer;
    private boolean isFiltered = false;
    private Dragon clickedDragon;
    private HashMap<String, Color> colors = new HashMap<>();
    private Random random = new Random();
    private GraphicsContext gc;
    private List<DragonHitBox> dragonHitBoxes = new ArrayList<>();

    private Localizer localizer;
    private final HashMap<String, Locale> localeHashMap = new HashMap<>() {{
        put("Русский", new Locale("ru")); //todo добавить остальные языки
        put("Español", new Locale("es", "DOM"));
    }};

    @FXML
    private Label userTxt;

    @FXML
    private Button helpBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private Button headBtn;

    @FXML
    private Button infoBtn;

    @FXML
    private Button addBtn;

    @FXML
    private Button addIfMinBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private Button removeLowerBtn;

    @FXML
    private Button executeScriptBtn;

    @FXML
    private Button removeByIdBtn;

    @FXML
    private Button filterContainsNameBtn;

    @FXML
    private Button filterStartsWithNameBtn;

    @FXML
    private Button sumOfAgeBtn;

    @FXML
    private Button clearBtn;

    @FXML
    private ComboBox<String> languageComboBox;

    @FXML
    private TableView<Dragon> dragonTable;

    @FXML
    private TableColumn<Dragon, Long> idColoumn;
    @FXML
    private TableColumn<Dragon, String> nameColoumn;
    @FXML
    private TableColumn<Dragon, Float> xCoordinateColoumn;
    @FXML
    private TableColumn<Dragon, Integer> yCoordinateColoumn;
    @FXML
    private TableColumn<Dragon, LocalDateTime> dateColoumn;
    @FXML
    private TableColumn<Dragon, Long> ageColoumn;
    @FXML
    private TableColumn<Dragon, String> descriptionColoumn;
    @FXML
    private TableColumn<Dragon, Long> weightColoumn;
    @FXML
    private TableColumn<Dragon, String> typeColoumn;
    @FXML
    private TableColumn<Dragon, String> personNameColoumn;
    @FXML
    private TableColumn<Dragon, String> passportIdColoumn;
    @FXML
    private TableColumn<Dragon, String> eyeColorColoumn;
    @FXML
    private TableColumn<Dragon, String> hairColorColoumn;
    @FXML
    private TableColumn<Dragon, Integer> xLocationColoumn;
    @FXML
    private TableColumn<Dragon, Integer> yLocationColoumn;
    @FXML
    private TableColumn<Dragon, Double> zLocationColoumn;
    @FXML
    private TableColumn<Dragon, String> locationNameColoumn;
    @FXML
    private Canvas dragonCanvas;
    @FXML
    private Pane dragonBase;
    @FXML
    private Label reconnectionText;
    @FXML
    private ProgressIndicator reconnectionBar;
    @FXML
    private Button removeFilterBtn;
    @FXML
    private ImageView image;



    @FXML
    void initialize() {
        this.localizer = MainApp.getLocalizer();
        changeLanguage();
        gc = dragonCanvas.getGraphicsContext2D();
        Platform.runLater(() -> setCollection(Client.getDragons()));

        fillTable();

        userTxt.setText(Client.getUser().getLogin());


        languageComboBox.setItems(FXCollections.observableArrayList(localeHashMap.keySet()));
        languageComboBox.setValue(Client.getLanguage());
        languageComboBox.setStyle("-fx-font: 12px \"Arial\";");
        languageComboBox.setOnAction(event -> {
            var newLanguage = languageComboBox.getValue();
            Locale locale = localeHashMap.get(newLanguage);
            localizer.setLocale(locale);
            MainApp.setLocalizer(this.localizer);
            changeLanguage();
            Client.setLanguage(newLanguage);
        });

        image = new ImageView(new Image(getClass().getResourceAsStream("/Sample_User_Icon.png")));


        dragonCanvas.widthProperty().bind(dragonBase.widthProperty());
        dragonCanvas.heightProperty().bind(dragonBase.heightProperty());
        dragonCanvas.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                double mouseX = event.getX();
                double mouseY = event.getY();

                for (DragonHitBox hitBox : dragonHitBoxes) {
                    if (hitBox.contains(mouseX, mouseY)) {
                        Dragon selectedDragon = hitBox.getDragon();
                        dragonClick(selectedDragon);
                        break;
                    }
                }
            }
        });
    }


    @FXML
    public void help() {
        Command upd = new HelpCommand(Client.getUser());
        Client.setCommand(upd);
        try {
            int attempts = 0;
            Response response = null;
            while (attempts < 20) {
                response = Client.getResponse();
                if (response != null && response.getType().equals(CommandResponse.HELP)) {
                    break;
                }
                Thread.sleep(100);
                attempts++;
            }
            if (response == null) {
                DialogManager.alert("TimeoutError", localizer);
                return;
            }
            if (response.getResponseStatus().equals(ResponseStatus.OK) && response.getType().equals(CommandResponse.HELP)) {
                DialogManager.help("HelpBtn", response.getCommandCollection().stream()
                        .map(element -> localizer.getKeyString(element)).collect(Collectors.joining("\n")), localizer);

            } else {
                DialogManager.alert("Error", localizer);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void exit() {
        System.exit(0);
    }

    @FXML
    public void head() {
        Command upd = new HeadCommand(Client.getUser());
        Client.setCommand(upd);
        try {
            int attempts = 0;
            Response response = null;
            while (attempts < 20) {
                response = Client.getResponse();
                if (response != null && response.getType().equals(CommandResponse.HEAD)) {
                    break;
                }
                Thread.sleep(100);
                attempts++;
            }
            if (response == null) {
                DialogManager.alert("TimeoutError", localizer);
                return;
            }
            if (response.getResponseStatus().equals(ResponseStatus.OK) && response.getType().equals(CommandResponse.HEAD)) {
                if (response.getDragons() != null){
                    isFiltered = true;
                    PriorityQueue<Dragon> dragons = new PriorityQueue<>();
                    dragons.addAll(response.getDragons());
                    setCollection(dragons);
                } else {
                    DialogManager.inform("Info", localizer.getKeyString(response.getResponse()), localizer);
                }

            } else {
                DialogManager.alert("Error", localizer);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void info() {
        try {
            Command info = new InfoCommand(Client.getUser());
            Client.setCommand(info);
            Thread.sleep(500);
            Response response = Client.getResponse();
            var message = MessageFormat.format(
                    localizer.getKeyString("InfoReturn"),
                    response.getInfo().getType(),
                    response.getInfo().getNumberOfDragons(),
                    response.getInfo().getYourDragons(),
                    localizer.getDate(response.getInfo().getDateOfInit()));
            DialogManager.inform("Info", message, localizer);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void add() {
        addDragonWindow.clearFields();
        addDragonWindow.show();
        addDragonWindow.setLocalizer(localizer);
        addDragonWindow.changeLanguage();
        Dragon dragon = addDragonWindow.getDragon();
        if (dragon != null) {
            Command add = new commands.AddCommand(dragon, Client.getUser());
            Client.setCommand(add);
            try {

                int attempts = 0;
                Response response = null;
                while (attempts < 20) {
                    response = Client.getResponse();
                    if (response != null && response.getType().equals(CommandResponse.ADD)) {
                        break;
                    }
                    Thread.sleep(100);
                    attempts++;
                }
                if (response == null) {
                    DialogManager.alert("TimeoutError", localizer);
                    return;
                }

                if (response.getResponseStatus().equals(ResponseStatus.OK) && response.getType().equals(CommandResponse.ADD)) {
                    DialogManager.inform("Info", localizer.getKeyString(response.getResponse()), localizer);
                } else {
                    DialogManager.alert("Error", localizer);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void addIfMin() {
        addDragonWindow.clearFields();
        addDragonWindow.show();
        addDragonWindow.setLocalizer(localizer);
        addDragonWindow.changeLanguage();
        Dragon dragon = addDragonWindow.getDragon();
        if (dragon != null) {
            Command addIfMin = new commands.AddIfMinCommand(dragon, Client.getUser());
            Client.setCommand(addIfMin);
            try {

                int attempts = 0;
                Response response = null;
                while (attempts < 20) {
                    response = Client.getResponse();
                    if (response != null && response.getType().equals(CommandResponse.ADD_IF_MIN)) {
                        break;
                    }
                    Thread.sleep(100);
                    attempts++;
                }
                if (response == null) {
                    DialogManager.alert("TimeoutError", localizer);
                    return;
                }
                if (response.getResponseStatus().equals(ResponseStatus.OK) && response.getType().equals(CommandResponse.ADD_IF_MIN)) {
                    DialogManager.inform("Info", localizer.getKeyString(response.getResponse()), localizer);
                } else {
                    System.out.println(response.getResponseStatus());
                    DialogManager.alert("Error", localizer);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void update() {
        long ID;
        Long id;
        Dragon dragon;
        if (clickedDragon == null){
            id = DialogManager.getId(localizer);
        } else {
            id = clickedDragon.getId();
            clickedDragon = null;
        }

        if (id != null) {
            ID = id;
            dragon = Client.getDragons().stream()
                    .filter(d -> d.getId() == ID && d.getUserLogin().equals(Client.getUser().getLogin()))
                    .findAny()
                    .orElse(null);
            if (dragon == null) {
                DialogManager.alert("NoSuchDragon", localizer);
                return;
            } else {
                addDragonWindow.fill(dragon);
                addDragonWindow.show();
            }


            var updDragon = addDragonWindow.getDragon();
            if (updDragon != null) {

                updDragon.setId(ID);
                Command upd = new UpdateIdCommand(ID, updDragon, Client.getUser());
                Client.setCommand(upd);
                try {
                    int attempts = 0;
                    Response response = null;
                    while (attempts < 20) {
                        response = Client.getResponse();
                        if (response != null && response.getType().equals(CommandResponse.UPDATE)) {
                            break;
                        }
                        Thread.sleep(100);
                        attempts++;
                    }
                    if (response == null) {
                        DialogManager.alert("TimeoutError", localizer);
                        return;
                    }
                    if (response.getResponseStatus().equals(ResponseStatus.OK) && response.getType().equals(CommandResponse.UPDATE)) {
                        DialogManager.inform("Info", localizer.getKeyString(response.getResponse()), localizer);
                    } else {
                        DialogManager.alert("Error", localizer);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            return;
        }
    }

    @FXML
    public void removeLower() {
        addDragonWindow.clearFields();
        addDragonWindow.show();
        addDragonWindow.setLocalizer(localizer);
        addDragonWindow.changeLanguage();
        Dragon dragon = addDragonWindow.getDragon();
        if (dragon != null) {
            Command removeLower = new commands.RemoveLowerCommand(dragon, Client.getUser());
            Client.setCommand(removeLower);
            try {

                int attempts = 0;
                Response response = null;
                while (attempts < 20) {
                    response = Client.getResponse();
                    if (response != null && response.getType().equals(CommandResponse.REMOVE_LOWER)) {
                        break;
                    }
                    Thread.sleep(100);
                    attempts++;
                }
                if (response == null) {
                    DialogManager.alert("TimeoutError", localizer);
                    return;
                }
                System.out.println(response.getType());
                if (response.getResponseStatus().equals(ResponseStatus.OK) && response.getType().equals(CommandResponse.REMOVE_LOWER)) {
                    DialogManager.inform("Info", localizer.getKeyString(response.getResponse()), localizer);
                } else {
                    DialogManager.alert("Error", localizer);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void executeScript() {
        File file;

        file = DialogManager.getScript(localizer);

        if (file != null){
            ExecuteScript script = new ExecuteScript(file, Client.getUser());
            script.readScript();
            Command exeScr = new ExecuteScriptCommand(script.getCommandQueue(), Client.getUser());
            Client.setCommand(exeScr);
            try {
                int attempts = 0;
                Response response = null;
                while (attempts < 40) {
                    response = Client.getResponse();
                    if (response != null && response.getType().equals(CommandResponse.EXECUTE_SCRIPT)) {
                        break;
                    }
                    Thread.sleep(100);
                    attempts++;
                }
                if (response == null) {
                    DialogManager.alert("TimeoutError", localizer);
                    return;
                }
                if (response.getResponseStatus().equals(ResponseStatus.OK) && response.getType().equals(CommandResponse.EXECUTE_SCRIPT)) {
                    if (response.getResponseList() == null){
                        DialogManager.alert("Error", localizer);
                    } else {
                        ScriptResponse.process(response.getResponseList(), localizer, this);
                    }

                } else {
                    DialogManager.alert("Error", localizer);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void removeById() {
        long ID;
        Long id;
        Dragon dragon;
        if (clickedDragon == null){
            id = DialogManager.getId(localizer);
        } else {
            id = clickedDragon.getId();
            clickedDragon = null;
        }

        if (id != null) {
            ID = id;
            dragon = Client.getDragons().stream()
                    .filter(d -> d.getId() == ID && d.getUserLogin().equals(Client.getUser().getLogin()))
                    .findAny()
                    .orElse(null);
            if (dragon == null) {
                DialogManager.alert("NoSuchDragon", localizer);
                return;
            }
            Command upd = new RemoveByIdCommand(ID, Client.getUser());
            Client.setCommand(upd);
            try {
                int attempts = 0;
                Response response = null;
                while (attempts < 40) {
                    response = Client.getResponse();
                    if (response != null && response.getType().equals(CommandResponse.REMOVE_BY_ID)) {
                        break;
                    }
                    Thread.sleep(100);
                    attempts++;
                }
                if (response == null) {
                    DialogManager.alert("TimeoutError", localizer);
                    return;
                }
                if (response.getResponseStatus().equals(ResponseStatus.OK) && response.getType().equals(CommandResponse.REMOVE_BY_ID)) {
                    DialogManager.inform("Info", localizer.getKeyString(response.getResponse()), localizer);
                } else {
                    System.out.println(response.getResponseStatus());
                    DialogManager.alert("Error", localizer);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            return;
        }
    }

    @FXML
    public void filterContainsName(){
        String namePart = DialogManager.getNamePart(localizer);
        if (namePart != null) {;
            Command upd = new FilterContainsNameCommand(namePart, Client.getUser());
            Client.setCommand(upd);
            try {
                int attempts = 0;
                Response response = null;
                while (attempts < 20) {
                    response = Client.getResponse();
                    if (response != null && response.getType().equals(CommandResponse.FILTER_CONTAINS_NAME)) {
                        break;
                    }
                    Thread.sleep(100);
                    attempts++;
                }
                if (response == null) {
                    DialogManager.alert("TimeoutError", localizer);
                    return;
                }
                if (response.getResponseStatus().equals(ResponseStatus.OK) && response.getType().equals(CommandResponse.FILTER_CONTAINS_NAME)) {
                    if (response.getDragons() != null){
                        isFiltered = true;
                        PriorityQueue<Dragon> dragons = new PriorityQueue<>();
                        dragons.addAll(response.getDragons());
                        setCollection(dragons);
                    } else {
                        DialogManager.inform("Info", localizer.getKeyString(response.getResponse()), localizer);
                    }

                } else {
                    DialogManager.alert("Error", localizer);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            return;
        }

    }

    @FXML
    public void filterStartsWithName(){
        String namePart = DialogManager.getStartNamePart(localizer);
        if (namePart != null) {;
            Command upd = new FilterStartsWithNameCommand(namePart, Client.getUser());
            Client.setCommand(upd);
            try {
                int attempts = 0;
                Response response = null;
                while (attempts < 20) {
                    response = Client.getResponse();
                    if (response != null && response.getType().equals(CommandResponse.FILTER_STARTS_WITH_NAME)) {
                        break;
                    }
                    Thread.sleep(100);
                    attempts++;
                }
                if (response == null) {
                    DialogManager.alert("TimeoutError", localizer);
                    return;
                }
                if (response.getResponseStatus().equals(ResponseStatus.OK) && response.getType().equals(CommandResponse.FILTER_STARTS_WITH_NAME)) {
                    if (response.getDragons() != null){
                        isFiltered = true;
                        PriorityQueue<Dragon> dragons = new PriorityQueue<>();
                        dragons.addAll(response.getDragons());
                        setCollection(dragons);
                    } else {
                        DialogManager.inform("Info", localizer.getKeyString(response.getResponse()), localizer);
                    }

                } else {
                    DialogManager.alert("Error", localizer);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            return;
        }
    }

    @FXML
    public void removeFilter(){
        setCollection(Client.getDragons());
        isFiltered = false;
    }

    @FXML
    public void sumOfAge(){
        Command upd = new SumOfAgeCommand(Client.getUser());
        Client.setCommand(upd);
        try {
            int attempts = 0;
            Response response = null;
            while (attempts < 20) {
                response = Client.getResponse();
                if (response != null && response.getType().equals(CommandResponse.SUM_OF_AGE)) {
                    break;
                }
                Thread.sleep(100);
                attempts++;
            }
            if (response == null) {
                DialogManager.alert("TimeoutError", localizer);
                return;
            }
            if (response.getResponseStatus().equals(ResponseStatus.OK) && response.getType().equals(CommandResponse.SUM_OF_AGE)) {
                DialogManager.inform("Info", localizer.getKeyString("SumOfAgeRet") + response.getResponse(), localizer);
            } else if (response.getResponseStatus().equals(ResponseStatus.ERROR) && response.getType().equals(CommandResponse.SUM_OF_AGE)) {
                DialogManager.inform("Info", localizer.getKeyString("NoAgeData"), localizer);
            } else {
                DialogManager.alert("Error", localizer);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void clear(){
        Command upd = new ClearCommand(Client.getUser());
        Client.setCommand(upd);
        try {
            int attempts = 0;
            Response response = null;
            while (attempts < 20) {
                response = Client.getResponse();
                if (response != null && response.getType().equals(CommandResponse.CLEAR)) {
                    break;
                }
                Thread.sleep(100);
                attempts++;
            }
            if (response == null) {
                DialogManager.alert("TimeoutError", localizer);
                return;
            }
            if (response.getResponseStatus().equals(ResponseStatus.OK) && response.getType().equals(CommandResponse.CLEAR)) {
                DialogManager.inform("Info", localizer.getKeyString(response.getResponse()), localizer);
            } else {
                DialogManager.alert("Error", localizer);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private double normalize(double value, double scale) {
        return (2.0 / Math.PI) * Math.atan(value / scale);
    }


    private void drawVisual(List<Dragon> lDragons){
        gc.clearRect(0, 0, dragonCanvas.getWidth(), dragonCanvas.getHeight());

        double canvasWidth = dragonCanvas.getWidth() - 100;
        double canvasHeight = dragonCanvas.getHeight() - 100;

        DragonVisual.setCanvasHeight(canvasHeight);
        DragonVisual.setCanvasWidth(canvasWidth);

        double scale = 200;

        for (Dragon dragon : lDragons) {

            String owner = dragon.getUserLogin();
            if (!colors.containsKey(owner)) {
                double hue = random.nextDouble() * 360;
                double saturation = 0.7 + random.nextDouble() * (1 - 0.7);
                double brightness = 0.8 + random.nextDouble() * (1 - 0.8);
                colors.put(owner, Color.hsb(hue, saturation, brightness));
            }

            var size = Math.min(Math.max(30, (dragon.getWeight() == null) ? 30 : dragon.getWeight() * 5), 100);

            double xNorm = normalize(dragon.getCoordinates().getX(), scale);
            double yNorm = normalize(dragon.getCoordinates().getY(), scale);

            double x = (xNorm + 1.0) / 2.0 * canvasWidth;
            double y = (yNorm + 1.0) / 2.0 * canvasHeight;

            double radius = size / 2.0;
            dragonHitBoxes.add(new DragonHitBox(dragon, x, y, radius));
            DragonVisual.draw(gc, size, x + radius, y, colors.get(owner));

        }
    }



    public void setCollection(PriorityQueue<Dragon> ds){
        PriorityQueue<Dragon> dragons = ds;
        List<Dragon> sortedDragons = new ArrayList<>(dragons);
        sortedDragons.sort(Comparator.comparingLong(Dragon::getId));

        dragonTable.setItems(FXCollections.observableArrayList(sortedDragons));

        dragonTable.getSortOrder().clear();
        dragonTable.getSortOrder().add(idColoumn);
        dragonTable.sort();

        drawVisual(sortedDragons);

    }

    private void changeLanguage(){
        fillTable();
        helpBtn.setText(localizer.getKeyString("HelpBtn"));
        exitBtn.setText(localizer.getKeyString("ExitBtn"));
        headBtn.setText(localizer.getKeyString("HeadBtn"));
        infoBtn.setText(localizer.getKeyString("InfoBtn"));
        addBtn.setText(localizer.getKeyString("AddBtn"));
        addIfMinBtn.setText(localizer.getKeyString("AddIfMinBtn"));
        updateBtn.setText(localizer.getKeyString("UpdateBtn"));
        removeLowerBtn.setText(localizer.getKeyString("RemoveLowerBtn"));
        executeScriptBtn.setText(localizer.getKeyString("ExecuteScriptBtn"));
        removeByIdBtn.setText(localizer.getKeyString("RemoveByIdBtn"));
        filterContainsNameBtn.setText(localizer.getKeyString("FilterContainsNameBtn"));
        filterStartsWithNameBtn.setText(localizer.getKeyString("FilterStartsWithNameBtn"));
        sumOfAgeBtn.setText(localizer.getKeyString("SumOfAgeBtn"));
        clearBtn.setText(localizer.getKeyString("Clear"));

        idColoumn.setText(localizer.getKeyString("Id"));
        nameColoumn.setText(localizer.getKeyString("Name"));
        xCoordinateColoumn.setText(localizer.getKeyString("xCoordinate"));
        yCoordinateColoumn.setText(localizer.getKeyString("yCoordinate"));
        dateColoumn.setText(localizer.getKeyString("Date"));
        ageColoumn.setText(localizer.getKeyString("Age"));
        descriptionColoumn.setText(localizer.getKeyString("Description"));
        weightColoumn.setText(localizer.getKeyString("Weight"));
        typeColoumn.setText(localizer.getKeyString("Type"));
        personNameColoumn.setText(localizer.getKeyString("KillerName"));
        passportIdColoumn.setText(localizer.getKeyString("KillerPassport"));
        eyeColorColoumn.setText(localizer.getKeyString("EyeColor"));
        hairColorColoumn.setText(localizer.getKeyString("HairColor"));
        xLocationColoumn.setText(localizer.getKeyString("LocationX"));
        yLocationColoumn.setText(localizer.getKeyString("LocationY"));
        zLocationColoumn.setText(localizer.getKeyString("LocationZ"));
        locationNameColoumn.setText(localizer.getKeyString("Place"));

        reconnectionText.setText(localizer.getKeyString("Reconnection"));
        removeFilterBtn.setText(localizer.getKeyString("Unfilter"));
    }

    private void fillTable(){
        idColoumn.setCellValueFactory(dragon -> new SimpleLongProperty(dragon.getValue().getId()).asObject());
        nameColoumn.setCellValueFactory(dragon -> new SimpleStringProperty(dragon.getValue().getName()));
        xCoordinateColoumn.setCellValueFactory(dragon -> new SimpleFloatProperty(dragon.getValue().getCoordinates().getX()).asObject());
        yCoordinateColoumn.setCellValueFactory(dragon -> new SimpleIntegerProperty(dragon.getValue().getCoordinates().getY()).asObject());
        dateColoumn.setCellValueFactory(dragon -> new SimpleObjectProperty<>(dragon.getValue().getCreationDate()));
        ageColoumn.setCellValueFactory(dragon ->
                new SimpleObjectProperty<>(dragon.getValue().getAge())
        );
        descriptionColoumn.setCellValueFactory(dragon ->
                new SimpleStringProperty(dragon.getValue().getDescription() != null ? dragon.getValue().getDescription() : localizer.getKeyString("None")));
        weightColoumn.setCellValueFactory(dragon ->
                new SimpleObjectProperty<>(dragon.getValue().getWeight())
        );
        typeColoumn.setCellValueFactory(dragon -> new SimpleStringProperty(localizer.getKeyString(dragon.getValue().getType().toString())));
        personNameColoumn.setCellValueFactory(dragon -> new SimpleStringProperty(
                dragon.getValue().getKiller() != null ? dragon.getValue().getKiller().getName() : localizer.getKeyString("None")));
        passportIdColoumn.setCellValueFactory(dragon -> new SimpleStringProperty(
                dragon.getValue().getKiller() != null ? dragon.getValue().getKiller().getPassportID() : localizer.getKeyString("None")));
        eyeColorColoumn.setCellValueFactory(dragon -> new SimpleStringProperty(
                dragon.getValue().getKiller() != null ? localizer.getKeyString(dragon.getValue().getKiller().getEyeColor().toString()) : localizer.getKeyString("None")));
        hairColorColoumn.setCellValueFactory(dragon -> new SimpleStringProperty(
                dragon.getValue().getKiller() != null ? localizer.getKeyString(dragon.getValue().getKiller().getHairColor().toString()) : localizer.getKeyString("None")));
        xLocationColoumn.setCellValueFactory(dragon -> new SimpleObjectProperty<>(
                (dragon.getValue().getKiller() != null && dragon.getValue().getKiller().getLocation() != null) ? dragon.getValue().getKiller().getLocation().getX() : null));

        yLocationColoumn.setCellValueFactory(dragon -> new SimpleObjectProperty<>(
                (dragon.getValue().getKiller() != null && dragon.getValue().getKiller().getLocation() != null) ? dragon.getValue().getKiller().getLocation().getY() : null));
        zLocationColoumn.setCellValueFactory(dragon -> new SimpleObjectProperty<>(
                (dragon.getValue().getKiller() != null && dragon.getValue().getKiller().getLocation() != null) ? dragon.getValue().getKiller().getLocation().getZ() : null)
        );
        locationNameColoumn.setCellValueFactory(dragon -> new SimpleStringProperty(
                dragon.getValue().getKiller() != null ? dragon.getValue().getKiller().getLocation().getName() : localizer.getKeyString("None")));

        dragonTable.getSortOrder().add(idColoumn);
        idColoumn.setComparator(Long::compareTo);
        dragonTable.setRowFactory(tv -> {
            var row = new TableRow<Dragon>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && !row.isEmpty()) {
                    clickedDragon = row.getItem();

                }
            });
            return row;
        });

        ageColoumn.setCellFactory(column -> new TableCell<Dragon, Long>() {
            @Override
            protected void updateItem(Long age, boolean empty) {
                super.updateItem(age, empty);
                if (empty) {
                    setText("");
                } else {
                    setText(age == null ? localizer.getKeyString("None") : age.toString());
                }
            }
        });

        weightColoumn.setCellFactory(column -> new TableCell<Dragon, Long>() {
            @Override
            protected void updateItem(Long weight, boolean empty) {
                super.updateItem(weight, empty);
                if (empty) {
                    setText("");
                } else {
                    setText(weight == null ? localizer.getKeyString("None") : weight.toString());
                }
            }
        });

        xLocationColoumn.setCellFactory(column -> new TableCell<Dragon, Integer>() {
            @Override
            protected void updateItem(Integer x, boolean empty) {
                super.updateItem(x, empty);
                if (empty) {
                    setText("");
                } else if (x == null) {
                    setText(localizer.getKeyString("None"));
                } else {
                    setText(x.toString());
                }
            }
        });

        yLocationColoumn.setCellFactory(column -> new TableCell<Dragon, Integer>() {
            @Override
            protected void updateItem(Integer y, boolean empty) {
                super.updateItem(y, empty);
                if (empty) {
                    setText("");
                } else if (y == null) {
                    setText(localizer.getKeyString("None"));
                } else {
                    setText(y.toString());
                }
            }
        });

        zLocationColoumn.setCellFactory(column -> new TableCell<Dragon, Double>() {
            @Override
            protected void updateItem(Double z, boolean empty) {
                super.updateItem(z, empty);
                if (empty) {
                    setText("");
                } else if (z == null) {
                    setText(localizer.getKeyString("None"));
                } else {
                    setText(String.format("%.2f", z));
                }
            }
        });

    }

    private void dragonClick(Dragon dragon) {
        clickedDragon = dragon;
        PriorityQueue<Dragon> dragons = new PriorityQueue<>();
        dragons.add(dragon);
        setCollection(dragons);
    }

    public void setConnectionError(ConnectionErrorWindow connectionError){
        this.connectionErrorWindow = connectionError;
    }

    public boolean isFiltered() {
        return isFiltered;
    }

    public void setFiltered(boolean filtered) {
        isFiltered = filtered;
    }

    public void setListener(SceneSwitchObserver listener) {
        this.listener = listener;
    }

    public Localizer getLocalizer() {
        return localizer;
    }

    public void setLocalizer(Localizer localizer) {
        this.localizer = localizer;
    }

    public void setAddDragonWindow(AddDragonWindow addDragonWindow) {
        this.addDragonWindow = addDragonWindow;
    }

    @Override
    public void disconnect() {
        reconnectionBar.setVisible(true);
        reconnectionText.setVisible(true);
        connectionErrorWindow.show();
    }

    @Override
    public void connect(){
        Platform.runLater(() -> {
            connectionErrorWindow.close();
            reconnectionBar.setVisible(false);
            reconnectionText.setVisible(false);
        });
    }

    @Override
    public void refresh(){
        Platform.runLater(() -> {
            if (!isFiltered){
                setCollection(Client.getDragons());
            }
        });
    }
}
