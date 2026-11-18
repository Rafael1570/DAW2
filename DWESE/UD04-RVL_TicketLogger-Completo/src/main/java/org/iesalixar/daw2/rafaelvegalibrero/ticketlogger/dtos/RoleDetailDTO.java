package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.dtos;

import java.util.List;
public record RoleDetailDTO(RoleDTO role, List<UserDTO> users) {}
