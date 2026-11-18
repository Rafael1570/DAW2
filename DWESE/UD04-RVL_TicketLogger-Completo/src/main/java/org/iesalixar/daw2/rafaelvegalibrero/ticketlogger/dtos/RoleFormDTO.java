package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RoleFormDTO {
    private Long id;
    @NotBlank(message="{msg.required}")
    @Pattern(regexp="ROLE_[A-Z_]+", message="{msg.roleNamePattern}")
    @Size(max=50)
    private String name;
    @NotBlank(message="{msg.required}") @Size(max=100)
    private String displayName;
    @Size(max=255)
    private String description;
}
