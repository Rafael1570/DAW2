package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.mappers;

import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.dtos.UserProfileFormDTO;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.entities.User;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.entities.UserProfile;

/** Conversiones del formulario único de alta/edición del perfil. */
public final class UserProfileMapper {
    private UserProfileMapper() {}
    public static UserProfileFormDTO toFormDto(User user,UserProfile profile) {
        if(user==null) return null;
        var dto=new UserProfileFormDTO();
        dto.setUserId(user.getId()); dto.setEmail(user.getEmail());
        if(profile!=null) {
            dto.setFirstName(profile.getFirstName()); dto.setLastName(profile.getLastName());
            dto.setPhoneNumber(profile.getPhoneNumber()); dto.setProfileImage(profile.getProfileImage());
            dto.setBio(profile.getBio()); dto.setLocale(profile.getLocale());
        }
        return dto;
    }
    public static UserProfile toNewEntity(UserProfileFormDTO dto,User user) {
        var profile=new UserProfile(); profile.setUser(user);
        copyToExistingEntity(dto,profile);
        return profile;
    }
    public static void copyToExistingEntity(UserProfileFormDTO dto,UserProfile profile) {
        profile.setFirstName(dto.getFirstName()); profile.setLastName(dto.getLastName());
        profile.setPhoneNumber(dto.getPhoneNumber()); profile.setProfileImage(dto.getProfileImage());
        profile.setBio(dto.getBio()); profile.setLocale(dto.getLocale());
    }
}
