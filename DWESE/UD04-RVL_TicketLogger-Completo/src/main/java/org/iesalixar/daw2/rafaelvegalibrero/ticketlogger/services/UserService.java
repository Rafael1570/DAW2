package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.services;

import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.dtos.*;
import java.util.List;

public interface UserService {
    UserPageDTO list(int page,int size,String sortField,String sortDir);
    UserUpdateDTO getForEdit(Long id);
    void create(UserCreateDTO dto);
    void update(UserUpdateDTO dto);
    void delete(Long id);
    UserDetailDTO getDetail(Long id);
    List<RoleDTO> roles();
}
