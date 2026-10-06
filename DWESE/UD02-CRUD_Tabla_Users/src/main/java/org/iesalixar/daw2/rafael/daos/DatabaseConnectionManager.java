package org.iesalixar.daw2.rafael.daos;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class DatabaseConnectionManager {
    private static final Logger logger = Logger.getLogger(DatabaseConnectionManager.class.getName());
    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

    private DatabaseConnectionManager() {}

    public static Connection getConnection() throws SQLException {
        logger.entering(DatabaseConnectionManager.class.getName(), "getConnection");
        try {
            String url = setting("DB_URL");
            String username = setting("DB_USER");
            String password = setting("DB_PASSWORD");
            if (url == null || username == null || password == null) {
                throw new SQLException("Faltan DB_URL, DB_USER o DB_PASSWORD en .env/entorno.");
            }
            logger.fine("Abriendo conexión con MariaDB.");
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "No se pudo abrir conexión con MariaDB.", e);
            throw e;
        } finally {
            logger.exiting(DatabaseConnectionManager.class.getName(), "getConnection");
        }
    }

    private static String setting(String name) {
        String value = dotenv.get(name);
        return value == null || value.isBlank() ? System.getenv(name) : value;
    }
}
