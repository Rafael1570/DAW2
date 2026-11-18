package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.dtos;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** Resultado de paginación propio, independiente de Spring Data. */
@Getter
@AllArgsConstructor
public class UserPageDTO {
    private final List<UserDTO> users;
    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;
    private final String sortField;
    private final String sortDir;
}
