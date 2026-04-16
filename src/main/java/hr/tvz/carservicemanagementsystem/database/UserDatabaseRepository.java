package hr.tvz.carservicemanagementsystem.database;

import hr.tvz.carservicemanagementsystem.exception.RepositoryAccessException;
import hr.tvz.carservicemanagementsystem.model.Role;
import hr.tvz.carservicemanagementsystem.model.User;
import hr.tvz.carservicemanagementsystem.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDatabaseRepository extends AbstractRepository<User> {
    private static final String FIND_USER_BY_ID_QUERY =
            "SELECT id, first_name, last_name, email, password, user_role FROM user WHERE id = ?";
    private static final String FIND_ALL_USERS_QUERY =
            "SELECT id, first_name, last_name, email, password, user_role FROM user";
    private static final String SAVE_USER_QUERY =
            "INSERT INTO user (first_name, last_name, email, password, user_role) VALUES (?, ?, ?, ?, ?)";
    private static final String FIND_USER_BY_EMAIL_QUERY =
            "SELECT id, first_name, last_name, email, password, user_role FROM user WHERE email = ?";

    private static UserDatabaseRepository instance;

    public static UserDatabaseRepository getInstance() {
        if (instance == null) {
            instance = new UserDatabaseRepository();
        }

        return instance;
    }

    @Override
    User findById(Long id) {
        Connection conn = DatabaseConnection.getInstance();

        try (PreparedStatement stmt = conn.prepareStatement(FIND_USER_BY_ID_QUERY)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }
        } catch (SQLException e) {
            throw new RepositoryAccessException(e);
        }

        return null;
    }

    @Override
    List<User> findAll() {
        List<User> users = new ArrayList<>();

        Connection conn = DatabaseConnection.getInstance();

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(FIND_ALL_USERS_QUERY);

            while (rs.next()) {
                users.add(extractUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryAccessException(e);
        }

        return users;
    }

    @Override
    public void save(User entity) {
        Connection conn = DatabaseConnection.getInstance();

        try (PreparedStatement stmt = conn.prepareStatement(SAVE_USER_QUERY)) {
            stmt.setString(1, entity.getFirstName());
            stmt.setString(2, entity.getLastName());
            stmt.setString(3, entity.getEmail());
            stmt.setString(4, entity.getPassword());
            stmt.setString(5, entity.getRole().toString());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryAccessException(e);
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

    public Optional<User> findUserByEmail(String email) {
        Connection conn = DatabaseConnection.getInstance();

        try (PreparedStatement stmt = conn.prepareStatement(FIND_USER_BY_EMAIL_QUERY)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(extractUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryAccessException(e);
        }

        return Optional.empty();
    }

    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        Long id = rs.getLong(1);
        String firstName = rs.getString(2);
        String lastName = rs.getString(3);
        String email = rs.getString(4);
        String password = rs.getString(5);
        Role role = Role.valueOf(rs.getString(6));

        return new User.Builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .role(role)
                .build();
    }
}
