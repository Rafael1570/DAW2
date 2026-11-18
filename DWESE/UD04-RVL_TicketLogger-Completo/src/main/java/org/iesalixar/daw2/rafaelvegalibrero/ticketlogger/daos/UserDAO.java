package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.daos;

import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.entities.User;
import java.util.*;

public interface UserDAO {
    long countUsers();
    List<User> listUsersPage(int page,int size,String sortField,String sortDir);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    boolean existsUserByEmail(String email);
    boolean existsUserByEmailAndNotId(String email,Long id);
    void insertUser(User user);
    void updateUser(User user);
    void deleteUser(Long id);
}
