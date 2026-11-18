package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.daos;

import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.entities.UserProfile;
import jakarta.persistence.*;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly=true)
public class UserProfileDAOImpl implements UserProfileDAO {
    @PersistenceContext private EntityManager entityManager;
    public Optional<UserProfile> findByUserId(Long userId) {
        return Optional.ofNullable(entityManager.find(UserProfile.class,userId));
    }
    public boolean existsUserProfileByUserId(Long userId) {
        if(userId==null) return false;
        return entityManager.createQuery("select count(p) from UserProfile p where p.id=:id",Long.class)
            .setParameter("id",userId).getSingleResult()>0;
    }
    @Transactional public void insertUserProfile(UserProfile profile) {
        entityManager.persist(profile);entityManager.flush();
    }
    @Transactional public void updateUserProfile(UserProfile profile) {
        entityManager.merge(profile);entityManager.flush();
    }
}
