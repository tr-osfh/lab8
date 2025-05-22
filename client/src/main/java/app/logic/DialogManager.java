package app.logic;

import connection.Response;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seClasses.Dragon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class DialogManager {
    private static ErrorWindow errorWindow;
    private static InfoWindow infoWindow;
    private static DialogWindow dialogWindow;

    static{
        FXMLLoader errorLoader = new FXMLLoader(DialogManager.class.getResource("/markup/error.fxml"));
        Parent errorRoot = loadFxml(errorLoader);
        Scene errorScene = new Scene(errorRoot);
        Stage errorStage = new Stage();
        errorStage.initModality(Modality.APPLICATION_MODAL);
        errorStage.setScene(errorScene);
        errorStage.setResizable(false);
        errorWindow = errorLoader.getController();
        errorWindow.setStage(errorStage);

        FXMLLoader infoLoader = new FXMLLoader(DialogManager.class.getResource("/markup/info.fxml"));
        Parent infoRoot = loadFxml(infoLoader);
        Scene infoScene = new Scene(infoRoot);
        Stage infoStage = new Stage();
        infoStage.initModality(Modality.APPLICATION_MODAL);
        infoStage.setScene(infoScene);
        infoStage.setResizable(false);
        infoWindow = infoLoader.getController();
        infoWindow.setStage(infoStage);

        FXMLLoader dialogLoader = new FXMLLoader(DialogManager.class.getResource("/markup/dialog.fxml"));
        Parent dialogRoot = loadFxml(dialogLoader);
        Scene dialogScene = new Scene(dialogRoot);
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setScene(dialogScene);
        dialogStage.setResizable(false);
        dialogWindow = dialogLoader.getController();
        dialogWindow.setStage(dialogStage);
    }

    public static void alert(String title, Localizer localizer){
            errorWindow.alert(localizer.getKeyString("Error"), localizer.getKeyString(title));
    }

    public static void alert(ArrayList<String> errors, Localizer localizer){
        errorWindow.alert(localizer.getKeyString("Error"), String.join("\n", errors));
    }

    public static void inform(String title, String txt, Localizer localizer){
        infoWindow.inform(localizer.getKeyString(title), txt); //todo Сюда прилетает респонс, который надо декодировать
    }

    public static Long getId(Localizer localizer){
        dialogWindow.setLocalizer(localizer);
        dialogWindow.clear();
        dialogWindow.setAction("update");
        dialogWindow.show();
        return dialogWindow.getId();
    }

    public static String getNamePart(Localizer localizer){
        dialogWindow.setLocalizer(localizer);
        dialogWindow.clear();
        dialogWindow.setAction("filterPart");
        dialogWindow.show();
        return dialogWindow.getNamePart();
    }

    public static String getStartNamePart(Localizer localizer){
        dialogWindow.setLocalizer(localizer);
        dialogWindow.clear();
        dialogWindow.setAction("filterStart");
        dialogWindow.show();
        return dialogWindow.getNamePart();
    }


    private static Parent loadFxml(FXMLLoader loader) {
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);

        }
        return parent;
    }
}
