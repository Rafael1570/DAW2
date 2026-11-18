package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.daos;

import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.entities.Role;
import java.util.*;

public interface RoleDAO {
    List<Role> listAllRoles();
    List<Role> findAllByIds(Set<Long> ids);
    Optional<Role> findById(Long id);
    boolean existsRoleByName(String name);
    boolean existsRoleByNameAndNotId(String name,Long id);
    boolean isAssigned(Long id);
    void insertRole(Role role);
    void updateRole(Role role);
    void deleteRole(Long id);
}
