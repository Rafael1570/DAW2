package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;

/** Datos de formulario; no contiene entidades JPA. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionCreateDTO {
    @Null(message = "{msg.common.id.mustBeNull}")
    private Long id;

    @NotBlank(message = "{msg.region.code.notEmpty}")
    @Size(max = 2, message = "{msg.region.code.size}")
    private String code;

    @NotBlank(message = "{msg.region.name.notEmpty}")
    @Size(max = 100, message = "{msg.region.name.size}")
    private String name;
}
