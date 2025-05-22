package app.logic;

import commands.AuthorizationCommand;
import commands.RegistrationCommand;
import connection.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class AuthWindow {
    private Runnable callback;
    private Localizer localizer;
    private final HashMap<String, Locale> localeHashMap = new HashMap<>() {{
        put("Русский", new Locale("ru")); //todo добавить остальные языки
        put("Español", new Locale("es", "DOM"));
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
                System.out.println("Aga");
                Client.setUser(response.getUser());
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
}
