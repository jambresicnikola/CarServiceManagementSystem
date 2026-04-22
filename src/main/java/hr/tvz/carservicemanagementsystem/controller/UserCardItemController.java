package hr.tvz.carservicemanagementsystem.controller;

import hr.tvz.carservicemanagementsystem.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

/**
 * Controller for a single user card item displayed in the user management screen.
 * Responsible for populating card UI elements with user data
 * and wiring up edit and delete button actions.
 */
public class UserCardItemController {

    private static final Logger logger = LoggerFactory.getLogger(UserCardItemController.class);

    @FXML private Label userNameLabel;
    @FXML private Label userEmailLabel;
    @FXML private Label userRoleLabel;
    @FXML private Button editButton;
    @FXML private Button deleteButton;

    /**
     * Populates the card with user data and sets button actions.
     *
     * @param user     the user to display
     * @param onEdit   callback invoked when the edit button is clicked
     * @param onDelete callback invoked when the delete button is clicked
     */
    public void setData(User user, Consumer<User> onEdit, Consumer<User> onDelete) {
        logger.debug("Setting card data for user: {}", user.getEmail());
        userNameLabel.setText(user.getFirstName() + " " + user.getLastName());
        userEmailLabel.setText(user.getEmail());
        userRoleLabel.setText(user.getRole().toString());
        editButton.setOnAction(e -> onEdit.accept(user));
        deleteButton.setOnAction(e -> onDelete.accept(user));
    }
}