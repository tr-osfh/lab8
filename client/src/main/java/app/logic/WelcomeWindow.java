package app.logic;

import connection.Client;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;


import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class WelcomeWindow{
    private SceneSwitchObserver listener;

    private Localizer localizer;
    private final HashMap<String, Locale> localeHashMap = new HashMap<>() {{
        put("Русский", new Locale("ru"));
        put("Español", new Locale("es", "DOM"));
    }};

    public WelcomeWindow() {
    }

    @FXML
    private Label welcomeText;

    @FXML
    private Button registrationBtn;

    @FXML
    private Button authorizationBtn;

    @FXML
    private ComboBox<String> languageComboBox;

    @FXML
    void initialize(){

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

    @FXML
    public void authorization() {
        if (listener != null){
            listener.onAuthRequest();
        }
    }

    @FXML
    public void registration(){
        if (listener != null){
            listener.onRegRequest();
        }
    }

    public void changeLanguage(){
        welcomeText.setText(localizer.getKeyString("Welcome"));
        authorizationBtn.setText(localizer.getKeyString("Authorization"));
        registrationBtn.setText(localizer.getKeyString("Registration"));
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

}
