package hr.tvz.carservicemanagementsystem.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * Utility class for hashing and verifying passwords using BCrypt.
 * Uses a cost factor of 12, providing a balance between security and performance.
 */
public class PasswordHasher {
    private PasswordHasher() {}

    /**
     * Hashes the given plain-text password using BCrypt with cost factor 12.
     *
     * @param password the plain-text password to hash
     * @return the BCrypt hashed password string
     */
    public static String hashPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    /**
     * Verifies a plain-text password against a BCrypt hash.
     *
     * @param password       the plain-text password to verify
     * @param hashedPassword the BCrypt hash to verify against
     * @return {@code true} if the password matches the hash, {@code false} otherwise
     */
    public static boolean checkPassword(String password, String hashedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);

        return result.verified;
    }
}
