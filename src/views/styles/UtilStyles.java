package views.styles;

import java.util.Collections;

import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

public class UtilStyles {
    public static void setErrorBorder(TextField tf) {
        ObservableList<String> styleClass = tf.getStyleClass();
        if (!styleClass.contains("tferror")) {
            styleClass.add("tferror");
        }
    }

    public static void removeErrorBorder(TextField tf) {
        ObservableList<String> styleClass = tf.getStyleClass();
        styleClass.removeAll(Collections.singleton("tferror"));
    }
    
    public static void setSuccessBorder(TextField tf) {
        ObservableList<String> styleClass = tf.getStyleClass();
        if (!styleClass.contains("tfsuccess")) {
            styleClass.add("tfsuccess");
        }
    }

    public static void removeSucsessBorder(TextField tf) {
        ObservableList<String> styleClass = tf.getStyleClass();
        styleClass.removeAll(Collections.singleton("tfsuccess"));
    }

}
