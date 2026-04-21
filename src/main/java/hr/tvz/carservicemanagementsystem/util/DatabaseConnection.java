package hr.tvz.carservicemanagementsystem.util;

import hr.tvz.carservicemanagementsystem.exception.RepositoryAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Singleton utility class for managing the application's database connection.
 * Reads connection parameters from {@code database.properties} on the classpath
 * and reuses a single {@link Connection} instance throughout the application lifecycle.
 */
public class DatabaseConnection {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);

    private DatabaseConnection() {
    }

    private static Connection connection;

    /**
     * Returns the active database connection, creating a new one if necessary.
     * A new connection is established if none exists or if the existing one is closed.
     *
     * @return an active {@link Connection} instance
     * @throws RepositoryAccessException if the connection cannot be established
     */
    public static Connection getInstance() {
        try {
            if (connection == null || connection.isClosed()) {
                logger.debug("Establishing new database connection");
                Properties properties = new Properties();

                try (var reader = DatabaseConnection.class
                        .getClassLoader()
                        .getResourceAsStream("database.properties")) {
                    properties.load(reader);
                }

                connection = DriverManager.getConnection(
                        properties.getProperty("url"),
                        properties.getProperty("username"),
                        properties.getProperty("password")
                );
                logger.info("Connection to database is successful.");
            }
        } catch (SQLException | IOException e) {
            throw new RepositoryAccessException("Failed to establish database connection", e);
        }

        return connection;
    }
}
