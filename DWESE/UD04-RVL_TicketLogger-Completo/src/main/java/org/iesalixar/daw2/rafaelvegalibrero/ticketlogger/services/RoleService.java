package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.services;

import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.dtos.*;
import java.util.List;

public interface RoleService {
    List<RoleDTO> list();
    RoleFormDTO getForEdit(Long id);
    RoleDetailDTO getDetail(Long id);
    void create(RoleFormDTO dto);
    void update(Long id, RoleFormDTO dto);
    void delete(Long id);
}
