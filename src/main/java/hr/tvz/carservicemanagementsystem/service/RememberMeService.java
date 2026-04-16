package hr.tvz.carservicemanagementsystem.service;

import hr.tvz.carservicemanagementsystem.exception.RememberMeException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class RememberMeService {
    private RememberMeService() {}

    private static final Path FILE_PATH = Path.of(
            System.getProperty("user.home"), ".autoservis", "saved_email.txt"
    );

    public static void saveEmail(String email) {
        try {
            Files.createDirectories(FILE_PATH.getParent());
            Files.writeString(FILE_PATH, email);
        } catch (IOException e) {
            throw new RememberMeException("Failed to save email.", e);
        }
    }

    public static void clear() {
        try {
            Files.deleteIfExists(FILE_PATH);
        } catch (IOException e) {
            throw new RememberMeException("Failed to clear saved email.", e);
        }
    }

    public static Optional<String> getEmail() {
        try {
            if (!Files.exists(FILE_PATH)) return Optional.empty();

            String email = Files.readString(FILE_PATH).trim();

            return email.isBlank() ? Optional.empty() : Optional.of(email);
        } catch (IOException e) {
            throw new RememberMeException("Failed to read saved email.", e);
        }
    }
}
