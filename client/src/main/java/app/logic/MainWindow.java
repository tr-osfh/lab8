package app.logic;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Comparator;

import commands.*;
import connection.Client;
import connection.CommandResponse;
import connection.Response;
import connection.ResponseStatus;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Window;
import javafx.util.Duration;
import seClasses.Dragon;
import seClasses.DragonType;

public class MainWindow extends Window {
    private SceneSwitchObserver listener;
    private AddDragonWindow addDragonWindow;
    private Timeline refreshTimer;
    private boolean isFiltered = false;

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
    void initialize() {
        localizer = MainApp.getLocalizer();
        setCollection(Client.getDragons());

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
                    Dragon clickedDragon = row.getItem();
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

        userTxt.setText(Client.getUser().getLogin());
        languageComboBox.setItems(FXCollections.observableArrayList(localeHashMap.keySet()));
        languageComboBox.setValue(Client.getLanguage());
        languageComboBox.setStyle("-fx-font: 12px \"Arial\";");
        languageComboBox.setOnAction(event -> {
            var newLanguage = languageComboBox.getValue();
            localizer.setResourceBundle(ResourceBundle.getBundle("locales/gui", localeHashMap.get(newLanguage)));
            Client.setLanguage(newLanguage);
            changeLanguage(); //todo Добавить смену языка
        });

        refreshTimer = new Timeline(
                new KeyFrame(
                        Duration.seconds(3),
                        event -> {
                            if (Client.isNeedRefresh() && !isFiltered) {
                                setCollection(Client.getDragons());
                                Client.setNeedRefresh(false);
                            }
                        }
                )
        );
        refreshTimer.setCycleCount(Timeline.INDEFINITE);
        refreshTimer.play();

    }


    @FXML
    public void help(
    ) {
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

                if (response.getResponseStatus().equals(ResponseStatus.OK) && response.equals(CommandResponse.ADD)) {
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
        Dragon dragon;
        Long id = DialogManager.getId(localizer);
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
                        System.out.println(response.getResponseStatus());
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
                    System.out.println(response.getResponseStatus());
                    DialogManager.alert("Error", localizer);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void executeScript() {
    }

    @FXML
    public void removeById() {

        long ID;
        Dragon dragon;
        Long id = DialogManager.getId(localizer);
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
                while (attempts < 20) {
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
                DialogManager.inform("Info", localizer.getKeyString("SumOfAgeBtn") + response.getResponse(), localizer);
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
                DialogManager.inform("Info", response.getResponse(), localizer);
            } else if (response.getResponseStatus().equals(ResponseStatus.ERROR) && response.getType().equals(CommandResponse.SUM_OF_AGE)) {
                DialogManager.inform("Info", localizer.getKeyString("NoAgeData"), localizer);
            } else {
                DialogManager.alert("Error", localizer);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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

    public void setCollection(PriorityQueue<Dragon> ds){
        PriorityQueue<Dragon> dragons = ds;
        List<Dragon> sortedDragons = new ArrayList<>(dragons);
        sortedDragons.sort(Comparator.comparingLong(Dragon::getId));

        dragonTable.setItems(FXCollections.observableArrayList(sortedDragons));

        dragonTable.getSortOrder().clear();
        dragonTable.getSortOrder().add(idColoumn);
        dragonTable.sort();
    }

    private void changeLanguage(){
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
    }
}
