package app.logic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;


public class MainApp extends Application implements SceneSwitchObserver{

    private static Localizer localizer;
    private static Stage index;



    @Override
    public void start(Stage stage) {

        localizer = new Localizer(ResourceBundle.getBundle("locales/gui", new Locale("ru")));
        index = stage;
        welcomeStage();
    }

    private void welcomeStage(){
        var welcomeLoader = new FXMLLoader(getClass().getResource("/markup/welcome.fxml"));
        Parent welcomeRoot = loadFxml(welcomeLoader);
        WelcomeWindow welcomeWindow = welcomeLoader.getController();
        welcomeWindow.setListener(this);
        welcomeWindow.setLocalizer(localizer);
        index.setScene(new Scene(welcomeRoot));
        index.setTitle(localizer.getKeyString("App"));
        index.setResizable(false);
        index.show();
    }

    private void authStage(){
        var authLoader = new FXMLLoader(getClass().getResource("/markup/auth.fxml"));
        Parent authRoot = loadFxml(authLoader);
        AuthWindow authWindow = authLoader.getController();
        authWindow.setCallback(this::mainStage);
        authWindow.setLocalizer(localizer);
        index.setScene(new Scene(authRoot));
        index.setTitle(localizer.getKeyString("App"));
    }

    private void regStage(){
        var regLoader = new FXMLLoader(getClass().getResource("/markup/reg.fxml"));
        Parent regRoot = loadFxml(regLoader);
        RegWindow regWindow = regLoader.getController();
        regWindow.setCallback(this::welcomeStage);
        regWindow.setLocalizer(localizer);
        index.setScene(new Scene(regRoot));
        index.setResizable(false);
        index.setTitle(localizer.getKeyString("App"));
    }

    private void mainStage(){





        var addLoader = new FXMLLoader(getClass().getResource("/markup/addDragon.fxml"));
        Parent addRoot = loadFxml(addLoader);
        Scene addScene = new Scene(addRoot);
        Stage addStage = new Stage();
        addStage.setResizable(false);
        //addStage.setMinWidth(755);
        //addStage.setMinHeight(500);
        addStage.setScene(addScene);
        addStage.setTitle(localizer.getKeyString("App"));
        AddDragonWindow addDragonWindow = addLoader.getController();
        addDragonWindow.setStage(addStage);


        var mainLoader = new FXMLLoader(getClass().getResource("/markup/main.fxml"));
        Parent mainRoot = loadFxml(mainLoader);
        MainWindow mainWindow = mainLoader.getController();
        mainWindow.setListener(this);
        mainWindow.setLocalizer(localizer);
        mainWindow.setAddDragonWindow(addDragonWindow);
        index.setScene(new Scene(mainRoot));
        index.setTitle(localizer.getKeyString("App"));
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