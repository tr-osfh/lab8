package app.logic;

import commands.RegistrationCommand;
import connection.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class RegWindow implements DisconnectListener{
    private Runnable callback;
    private Localizer localizer;
    private ConnectionErrorWindow connectionErrorWindow;
    private final HashMap<String, Locale> localeHashMap = new HashMap<>() {{
        put("Русский", new Locale("ru")); //todo добавить остальные языки
        put("Español", new Locale("es", "DOM"));
    }};

    public RegWindow(){}

    @FXML
    private Label loginTxt;

    @FXML
    private Label passwordTxt;

    @FXML
    private Label repeatPswdTxt;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField repeatPasswordField;

    @FXML
    private ComboBox<String> languageComboBox;

    @FXML
    private Button createBtn;

    @FXML
    void initialize(){
        this.localizer = MainApp.getLocalizer();
        changeLanguage();
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
    }



    private void changeLanguage(){
        loginTxt.setText(localizer.getKeyString("Login"));
        passwordTxt.setText(localizer.getKeyString("Password"));
        repeatPswdTxt.setText(localizer.getKeyString("RepeatPasswod"));
        createBtn.setText(localizer.getKeyString("Create"));
    }

    @FXML
    public void create() throws InterruptedException {
        if (loginField.getText().isEmpty() || passwordField.getText().isEmpty() || repeatPasswordField.getText().isEmpty()){
            DialogManager.alert("Empty", localizer);
            return;
        }
        if (!passwordField.getText().equals(repeatPasswordField.getText())){
            DialogManager.alert("notEquals", localizer);
            return;
        }

        User user = new User(loginField.getText(), passwordField.getText());
        RegistrationCommand rc = new RegistrationCommand(user);
        Client.setCommand(rc);
        int attempts = 0;
        Response response = null;
        while (attempts < 20) {
            response = Client.getResponse();
            if (response != null && response.getType().equals(CommandResponse.REGISTRATION)) {
                break;
            }
            Thread.sleep(100);
            attempts++;
        }
        if (response == null){
            DialogManager.alert("Error", localizer);
            return;
        }
        try{
            if (response.getResponseStatus().equals(ResponseStatus.OK)){
                DialogManager.inform("Info", localizer.getKeyString("SuccessReg") + user.getLogin(), localizer);
                callback.run();
                Client.removeDisconnectListener(this);
            } else {
                DialogManager.inform("Info", localizer.getKeyString("UnableToReg") + user.getLogin(), localizer);
            }
        } catch (Exception e){
            DialogManager.alert("RegistrationError", localizer);
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

    public void setConnectionError(ConnectionErrorWindow connectionError){
        this.connectionErrorWindow = connectionError;
    }

    @Override
    public void disconnect() {

        connectionErrorWindow.show();
    }

    @Override
    public void connect(){
        Platform.runLater(() -> {
            connectionErrorWindow.close();
            DialogManager.inform("Info", "Reconnected", localizer);
        });
    }
}
