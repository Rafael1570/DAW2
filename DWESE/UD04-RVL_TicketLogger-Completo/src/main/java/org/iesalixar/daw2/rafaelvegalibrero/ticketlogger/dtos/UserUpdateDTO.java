package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.dtos;

import lombok.Data;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import java.util.*;

@Data
public class UserUpdateDTO {
    @NotNull @Positive private Long id;
    @NotBlank(message="{msg.required}") @Email @Size(max=100)
    private String email;
    @Size(max=72)
    private String passwordHash;
    private boolean active = true;
    private boolean accountNonLocked = true;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime lastPasswordChange;
    private LocalDateTime passwordExpiresAt;
    @Min(value=0, message="{msg.user.failedLoginAttempts.min}")
    private int failedLoginAttempts;
    private boolean emailVerified;
    private boolean mustChangePassword;
    @NotEmpty(message="{msg.user.roles.notempty}")
    private Set<@NotNull @Positive Long> roleIds = new HashSet<>();
}
