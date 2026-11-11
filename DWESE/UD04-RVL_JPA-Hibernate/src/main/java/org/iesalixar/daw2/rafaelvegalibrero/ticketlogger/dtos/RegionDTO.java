package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/** Datos de lectura; no contiene entidades JPA. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionDTO {
    private Long id;

    private String code;

    private String name;
}
