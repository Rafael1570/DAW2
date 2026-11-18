package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.services;

import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.dtos.*;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.entities.Role;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.daos.RoleDAO;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.exceptions.*;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.mappers.*;
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class RoleServiceImpl implements RoleService {
    private final RoleDAO roleDAO;
    public RoleServiceImpl(RoleDAO roleDAO) {this.roleDAO=roleDAO;}
    private Role required(Long id) {return roleDAO.findById(id).orElseThrow(()->new ResourceNotFoundException("role","id",id));}
    private void unique(String name,Long id) {
        boolean duplicate=id==null?roleDAO.existsRoleByName(name):roleDAO.existsRoleByNameAndNotId(name,id);
        if(duplicate) throw new DuplicateResourceException("role","name",name);
    }
    public List<RoleDTO> list() {return roleDAO.listAllRoles().stream().map(UserMapper::role).toList();}
    public RoleFormDTO getForEdit(Long id) {return RoleMapper.toForm(required(id));}
    public RoleDetailDTO getDetail(Long id) {
        var role=required(id);
        return new RoleDetailDTO(UserMapper.role(role),role.getUsers().stream()
            .sorted(Comparator.comparing(u->u.getEmail())).map(UserMapper::toDTO).toList());
    }
    @Transactional public void create(RoleFormDTO dto) {
        unique(dto.getName().trim(),null);var role=new Role();RoleMapper.update(dto,role);roleDAO.insertRole(role);
    }
    @Transactional public void update(Long id,RoleFormDTO dto) {
        var role=required(id);unique(dto.getName().trim(),id);RoleMapper.update(dto,role);roleDAO.updateRole(role);
    }
    @Transactional public void delete(Long id) {
        var role=required(id);
        if(roleDAO.isAssigned(id)) throw new IllegalStateException("Role has assigned users");
        roleDAO.deleteRole(id);
    }
}
