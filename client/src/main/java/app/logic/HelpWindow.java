package app.logic;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HelpWindow {
    private Stage stage;

    @FXML
    private Label mainTxt;

    @FXML
    private Label txt;

    @FXML
    private Button okBtn;

    @FXML
    public void initialize(){
        okBtn.setOnAction(event -> stage.close());
    }

    public void help(String title, String content) {
        mainTxt.setText(title);
        txt.setText(content);
        stage.showAndWait();
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
}
