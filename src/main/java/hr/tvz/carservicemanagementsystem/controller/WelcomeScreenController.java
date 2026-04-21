package hr.tvz.carservicemanagementsystem.controller;

import hr.tvz.carservicemanagementsystem.dto.UserSignInDTO;
import hr.tvz.carservicemanagementsystem.exception.AuthenticationException;
import hr.tvz.carservicemanagementsystem.exception.ValidationException;
import hr.tvz.carservicemanagementsystem.service.RememberMeService;
import hr.tvz.carservicemanagementsystem.service.UserService;
import hr.tvz.carservicemanagementsystem.util.DialogUtils;
import hr.tvz.carservicemanagementsystem.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller for the welcome screen.
 * Handles user sign in, navigation to the sign up screen,
 * and restoring remembered email on startup.
 */
public class WelcomeScreenController {

    private static final Logger logger = LoggerFactory.getLogger(WelcomeScreenController.class);

    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private CheckBox rememberMeCheckBox;

    /**
     * Invoked automatically by FXMLLoader after all {@code @FXML} fields are injected.
     * Restores the remembered email and pre-selects the checkbox if a saved email exists.
     */
    public void initialize() {
        RememberMeService.getEmail().ifPresent(email -> {
            emailTextField.setText(email);
            rememberMeCheckBox.setSelected(true);
            passwordField.requestFocus();
        });
    }

    /**
     * Handles the sign in button action.
     * Validates credentials, manages the remember me state,
     * and navigates to the home screen on success.
     */
    public void signIn() {
        UserSignInDTO userSignInDTO = new UserSignInDTO(
                emailTextField.getText(),
                passwordField.getText()
        );

        logger.debug("Sign in attempt for email: {}", userSignInDTO.email());

        try {
            UserService.signIn(userSignInDTO);

            if (rememberMeCheckBox.isSelected()) {
                RememberMeService.saveEmail(userSignInDTO.email());
                logger.debug("Email saved for remember me: {}", userSignInDTO.email());
            } else {
                RememberMeService.clear();
                logger.debug("Remember me cleared for email: {}", userSignInDTO.email());
            }

            logger.info("User signed in successfully: {}", userSignInDTO.email());
            SceneManager.openHomeScreen();
        } catch (ValidationException | AuthenticationException e) {
            logger.warn("Sign in failed for email: {} — {}", userSignInDTO.email(), e.getMessage());
            DialogUtils.showError("Sign in failed", e.getMessage());
        }
    }

    /**
     * Navigates to the sign up screen.
     */
    public void signUp() {
        logger.debug("Navigating to sign up screen");
        SceneManager.openSignUpScreen();
    }
}
