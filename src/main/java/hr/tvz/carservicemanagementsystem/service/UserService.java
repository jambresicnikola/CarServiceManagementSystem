package hr.tvz.carservicemanagementsystem.service;

import hr.tvz.carservicemanagementsystem.database.UserDatabaseRepository;
import hr.tvz.carservicemanagementsystem.dto.UserSignInDTO;
import hr.tvz.carservicemanagementsystem.dto.UserSignUpDTO;
import hr.tvz.carservicemanagementsystem.exception.AuthenticationException;
import hr.tvz.carservicemanagementsystem.exception.ValidationException;
import hr.tvz.carservicemanagementsystem.model.Role;
import hr.tvz.carservicemanagementsystem.model.User;
import hr.tvz.carservicemanagementsystem.util.PasswordHasher;
import hr.tvz.carservicemanagementsystem.util.Session;
import hr.tvz.carservicemanagementsystem.validation.UserValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service responsible for user-related business logic.
 */
public class UserService {
    private UserService() {}

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * Registers a new user with the MECHANIC role.
     * Validates the input, checks for duplicate email,
     * hashes the password and persists the new user.
     *
     * @param userSignUpDTO the registration data
     * @throws ValidationException if input is invalid or email is already in use
     */
    public static void register(UserSignUpDTO userSignUpDTO) {
        logger.debug("Registering new user with email: {}", userSignUpDTO.email());

        UserValidation.validateUserSignUp(userSignUpDTO);

        if (UserDatabaseRepository.getInstance().findUserByEmail(userSignUpDTO.email()).isPresent()) {
            logger.warn("Registration failed — email already in use: {}", userSignUpDTO.email());
            throw new ValidationException("Email already in use.");
        }

        String hashedPassword = PasswordHasher.hashPassword(userSignUpDTO.password());

        UserDatabaseRepository.getInstance().save(
                new User.Builder()
                        .firstName(userSignUpDTO.firstName())
                        .lastName(userSignUpDTO.lastName())
                        .email(userSignUpDTO.email())
                        .password(hashedPassword)
                        .role(Role.MECHANIC)
                        .build()
        );

        logger.info("User registered successfully: {}", userSignUpDTO.email());
    }

    /**
     * Authenticates a user with the provided credentials.
     * Validates input format, verifies email existence and password correctness,
     * and sets the authenticated user in the current {@link Session}.
     *
     * @param userSignInDTO the sign in credentials
     * @throws ValidationException     if input format is invalid
     * @throws AuthenticationException if email does not exist or password is incorrect
     */
    public static void signIn(UserSignInDTO userSignInDTO) {
        logger.debug("Sign in attempt for email: {}", userSignInDTO.email());

        UserValidation.validateUserSignIn(userSignInDTO);

        User user = UserDatabaseRepository.getInstance()
                .findUserByEmail(userSignInDTO.email())
                .orElseThrow(() -> {
                    logger.warn("Sign in failed — email not found: {}", userSignInDTO.email());
                    return new AuthenticationException("Invalid email or password.");
                });

        if (!PasswordHasher.checkPassword(userSignInDTO.password(), user.getPassword())) {
            logger.warn("Sign in failed — incorrect password for email: {}", userSignInDTO.email());
            throw new AuthenticationException("Invalid email or password.");
        }

        Session.setLoggedUser(user);

        logger.info("User signed in successfully: {}", userSignInDTO.email());
    }
}
