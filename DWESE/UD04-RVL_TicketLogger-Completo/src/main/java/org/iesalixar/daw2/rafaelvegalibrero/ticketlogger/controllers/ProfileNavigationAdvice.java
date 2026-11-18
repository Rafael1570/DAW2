package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.controllers;

import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.services.UserProfileService;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/** Foto del usuario temporal de la práctica, disponible en todas las vistas. */
@ControllerAdvice
public class ProfileNavigationAdvice {
    private final UserProfileService profiles;
    private final String email;

    public ProfileNavigationAdvice(UserProfileService profiles,
            @Value("${app.profile-email:admin@app.local}") String email) {
        this.profiles=profiles;
        this.email=email;
    }

    @ModelAttribute("navProfileImage")
    public String avatar() {
        try {
            String image=profiles.getFormByEmail(email).getProfileImage();
            return image==null || image.isBlank() ? "/images/avatar.svg" : image;
        } catch(ResourceNotFoundException exception) {
            return "/images/avatar.svg";
        }
    }
}
