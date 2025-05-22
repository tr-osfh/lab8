package app.logic;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class DialogWindow {

    private Long id;
    private String name;

    private Stage stage;
    private Localizer localizer;
    private String txt;
    private String todo;

    @FXML
    private Label mainTxt;
    @FXML
    private Label whatToDo;
    @FXML
    private TextField infoField;

    @FXML
    private Button okBtn;
    @FXML
    public void initialize(){
        okBtn.setOnMouseClicked(event -> stage.close());
    }

    public Long getId(){
        try{
            id = Long.parseLong(infoField.getText());
            return id;
        } catch (NumberFormatException e){
            DialogManager.alert("idError", localizer);
        }
        return null;
    }

    public String getNamePart(){
        try{
            name = infoField.getText();
            return name;
        } catch (NumberFormatException e){
            DialogManager.alert("nameError", localizer);
        }
        return null;
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void setLocalizer(Localizer localizer){
        this.localizer = localizer;
    }
    public void show() {
        if (!stage.isShowing()) {
            mainTxt.setText(localizer.getKeyString(txt));
            whatToDo.setText(localizer.getKeyString(todo));
            stage.showAndWait();
        }
    }

    public void clear() {
        id = null;
        name = null;
        txt = "";
        todo = "";
        mainTxt.setText("");
        whatToDo.setText("");
        infoField.clear();
    }

    public void setAction(String action){
        switch (action){
            case "update":
                txt = "EnterUpdId";
                todo = "EnterId";
                return;
            case "remove":
                txt = "EnterDelId";
                todo = "EnterId";
                return;
            case "filterPart":
                txt = "FilterContainsNameBtn";
                todo = "EnterNamePart";
                return;
            case "filterStart":
                txt = "FilterStartsWithNameBtn";
                todo = "EnterNamePart";
                return;
            default:
                todo = "None";
                txt = "None";
        }
    }
}
