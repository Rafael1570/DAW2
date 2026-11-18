package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.mappers;

import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.dtos.*;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.entities.*;
import java.util.*;
import java.util.stream.Collectors;

public final class UserMapper {
    private UserMapper() {}
    public static RoleDTO role(Role r) { return new RoleDTO(r.getId(),r.getName(),r.getDisplayName(),r.getDescription()); }
    public static Set<String> roles(User user) {
        return user.getRoles().stream().map(Role::getName).sorted()
            .collect(Collectors.toCollection(LinkedHashSet::new));
    }
    public static UserDTO toDTO(User user) {
        if(user==null) return null;
        var dto=new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setActive(user.isActive());
        dto.setAccountNonLocked(user.isAccountNonLocked());
        dto.setLastPasswordChange(user.getLastPasswordChange());
        dto.setPasswordExpiresAt(user.getPasswordExpiresAt());
        dto.setFailedLoginAttempts(user.getFailedLoginAttempts());
        dto.setEmailVerified(user.isEmailVerified());
        dto.setMustChangePassword(user.isMustChangePassword());
        dto.setRoles(roles(user));
        return dto;
    }
    public static List<UserDTO> toDTOList(List<User> users) {
        return users==null ? List.of() : users.stream().map(UserMapper::toDTO).toList();
    }
    public static UserDetailDTO toDetailDTO(User user) {
        if(user==null) return null;
        var dto=new UserDetailDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setActive(user.isActive());
        dto.setAccountNonLocked(user.isAccountNonLocked());
        dto.setLastPasswordChange(user.getLastPasswordChange());
        dto.setPasswordExpiresAt(user.getPasswordExpiresAt());
        dto.setFailedLoginAttempts(user.getFailedLoginAttempts());
        dto.setEmailVerified(user.isEmailVerified());
        dto.setMustChangePassword(user.isMustChangePassword());
        dto.setRoles(roles(user));
        var p=user.getProfile();
        if(p!=null) {
            dto.setFirstName(p.getFirstName()); dto.setLastName(p.getLastName());
            dto.setPhoneNumber(p.getPhoneNumber()); dto.setProfileImage(p.getProfileImage());
            dto.setBio(p.getBio()); dto.setLocale(p.getLocale());
        }
        return dto;
    }
    public static UserUpdateDTO toUpdateDTO(User user) {
        if(user==null) return null;
        var dto=new UserUpdateDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setActive(user.isActive());
        dto.setAccountNonLocked(user.isAccountNonLocked());
        dto.setLastPasswordChange(user.getLastPasswordChange());
        dto.setPasswordExpiresAt(user.getPasswordExpiresAt());
        dto.setFailedLoginAttempts(user.getFailedLoginAttempts());
        dto.setEmailVerified(user.isEmailVerified());
        dto.setMustChangePassword(user.isMustChangePassword());
        dto.setRoleIds(user.getRoles().stream().map(Role::getId).collect(Collectors.toSet()));
        // El hash almacenado no se introduce en el campo de contraseña.
        return dto;
    }
    public static User toEntity(UserCreateDTO dto,Set<Role> roles) {
        var user=new User();
        user.setEmail(dto.getEmail().trim().toLowerCase(Locale.ROOT));
        user.setActive(dto.isActive()); user.setAccountNonLocked(dto.isAccountNonLocked());
        user.setLastPasswordChange(dto.getLastPasswordChange());
        user.setPasswordExpiresAt(dto.getPasswordExpiresAt());
        user.setFailedLoginAttempts(dto.getFailedLoginAttempts());
        user.setEmailVerified(dto.isEmailVerified()); user.setMustChangePassword(dto.isMustChangePassword());
        user.setRoles(roles);
        return user;
    }
    public static void copyToEntity(UserUpdateDTO dto,User user,Set<Role> roles) {
        user.setEmail(dto.getEmail().trim().toLowerCase(Locale.ROOT));
        user.setActive(dto.isActive()); user.setAccountNonLocked(dto.isAccountNonLocked());
        user.setLastPasswordChange(dto.getLastPasswordChange());
        user.setFailedLoginAttempts(dto.getFailedLoginAttempts());
        user.setEmailVerified(dto.isEmailVerified()); user.setMustChangePassword(dto.isMustChangePassword());
        user.setRoles(roles);
    }
}
