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

    public void signUp() {
        UserSignUpDTO userSignUpDTO = new UserSignUpDTO(
                firstNameTextField.getText(),
                lastNameTextField.getText(),
                emailTextField.getText(),
                passwordField.getText(),
                confirmPasswordField.getText()
        );

        try {
            UserService.register(userSignUpDTO);

            DialogUtils.showInformation("Registration successful", "You have successfully registered. Enjoy the app!");

            SceneManager.openWelcomeScreen();
        } catch (ValidationException e) {
            logger.warn(e.getMessage());
            DialogUtils.showError("Registration failed", e.getMessage());
        }
    }
}
