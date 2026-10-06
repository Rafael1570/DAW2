package org.iesalixar.daw2.rafael.daos;

import org.iesalixar.daw2.rafael.entities.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    List<User> findAll() throws SQLException;
    User findById(long id) throws SQLException;
    void insert(User user) throws SQLException;
    void update(User user) throws SQLException;
    void delete(long id) throws SQLException;
    boolean usernameExists(String username, Long excludedId) throws SQLException;
}
