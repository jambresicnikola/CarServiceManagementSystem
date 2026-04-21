package hr.tvz.carservicemanagementsystem.controller;

import hr.tvz.carservicemanagementsystem.dto.UserSignUpDTO;
import hr.tvz.carservicemanagementsystem.exception.ValidationException;
import hr.tvz.carservicemanagementsystem.service.UserService;
import hr.tvz.carservicemanagementsystem.util.DialogUtils;
import hr.tvz.carservicemanagementsystem.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller for the sign up screen.
 * Handles user registration by collecting form input,
 * delegating validation and persistence to {@link UserService}.
 */
public class SignUpScreenController {
    private static final Logger logger = LoggerFactory.getLogger(SignUpScreenController.class);

    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;

    /**
     * Handles the sign up button action.
     * Collects form data, attempts registration via {@link UserService},
     * and navigates back to the welcome screen on success.
     * Displays an error dialog if validation fails.
     */
    public void signUp() {
        UserSignUpDTO userSignUpDTO = new UserSignUpDTO(
                firstNameTextField.getText().trim(),
                lastNameTextField.getText().trim(),
                emailTextField.getText().trim(),
                passwordField.getText(),
                confirmPasswordField.getText()
        );

        logger.debug("Sign up attempt for email: {}", userSignUpDTO.email());

        try {
            UserService.register(userSignUpDTO);

            logger.info("User registered successfully: {}", userSignUpDTO.email());
            DialogUtils.showInformation("Registration successful",
                    "You have successfully registered. Enjoy the app!");

            SceneManager.openWelcomeScreen();
        } catch (ValidationException e) {
            logger.warn("Registration validation failed for email: {} — {}",
                    userSignUpDTO.email(), e.getMessage());
            DialogUtils.showError("Registration failed", e.getMessage());
        }
    }
}
