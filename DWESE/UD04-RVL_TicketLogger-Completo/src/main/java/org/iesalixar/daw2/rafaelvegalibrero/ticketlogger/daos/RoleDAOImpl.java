package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.daos;

import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.entities.Role;
import jakarta.persistence.*;
import java.util.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly=true)
public class RoleDAOImpl implements RoleDAO {
    @PersistenceContext private EntityManager entityManager;
    public List<Role> listAllRoles() {
        return entityManager.createQuery("select r from Role r order by r.name",Role.class).getResultList();
    }
    public List<Role> findAllByIds(Set<Long> ids) {
        if(ids==null || ids.isEmpty()) return List.of();
        return entityManager.createQuery("select r from Role r where r.id in :ids",Role.class)
            .setParameter("ids",ids).getResultList();
    }
    public Optional<Role> findById(Long id) {
        return entityManager.createQuery("select distinct r from Role r left join fetch r.users where r.id=:id",Role.class)
            .setParameter("id",id).getResultList().stream().findFirst();
    }
    public boolean existsRoleByName(String name) {
        return entityManager.createQuery("select count(r) from Role r where upper(r.name)=:name",Long.class)
            .setParameter("name",name.trim().toUpperCase(Locale.ROOT)).getSingleResult()>0;
    }
    public boolean existsRoleByNameAndNotId(String name,Long id) {
        return entityManager.createQuery("select count(r) from Role r where upper(r.name)=:name and r.id<>:id",Long.class)
            .setParameter("name",name.trim().toUpperCase(Locale.ROOT)).setParameter("id",id).getSingleResult()>0;
    }
    public boolean isAssigned(Long id) {
        return entityManager.createQuery("select count(u) from User u join u.roles r where r.id=:id",Long.class)
            .setParameter("id",id).getSingleResult()>0;
    }
    @Transactional public void insertRole(Role role) {entityManager.persist(role);entityManager.flush();}
    @Transactional public void updateRole(Role role) {entityManager.merge(role);entityManager.flush();}
    @Transactional public void deleteRole(Long id) {
        Role role=entityManager.find(Role.class,id);
        if(role!=null) {entityManager.remove(role);entityManager.flush();}
    }
}
