package hr.tvz.carservicemanagementsystem.app;

import hr.tvz.carservicemanagementsystem.util.DialogUtils;
import hr.tvz.carservicemanagementsystem.util.SceneManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CarServiceManagementSystemApplication extends Application {
    private static final Logger logger = LoggerFactory.getLogger(CarServiceManagementSystemApplication.class);

    @Override
    public void start(Stage stage) {
        logger.info("Starting Car Service Management System");

        Thread.currentThread().setUncaughtExceptionHandler((thread, throwable) -> {
            logger.error("Unexpected error on thread: {}", thread.getName(), throwable);

            Platform.runLater(() ->
                    DialogUtils.showError("Unexpected error",
                            "Something went wrong. Please restart the app."));
        });

        SceneManager.setStage(stage);
        logger.debug("Stage initialized, opening welcome screen");
        SceneManager.openWelcomeScreen();
    }
}
