package hr.tvz.carservicemanagementsystem.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;

import java.util.Optional;

public class DialogUtils {
    private DialogUtils() {

    }

    private static void showAlert(String title, String message, Alert alert) {
        alert.setTitle("Car Service Management System");
        alert.setHeaderText(title);

        TextArea textArea = new TextArea(message);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefRowCount(10);

        alert.getDialogPane().setContent(textArea);
        alert.showAndWait();
    }

    public static void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        showAlert(title, message, alert);
    }

    public static void showInformation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        showAlert(title, message, alert);
    }

    public static boolean showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Car Service Management System");
        alert.setHeaderText(title);

        TextArea textArea = new TextArea(message);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefRowCount(10);

        alert.getDialogPane().setContent(textArea);

        Optional<ButtonType> result = alert.showAndWait();

        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
