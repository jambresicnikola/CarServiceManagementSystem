package hr.tvz.carservicemanagementsystem.database;

import hr.tvz.carservicemanagementsystem.exception.RepositoryAccessException;
import hr.tvz.carservicemanagementsystem.model.Role;
import hr.tvz.carservicemanagementsystem.model.User;
import hr.tvz.carservicemanagementsystem.util.DatabaseConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository for performing database operations on {@link User} entities.
 * Implemented as a Singleton — use {@link #getInstance()} to obtain the instance.
 */
public class UserDatabaseRepository extends AbstractRepository<User> {

    private UserDatabaseRepository() {}

    private static final Logger logger = LoggerFactory.getLogger(UserDatabaseRepository.class);

    private static final String FIND_USER_BY_ID_QUERY =
            "SELECT id, first_name, last_name, email, password, user_role FROM user WHERE id = ?";
    private static final String FIND_ALL_USERS_QUERY =
            "SELECT id, first_name, last_name, email, password, user_role FROM user";
    private static final String SAVE_USER_QUERY =
            "INSERT INTO user (first_name, last_name, email, password, user_role) VALUES (?, ?, ?, ?, ?)";
    private static final String FIND_USER_BY_EMAIL_QUERY =
            "SELECT id, first_name, last_name, email, password, user_role FROM user WHERE email = ?";

    private static UserDatabaseRepository instance;

    /**
     * Returns the Singleton instance of this repository.
     *
     * @return the single {@link UserDatabaseRepository} instance
     */
    public static UserDatabaseRepository getInstance() {
        if (instance == null) {
            instance = new UserDatabaseRepository();
        }

        return instance;
    }

    /**
     * Finds a user by their unique identifier.
     *
     * @param id the user's ID
     * @return the {@link User}, or {@code null} if not found
     * @throws RepositoryAccessException if a database error occurs
     */
    @Override
    User findById(Long id) {
        logger.debug("Finding user by id: {}", id);
        Connection conn = DatabaseConnection.getInstance();

        try (PreparedStatement stmt = conn.prepareStatement(FIND_USER_BY_ID_QUERY)) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryAccessException("Failed to find user by id: " + id, e);
        }

        return null;
    }

    /**
     * Retrieves all users from the database.
     *
     * @return list of all {@link User} entities, empty list if none exist
     * @throws RepositoryAccessException if a database error occurs
     */
    @Override
    List<User> findAll() {
        logger.debug("Fetching all users");
        List<User> users = new ArrayList<>();

        Connection conn = DatabaseConnection.getInstance();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(FIND_ALL_USERS_QUERY)) {

            while (rs.next()) {
                users.add(extractUserFromResultSet(rs));
            }

            logger.debug("Fetched {} users", users.size());
        } catch (SQLException e) {
            throw new RepositoryAccessException("Failed to fetch all users", e);
        }

        return users;
    }

    /**
     * Persists a new user to the database.
     *
     * @param entity the {@link User} to save
     * @throws RepositoryAccessException if a database error occurs
     */
    @Override
    public void save(User entity) {
        logger.debug("Saving user with email: {}", entity.getEmail());
        Connection conn = DatabaseConnection.getInstance();

        try (PreparedStatement stmt = conn.prepareStatement(SAVE_USER_QUERY)) {
            stmt.setString(1, entity.getFirstName());
            stmt.setString(2, entity.getLastName());
            stmt.setString(3, entity.getEmail());
            stmt.setString(4, entity.getPassword());
            stmt.setString(5, entity.getRole().toString());

            stmt.executeUpdate();
            logger.info("User saved successfully: {}", entity.getEmail());
        } catch (SQLException e) {
            throw new RepositoryAccessException("Failed to save user: " + entity.getEmail(), e);
        }
    }

    @Override
    void delete(User entity) {
        //delete
    }

    @Override
    void update(User entity) {
        //update
    }

    /**
     * Finds a user by their email address.
     *
     * @param email the email to search for
     * @return an {@link Optional} containing the user if found, empty otherwise
     * @throws RepositoryAccessException if a database error occurs
     */
    public Optional<User> findUserByEmail(String email) {
        logger.debug("Finding user by email: {}", email);
        Connection conn = DatabaseConnection.getInstance();

        try (PreparedStatement stmt = conn.prepareStatement(FIND_USER_BY_EMAIL_QUERY)) {
            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(extractUserFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            throw new RepositoryAccessException("Failed to find user by email: " + email, e);
        }

        return Optional.empty();
    }

    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        return new User.Builder()
                .id(rs.getLong("id"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .role(Role.valueOf(rs.getString("user_role")))
                .build();
    }
}