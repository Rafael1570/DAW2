package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.dtos;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.*;

/** DTO de lectura: no entrega el hash de contraseña a la vista. */
@Data
public class UserDTO {
    private Long id;
    private String email;
    private boolean active;
    private boolean accountNonLocked;
    private LocalDateTime lastPasswordChange;
    private LocalDateTime passwordExpiresAt;
    private int failedLoginAttempts;
    private boolean emailVerified;
    private boolean mustChangePassword;
    private Set<String> roles = new LinkedHashSet<>();
}
