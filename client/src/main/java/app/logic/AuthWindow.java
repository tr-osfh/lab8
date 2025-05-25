package app.logic;

import commands.AuthorizationCommand;
import connection.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.HashMap;
import java.util.Locale;

public class AuthWindow implements DisconnectListener{
    private Runnable callback;
    private Localizer localizer;
    private ConnectionErrorWindow connectWindow;
    private final HashMap<String, Locale> localeHashMap = new HashMap<>() {{
        put("Русский", new Locale("ru"));
        put("Español", new Locale("es", "DO"));
        put("ελληνικά", new Locale("el"));
        put("slovenščina", new Locale("sl"));
    }};

    public AuthWindow(){}

    @FXML
    private Label loginTxt;

    @FXML
    private Label passwordTxt;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField loginField;

    @FXML
    private ComboBox<String> languageComboBox;

    @FXML
    private Button enterBtn;

    @FXML
    void initialize(){
        this.localizer = MainApp.getLocalizer();
        changeLanguage();
        languageComboBox.setItems(FXCollections.observableArrayList(localeHashMap.keySet()));
        languageComboBox.setValue(Client.getLanguage());
        languageComboBox.setOnAction(event -> {
            var newLanguage = languageComboBox.getValue();
            Locale locale = localeHashMap.get(newLanguage);
            localizer.setLocale(locale);
            MainApp.setLocalizer(this.localizer);
            changeLanguage();
            Client.setLanguage(newLanguage);
        });
    }



    public void changeLanguage(){
        loginTxt.setText(localizer.getKeyString("Login"));
        passwordTxt.setText(localizer.getKeyString("Password"));
        enterBtn.setText(localizer.getKeyString("Enter"));
    }

    @FXML
    public void enter() throws InterruptedException {
        if(loginField.getText().isEmpty() || passwordField.getText().isEmpty()){
            DialogManager.alert("Empty", localizer);
            return;
        }
        User user = new User(loginField.getText(), passwordField.getText());
        AuthorizationCommand ac = new AuthorizationCommand(user);
        Client.setCommand(ac);
        int attempts = 0;
        Response response = null;
        while (attempts < 20) {
            response = Client.getResponse();
            if (response != null && response.getType().equals(CommandResponse.AUTHORIZATION)) {
                break;
            }
            Thread.sleep(100);
            attempts++;
        }
        if (response == null){
            DialogManager.alert("Error", localizer);
            return;
        }
        try {
            if (response.getResponseStatus().equals(ResponseStatus.OK)){
                Client.setUser(response.getUser());
                Client.removeDisconnectListener(this);
                callback.run();
            } else {
                DialogManager.alert("NoSuchUser", localizer);
            }
        } catch (Exception e){
            e.printStackTrace();
            DialogManager.alert("SingInError", localizer);
        }

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

    public void setConnectionError(ConnectionErrorWindow connectWindow) {
        this.connectWindow = connectWindow;
    }

    @Override
    public void disconnect() {
        connectWindow.show();
    }

    @Override
    public void connect(){

        Platform.runLater(() -> {
            connectWindow.close();
            DialogManager.inform("Info", "Reconnected", localizer);
        });
    }
}
