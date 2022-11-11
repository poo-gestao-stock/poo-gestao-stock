package utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MeuAlert {

    public static void mostrar(String title, String message, AlertType type) {
        Alert a = new Alert(AlertType.NONE);
        // set alert type
        a.setAlertType(type == null ? AlertType.INFORMATION : type);
        a.setContentText(message);
        a.setHeaderText(title);
        // show the dialog
        a.show();
    }
}
