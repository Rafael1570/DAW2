package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;

/** Datos de formulario; no contiene entidades JPA. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProvinceCreateDTO {
    @Null(message = "{msg.common.id.mustBeNull}")
    private Long id;

    @NotBlank(message = "{msg.province.code.notEmpty}")
    @Size(max = 10, message = "{msg.province.code.size}")
    private String code;

    @NotBlank(message = "{msg.province.name.notEmpty}")
    @Size(max = 100, message = "{msg.province.name.size}")
    private String name;

    @NotNull(message = "{msg.province.region.notNull}")
    @Positive(message = "{msg.province.region.notNull}")
    private Long regionId;
}
