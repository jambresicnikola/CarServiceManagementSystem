package hr.tvz.carservicemanagementsystem.util;

import hr.tvz.carservicemanagementsystem.app.CarServiceManagementSystemApplication;
import hr.tvz.carservicemanagementsystem.exception.ScreenLoadingException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SceneManager {
    private SceneManager() {}

    public static final Logger logger = LoggerFactory.getLogger(SceneManager.class);

    private static Stage stage;

    public static void setStage(Stage stage) {
        SceneManager.stage = stage;
    }

    public static void openScreen(String fxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CarServiceManagementSystemApplication.class.getResource(fxml));
            Scene scene = new Scene(fxmlLoader.load(), 900, 600);
            stage.setTitle("Car Service Management System");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new ScreenLoadingException();
        }
    }

    public static void openWelcomeScreen() {
        openScreen("/hr/tvz/carservicemanagementsystem/welcomeScreen.fxml");
    }

    public static void openSignUpScreen() {
        openScreen("/hr/tvz/carservicemanagementsystem/signUpScreen.fxml");
    }

    public static void openHomeScreen() {
        if (Session.isAdmin()) {
            openScreen("/hr/tvz/carservicemanagementsystem/adminHomeScreen.fxml");
        } else {
            openScreen("/hr/tvz/carservicemanagementsystem/mechanicHomeScreen.fxml");
        }
    }
}
