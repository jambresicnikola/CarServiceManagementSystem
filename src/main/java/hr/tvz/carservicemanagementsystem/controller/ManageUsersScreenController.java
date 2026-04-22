package hr.tvz.carservicemanagementsystem.controller;

import hr.tvz.carservicemanagementsystem.database.UserDatabaseRepository;
import hr.tvz.carservicemanagementsystem.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Controller for the user management screen.
 * Loads and displays all registered users as cards,
 * and handles adding, editing, and deleting users.
 */
public class ManageUsersScreenController {

    private static final Logger logger = LoggerFactory.getLogger(ManageUsersScreenController.class);

    @FXML private FlowPane userCardsFlowPane;

    /**
     * Invoked automatically by FXMLLoader after all {@code @FXML} fields are injected.
     * Triggers the initial load of user cards.
     */
    public void initialize() {
        logger.debug("Initializing ManageUsersScreenController");
        loadUserCards();
    }

    public void addNewUser() {
        //TO DO: user add
    }

    /**
     * Loads all users from the database and renders a card for each one
     * in the {@link FlowPane}. Clears existing cards before reloading.
     */
    private void loadUserCards() {
        logger.debug("Loading user cards");
        userCardsFlowPane.getChildren().clear();
        List<User> users = UserDatabaseRepository.getInstance().findAll();
        logger.debug("Loaded {} users from database", users.size());

        for (User user : users) {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource(
                                "/hr/tvz/carservicemanagementsystem/userCardItem.fxml"));
                VBox card = loader.load();

                UserCardItemController cardController = loader.getController();
                cardController.setData(user, this::editUser, this::deleteUser);

                userCardsFlowPane.getChildren().add(card);
            } catch (IOException e) {
                logger.error("Failed to load user card for user: {}", user.getEmail(), e);
            }
        }

        logger.debug("User cards loaded successfully");
    }


    private void deleteUser(User user) {
        //TO DO: user delete
    }


    private void editUser(User user) {
        //TO DO: user edit
    }
}