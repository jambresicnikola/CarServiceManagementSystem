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

public class WelcomeScreenController {
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private CheckBox rememberMeCheckBox;

    public void initialize() {
        RememberMeService.getEmail().ifPresent(email -> {
            emailTextField.setText(email);
            rememberMeCheckBox.setSelected(true);
        });
    }

    public void signIn() {
        UserSignInDTO userSignInDTO = new UserSignInDTO(
                emailTextField.getText(),
                passwordField.getText()
        );

        try {
            UserService.signIn(userSignInDTO);

            if (rememberMeCheckBox.isSelected()) {
                RememberMeService.saveEmail(userSignInDTO.email());
            } else {
                RememberMeService.clear();
            }

            SceneManager.openHomeScreen();
        } catch (ValidationException | AuthenticationException e) {
            DialogUtils.showError("Sign in failed", e.getMessage());
        }
    }

    public void signUp() {
        SceneManager.openSignUpScreen();
    }
}
