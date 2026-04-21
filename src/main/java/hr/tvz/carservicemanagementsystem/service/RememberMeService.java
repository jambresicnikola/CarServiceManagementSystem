package hr.tvz.carservicemanagementsystem.service;

import hr.tvz.carservicemanagementsystem.exception.RememberMeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Service for managing the "remember me" functionality.
 * Persists and retrieves the user's email address to/from a local file,
 * allowing the welcome screen to pre-fill the email field on next launch.
 */
public class RememberMeService {
    private RememberMeService() {}

    private static final Logger logger = LoggerFactory.getLogger(RememberMeService.class);

    private static final Path FILE_PATH = Path.of(
            System.getProperty("user.home"), ".autoservis", "saved_email.txt"
    );

    /**
     * Saves the given email address to the local remember me file.
     * Creates the necessary directories if they do not exist.
     *
     * @param email the email address to persist
     * @throws RememberMeException if the file cannot be written
     */
    public static void saveEmail(String email) {
        logger.debug("Saving email to remember me file: {}", email);
        try {
            Files.createDirectories(FILE_PATH.getParent());
            Files.writeString(FILE_PATH, email);
            logger.info("Email saved for remember me: {}", email);
        } catch (IOException e) {
            throw new RememberMeException("Failed to save email.", e);
        }
    }

    /**
     * Deletes the remember me file if it exists,
     * effectively clearing the saved email.
     *
     * @throws RememberMeException if the file cannot be deleted
     */
    public static void clear() {
        logger.debug("Clearing remember me file");
        try {
            Files.deleteIfExists(FILE_PATH);
            logger.info("Remember me file cleared");
        } catch (IOException e) {
            throw new RememberMeException("Failed to clear saved email.", e);
        }
    }

    /**
     * Reads the saved email address from the local remember me file.
     *
     * @return an {@link Optional} containing the saved email,
     *         or empty if no email is saved or the file does not exist
     * @throws RememberMeException if the file cannot be read
     */
    public static Optional<String> getEmail() {
        logger.debug("Reading email from remember me file");
        try {
            if (!Files.exists(FILE_PATH)) return Optional.empty();

            String email = Files.readString(FILE_PATH).trim();

            return email.isBlank() ? Optional.empty() : Optional.of(email);
        } catch (IOException e) {
            throw new RememberMeException("Failed to read saved email.", e);
        }
    }
}
