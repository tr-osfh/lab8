package app.logic;

import commands.RegistrationCommand;
import connection.Client;
import connection.Response;
import connection.ResponseStatus;
import connection.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class RegWindow {
    private Runnable callback;
    private Localizer localizer;
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
        languageComboBox.setItems(FXCollections.observableArrayList(localeHashMap.keySet()));
        languageComboBox.setValue(Client.getLanguage());
        languageComboBox.setStyle("-fx-font: 12px \"Arial\";");
        languageComboBox.setOnAction(event -> {
            var newLanguage = languageComboBox.getValue();
            localizer.setResourceBundle(ResourceBundle.getBundle("locales/gui", localeHashMap.get(newLanguage)));
            Client.setLanguage(newLanguage);
            changeLanguage();
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
        Thread.sleep(100);
        try{
            Response response = Client.getResponse();
            if (response.getResponseStatus().equals(ResponseStatus.OK)){
                Client.setUser(user);
            } else {
                DialogManager.alert("RegistrationError", localizer);
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
}
