package hr.tvz.carservicemanagementsystem.util;

import hr.tvz.carservicemanagementsystem.exception.RepositoryAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);

    private DatabaseConnection() {
    }

    private static Connection connection;

    public static Connection getInstance() {
        try {
            if (connection == null || connection.isClosed()) {
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
            throw new RepositoryAccessException(e);
        }

        return connection;
    }
}
