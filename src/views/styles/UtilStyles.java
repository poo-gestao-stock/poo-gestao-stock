package views.styles;

import java.util.Collections;

import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

public class UtilStyles {
    public static void setRed(TextField tf) {
        ObservableList<String> styleClass = tf.getStyleClass();
        if (!styleClass.contains("tferror")) {
            styleClass.add("tferror");
        }
    }

    public static void removeRed(TextField tf) {
        ObservableList<String> styleClass = tf.getStyleClass();
        styleClass.removeAll(Collections.singleton("tferror"));
    }
}
