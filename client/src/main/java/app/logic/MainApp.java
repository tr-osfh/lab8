package app.logic;

import connection.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;


public class MainApp extends Application implements SceneSwitchObserver{

    private static Localizer localizer;
    private static Stage index;
    private static ConnectionErrorWindow connectWindow;



    @Override
    public void start(Stage stage) {
        localizer = new Localizer(new Locale("ru"));
        index = stage;

        var connectLoader = new FXMLLoader(getClass().getResource("/markup/connectionError.fxml"));
        Parent connectionRoot = loadFxml(connectLoader);
        Scene connectionScene = new Scene(connectionRoot);
        Stage connectionStage = new Stage();
        connectionStage.setResizable(false);
        connectionStage.setScene(connectionScene);
        connectWindow = connectLoader.getController();
        connectWindow.setLocalizer(localizer);
        connectWindow.setStage(connectionStage);

        welcomeStage();
    }

    private void welcomeStage(){
        var welcomeLoader = new FXMLLoader(getClass().getResource("/markup/welcome.fxml"));
        Parent welcomeRoot = loadFxml(welcomeLoader);
        WelcomeWindow welcomeWindow = welcomeLoader.getController();
        welcomeWindow.setListener(this);
        welcomeWindow.setLocalizer(localizer);
        index.setScene(new Scene(welcomeRoot));
        index.setResizable(false);
        index.show();
    }

    private void authStage(){
        var authLoader = new FXMLLoader(getClass().getResource("/markup/auth.fxml"));
        Parent authRoot = loadFxml(authLoader);
        AuthWindow authWindow = authLoader.getController();
        authWindow.setCallback(this::mainStage);
        authWindow.setLocalizer(localizer);
        authWindow.setConnectionError(connectWindow);
        index.setScene(new Scene(authRoot));
        Client.addDisconnectListener(authWindow);
    }

    private void regStage(){
        var regLoader = new FXMLLoader(getClass().getResource("/markup/reg.fxml"));
        Parent regRoot = loadFxml(regLoader);
        RegWindow regWindow = regLoader.getController();
        regWindow.setCallback(this::welcomeStage);
        regWindow.setLocalizer(localizer);
        regWindow.setConnectionError(connectWindow);
        index.setScene(new Scene(regRoot));
        index.setResizable(false);
        Client.addDisconnectListener(regWindow);
    }

    private void mainStage(){

        var addLoader = new FXMLLoader(getClass().getResource("/markup/addDragon.fxml"));
        Parent addRoot = loadFxml(addLoader);
        Scene addScene = new Scene(addRoot);
        Stage addStage = new Stage();
        addStage.setResizable(false);
        addStage.setScene(addScene);
        AddDragonWindow addDragonWindow = addLoader.getController();
        addDragonWindow.setLocalizer(localizer);
        addDragonWindow.setStage(addStage);


        var mainLoader = new FXMLLoader(getClass().getResource("/markup/main.fxml"));
        Parent mainRoot = loadFxml(mainLoader);
        MainWindow mainWindow = mainLoader.getController();
        mainWindow.setLocalizer(localizer);
        mainWindow.setListener(this);
        mainWindow.setConnectionError(connectWindow);
        mainWindow.setAddDragonWindow(addDragonWindow);
        Client.addRefreshListener(mainWindow);
        Client.addDisconnectListener(mainWindow);

        index.setScene(new Scene(mainRoot));
        index.setResizable(false);
        index.show();

    }

    private Parent loadFxml(FXMLLoader loader){
        Parent parent = null;
        try{
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parent;
    }

    @Override
    public void onAuthRequest() {
        authStage();
    }

    @Override
    public void onRegRequest() {
        regStage();
    }

    public static Localizer getLocalizer() {
        return localizer;
    }

    public static void setLocalizer(Localizer localizer) {
        MainApp.localizer = localizer;
    }
}