package hr.tvz.carservicemanagementsystem.validation;

import hr.tvz.carservicemanagementsystem.dto.UserSignInDTO;
import hr.tvz.carservicemanagementsystem.dto.UserSignUpDTO;
import hr.tvz.carservicemanagementsystem.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for validating user-related input data.
 * Collects all validation errors before throwing, so the user
 * receives complete feedback in a single pass.
 */
public class UserValidation {

    private static final Logger logger = LoggerFactory.getLogger(UserValidation.class);

    private UserValidation() {}

    /**
     * Validates the sign up form data.
     * Checks first name, last name, email format, password strength
     * and that the confirmation password matches.
     *
     * @param dto the sign up data to validate
     * @throws ValidationException if one or more validation rules are violated
     */
    public static void validateUserSignUp(UserSignUpDTO dto) {
        logger.debug("Validating sign up data for email: {}", dto.email());
        List<String> errors = new ArrayList<>();

        validateFirstName(dto.firstName(), errors);
        validateLastName(dto.lastName(), errors);
        validateEmail(dto.email(), errors);
        validatePassword(dto.password(), dto.confirmPassword(), errors);

        if (!errors.isEmpty()) {
            logger.warn("Sign up validation failed with {} error(s): {}", errors.size(), errors);
            throw new ValidationException(String.join("\n", errors));
        }
    }

    /**
     * Validates the sign in form data.
     * Checks that email is in a valid format and password is not blank.
     *
     * @param userSignInDTO the sign in data to validate
     * @throws ValidationException if one or more validation rules are violated
     */
    public static void validateUserSignIn(UserSignInDTO userSignInDTO) {
        logger.debug("Validating sign in data for email: {}", userSignInDTO.email());
        List<String> errors = new ArrayList<>();

        validateEmail(userSignInDTO.email(), errors);

        if (userSignInDTO.password() == null || userSignInDTO.password().isBlank()) {
            errors.add("Password is required.");
        }

        if (!errors.isEmpty()) {
            logger.warn("Sign in validation failed with {} error(s): {}", errors.size(), errors);
            throw new ValidationException(String.join("\n", errors));
        }
    }

    private static void validateFirstName(String firstName, List<String> errors) {
        if (firstName == null || firstName.isBlank()) {
            errors.add("First name is required.");
        } else if (firstName.length() > 50) {
            errors.add("First name must not exceed 50 characters.");
        }
    }

    private static void validateLastName(String lastName, List<String> errors) {
        if (lastName == null || lastName.isBlank()) {
            errors.add("Last name is required.");
        } else if (lastName.length() > 50) {
            errors.add("Last name must not exceed 50 characters.");
        }
    }

    private static void validateEmail(String email, List<String> errors) {
        if (email == null || email.isBlank()) {
            errors.add("Email is required.");
        } else if (email.length() > 100) {
            errors.add("Email must not exceed 100 characters.");
        } else if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            errors.add("Email format is invalid.");
        }
    }

    private static void validatePassword(String password, String confirmPassword, List<String> errors) {
        if (password == null || password.isBlank()) {
            errors.add("Password is required.");
        } else {
            if (password.length() < 8) {
                errors.add("Password must be at least 8 characters.");
            }
            if (!password.matches(".*[A-Z].*")) {
                errors.add("Password must contain at least one uppercase letter.");
            }
            if (!password.matches(".*\\d.*")) {
                errors.add("Password must contain at least one number.");
            }
            if (confirmPassword == null || confirmPassword.isBlank()) {
                errors.add("Please confirm your password.");
            } else if (!password.equals(confirmPassword)) {
                errors.add("Passwords do not match.");
            }
        }
    }
}
