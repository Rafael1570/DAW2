package org.iesalixar.daw2.rafael.daos;

import org.iesalixar.daw2.rafael.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAOImpl implements UserDAO {
    private static final Logger logger = Logger.getLogger(UserDAOImpl.class.getName());
    private static final String COLUMNS = "id, username, passwordHash, active, accountNonLocked, "
            + "lastPasswordChange, passwordExpiresAt, failedLoginAttempts, emailVerified, mustChangePassword";

    @Override
    public List<User> findAll() throws SQLException {
        logger.entering(getClass().getName(), "findAll");
        String sql = "SELECT " + COLUMNS + " FROM users ORDER BY username";
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<User> users = new ArrayList<>();
            while (resultSet.next()) users.add(mapUser(resultSet));
            logger.info(() -> "Usuarios cargados: " + users.size());
            return users;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "No se pudieron listar los usuarios.", e);
            throw e;
        } finally {
            logger.exiting(getClass().getName(), "findAll");
        }
    }

    @Override
    public User findById(long id) throws SQLException {
        logger.entering(getClass().getName(), "findById");
        String sql = "SELECT " + COLUMNS + " FROM users WHERE id = ?";
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? mapUser(resultSet) : null;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "No se pudo recuperar el usuario.", e);
            throw e;
        } finally {
            logger.exiting(getClass().getName(), "findById");
        }
    }

    @Override
    public void insert(User user) throws SQLException {
        logger.entering(getClass().getName(), "insert");
        String sql = "INSERT INTO users (username, passwordHash, active, accountNonLocked, "
                + "lastPasswordChange, passwordExpiresAt, failedLoginAttempts, emailVerified, mustChangePassword) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            bindUser(statement, user, false);
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) user.setId(keys.getLong(1));
            }
            logger.info("Usuario insertado.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "No se pudo insertar el usuario.", e);
            throw e;
        } finally {
            logger.exiting(getClass().getName(), "insert");
        }
    }

    @Override
    public void update(User user) throws SQLException {
        logger.entering(getClass().getName(), "update");
        String sql = "UPDATE users SET username = ?, passwordHash = ?, active = ?, accountNonLocked = ?, "
                + "lastPasswordChange = ?, passwordExpiresAt = ?, failedLoginAttempts = ?, "
                + "emailVerified = ?, mustChangePassword = ? WHERE id = ?";
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            bindUser(statement, user, true);
            statement.executeUpdate();
            logger.info("Usuario actualizado.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "No se pudo actualizar el usuario.", e);
            throw e;
        } finally {
            logger.exiting(getClass().getName(), "update");
        }
    }

    @Override
    public void delete(long id) throws SQLException {
        logger.entering(getClass().getName(), "delete");
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
            logger.info("Usuario eliminado.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "No se pudo eliminar el usuario.", e);
            throw e;
        } finally {
            logger.exiting(getClass().getName(), "delete");
        }
    }

    @Override
    public boolean usernameExists(String username, Long excludedId) throws SQLException {
        logger.entering(getClass().getName(), "usernameExists");
        String sql = excludedId == null
                ? "SELECT 1 FROM users WHERE LOWER(username) = LOWER(?) LIMIT 1"
                : "SELECT 1 FROM users WHERE LOWER(username) = LOWER(?) AND id <> ? LIMIT 1";
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            if (excludedId != null) statement.setLong(2, excludedId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "No se pudo comprobar la unicidad del username.", e);
            throw e;
        } finally {
            logger.exiting(getClass().getName(), "usernameExists");
        }
    }

    private void bindUser(PreparedStatement statement, User user, boolean includeId) throws SQLException {
        logger.entering(getClass().getName(), "bindUser");
        try {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPasswordHash());
            statement.setBoolean(3, Boolean.TRUE.equals(user.getActive()));
            statement.setBoolean(4, Boolean.TRUE.equals(user.getAccountNonLocked()));
            statement.setTimestamp(5, Timestamp.valueOf(user.getLastPasswordChange()));
            statement.setTimestamp(6, Timestamp.valueOf(user.getPasswordExpiresAt()));
            statement.setInt(7, user.getFailedLoginAttempts());
            statement.setBoolean(8, Boolean.TRUE.equals(user.getEmailVerified()));
            statement.setBoolean(9, Boolean.TRUE.equals(user.getMustChangePassword()));
            if (includeId) statement.setLong(10, user.getId());
        } finally {
            logger.exiting(getClass().getName(), "bindUser");
        }
    }

    private User mapUser(ResultSet resultSet) throws SQLException {
        logger.entering(getClass().getName(), "mapUser");
        try {
            Timestamp changed = resultSet.getTimestamp("lastPasswordChange");
            Timestamp expires = resultSet.getTimestamp("passwordExpiresAt");
            return new User(resultSet.getLong("id"), resultSet.getString("username"),
                    resultSet.getString("passwordHash"), resultSet.getBoolean("active"),
                    resultSet.getBoolean("accountNonLocked"), changed.toLocalDateTime(),
                    expires.toLocalDateTime(), resultSet.getInt("failedLoginAttempts"),
                    resultSet.getBoolean("emailVerified"), resultSet.getBoolean("mustChangePassword"));
        } finally {
            logger.exiting(getClass().getName(), "mapUser");
        }
    }
}
