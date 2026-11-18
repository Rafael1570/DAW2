package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.services;

import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.dtos.UserProfileFormDTO;
import org.springframework.web.multipart.MultipartFile;

public interface UserProfileService {
    UserProfileFormDTO getFormByEmail(String email);
    void updateProfile(UserProfileFormDTO profileDto,MultipartFile profileImageFile);
}
