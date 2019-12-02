package ca.macewan.c305;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;

public class PopUp {

    public static void Error(String err) {
        Alert dialog = new Alert(Alert.AlertType.ERROR, err, ButtonType.OK);
        dialog.setHeaderText("Error");
        dialog.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        dialog.show();
    }
}
