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

public class UserService {
    private UserService() {}

    public static void register(UserSignUpDTO userSignUpDTO) {
        UserValidation.validateUserSignUp(userSignUpDTO);

        if (UserDatabaseRepository.getInstance().findUserByEmail(userSignUpDTO.email()).isPresent()) {
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
    }

    public static void signIn(UserSignInDTO userSignInDTO) {
        UserValidation.validateUserSignIn(userSignInDTO);

        User user = UserDatabaseRepository.getInstance()
                .findUserByEmail(userSignInDTO.email())
                .orElseThrow(() -> new AuthenticationException("Invalid email or password."));

        if (!PasswordHasher.checkPassword(userSignInDTO.password(), user.getPassword())) {
            throw new AuthenticationException("Invalid email or password.");
        }

        Session.setLoggedUser(user);
    }
}
