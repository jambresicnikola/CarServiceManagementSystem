package hr.tvz.carservicemanagementsystem.util;

import hr.tvz.carservicemanagementsystem.app.CarServiceManagementSystemApplication;
import hr.tvz.carservicemanagementsystem.exception.ScreenLoadingException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Utility class for managing scene transitions in the application.
 * Holds a reference to the primary {@link Stage} and handles FXML loading.
 */
public class SceneManager {
    private SceneManager() {}

    public static final Logger logger = LoggerFactory.getLogger(SceneManager.class);

    private static Stage stage;

    /**
     * Sets the primary stage used for all scene transitions.
     * Must be called once during application startup before any screen is opened.
     *
     * @param stage the primary {@link Stage} provided by the JavaFX runtime
     */
    public static void setStage(Stage stage) {
        SceneManager.stage = stage;
    }

    /**
     * Loads the given FXML file and sets it as the current scene on the primary stage.
     *
     * @param fxml the classpath-relative path to the FXML file
     * @throws ScreenLoadingException if the FXML file cannot be loaded
     */
    public static void openScreen(String fxml) {
        logger.debug("Opening screen: {}", fxml);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CarServiceManagementSystemApplication.class.getResource(fxml));
            Scene scene = new Scene(fxmlLoader.load(), 900, 600);
            stage.setTitle("Car Service Management System");
            stage.setScene(scene);
            stage.show();
            logger.debug("Screen opened successfully: {}", fxml);
        } catch (IOException e) {
            logger.error("Failed to load screen: {}", fxml, e);
            throw new ScreenLoadingException();
        }
    }

    /**
     * Opens the welcome (sign in) screen.
     */
    public static void openWelcomeScreen() {
        openScreen("/hr/tvz/carservicemanagementsystem/welcomeScreen.fxml");
    }

    /**
     * Opens the sign up screen.
     */
    public static void openSignUpScreen() {
        openScreen("/hr/tvz/carservicemanagementsystem/signUpScreen.fxml");
    }

    /**
     * Opens the appropriate home screen based on the logged-in user's role.
     * Admins are directed to the admin home screen, mechanics to the mechanic home screen.
     */
    public static void openHomeScreen() {
        if (Session.isAdmin()) {
            logger.debug("Opening admin home screen");
            openScreen("/hr/tvz/carservicemanagementsystem/adminHomeScreen.fxml");
        } else {
            logger.debug("Opening mechanic home screen");
            openScreen("/hr/tvz/carservicemanagementsystem/mechanicHomeScreen.fxml");
        }
    }
}
