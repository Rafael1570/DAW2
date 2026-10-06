package org.iesalixar.daw2.rafael.daos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public final class DataInitializer {
    private static final Logger logger = Logger.getLogger(DataInitializer.class.getName());

    private DataInitializer() {}

    public static void loadDataFromSQL(InputStream sqlFileStream) throws SQLException, IOException {
        logger.entering(DataInitializer.class.getName(), "loadDataFromSQL");
        if (sqlFileStream == null) {
            throw new IOException("No se ha encontrado el archivo data.sql.");
        }

        String sql;
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(sqlFileStream, StandardCharsets.UTF_8))) {
            sql = reader.lines().collect(Collectors.joining("\n"));
        }

        try (Connection connection = DatabaseConnectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            for (String sqlStatement : sql.split(";")) {
                String command = sqlStatement.trim();
                if (!command.isEmpty()) {
                    statement.execute(command);
                }
            }
            logger.info("Esquema y datos iniciales cargados.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "No se pudo ejecutar data.sql.", e);
            throw e;
        } finally {
            logger.exiting(DataInitializer.class.getName(), "loadDataFromSQL");
        }
    }
}
