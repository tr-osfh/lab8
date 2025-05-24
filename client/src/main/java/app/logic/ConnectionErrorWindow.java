package app.logic;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ConnectionErrorWindow {
    private Localizer localizer;
    private Stage stage;

    @FXML
    private Label txt;

    @FXML
    private Button reconnectBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private Label error;

    @FXML
    void initialize() {
        this.localizer = MainApp.getLocalizer();
        changeLanguage();
    }


    @FXML
    public void exit(){
        System.exit(0);
    }


    @FXML
    public void reconnect(){
        stage.close();
    }

    private void changeLanguage(){
        txt.setText(localizer.getKeyString("NoConnection"));
        error.setText(localizer.getKeyString("Error"));
        exitBtn.setText(localizer.getKeyString("ExitBtn"));
        reconnectBtn.setText(localizer.getKeyString("Reconnect"));
    }


    public void setLocalizer(Localizer localizer) {
        this.localizer = localizer;
    }

    public void show(){
        this.localizer = MainApp.getLocalizer();
        Platform.runLater(()->{
            changeLanguage();
            if (!stage.isShowing()){
                stage.showAndWait();
            }
        });
    }
    public void close(){
        stage.close();
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

}
