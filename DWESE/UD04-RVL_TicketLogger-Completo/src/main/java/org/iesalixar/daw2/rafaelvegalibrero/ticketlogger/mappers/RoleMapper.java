package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.mappers;

import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.dtos.*;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.entities.Role;

public final class RoleMapper {
    private RoleMapper() {}
    public static RoleFormDTO toForm(Role role) {
        var dto=new RoleFormDTO();
        dto.setId(role.getId()); dto.setName(role.getName());
        dto.setDisplayName(role.getDisplayName()); dto.setDescription(role.getDescription());
        return dto;
    }
    public static void update(RoleFormDTO dto, Role role) {
        role.setName(dto.getName().trim());
        role.setDisplayName(dto.getDisplayName().trim());
        role.setDescription(dto.getDescription());
    }
}
