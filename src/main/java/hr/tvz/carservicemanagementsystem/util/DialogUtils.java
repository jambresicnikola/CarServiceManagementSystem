package hr.tvz.carservicemanagementsystem.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;

import java.util.Optional;

/**
 * Utility class for displaying JavaFX alert dialogs.
 * All dialogs are modal and display the application name as the window title.
 */
public class DialogUtils {
    private DialogUtils() {}

    private static final String APP_NAME = "Car Service Management System";

    /**
     * Configures and displays the given alert with a title, message, and scrollable text area.
     *
     * @param title   the header text of the dialog
     * @param message the body message to display
     * @param alert   the pre-configured {@link Alert} instance to show
     */
    private static void showAlert(String title, String message, Alert alert) {
        alert.setTitle(APP_NAME);
        alert.setHeaderText(title);

        TextArea textArea = new TextArea(message);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefRowCount(10);

        alert.getDialogPane().setContent(textArea);
        alert.showAndWait();
    }

    /**
     * Displays an error dialog with the given title and message.
     *
     * @param title   the header text
     * @param message the error message
     */
    public static void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        showAlert(title, message, alert);
    }

    /**
     * Displays an informational dialog with the given title and message.
     *
     * @param title   the header text
     * @param message the informational message
     */
    public static void showInformation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        showAlert(title, message, alert);
    }

    /**
     * Displays a confirmation dialog and returns whether the user confirmed.
     *
     * @param title   the header text
     * @param message the confirmation message
     * @return {@code true} if the user clicked OK, {@code false} otherwise
     */
    public static boolean showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle(APP_NAME);
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
