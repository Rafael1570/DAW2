package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.daos;

import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.entities.UserProfile;
import java.util.Optional;

public interface UserProfileDAO {
    Optional<UserProfile> findByUserId(Long userId);
    boolean existsUserProfileByUserId(Long userId);
    void insertUserProfile(UserProfile profile);
    void updateUserProfile(UserProfile profile);
}
