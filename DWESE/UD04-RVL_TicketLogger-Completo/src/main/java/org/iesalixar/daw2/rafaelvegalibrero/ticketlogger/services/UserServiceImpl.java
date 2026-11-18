package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.services;

import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.dtos.*;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.entities.*;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.daos.*;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.mappers.UserMapper;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.exceptions.*;
import java.time.LocalDateTime;
import java.util.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Casos de uso del CRUD: reglas, relaciones, transacciones y DTOs. */
@Service
@Transactional(readOnly=true)
public class UserServiceImpl implements UserService {
    private static final int PASSWORD_EXPIRY_DAYS=90;
    private final UserDAO users;
    private final RoleDAO roles;
    private final PasswordEncoder encoder;
    private final FileStorageService files;
    public UserServiceImpl(UserDAO users,RoleDAO roles,PasswordEncoder encoder,FileStorageService files) {
        this.users=users; this.roles=roles; this.encoder=encoder; this.files=files;
    }
    private User required(Long id) {
        return users.findById(id).orElseThrow(()->new ResourceNotFoundException("user","id",id));
    }
    private Set<Role> selected(Set<Long> ids) {
        if(ids==null || ids.isEmpty()) throw new ResourceNotFoundException("role","id",ids);
        var selected=new HashSet<>(roles.findAllByIds(ids));
        if(selected.size()!=ids.size()) throw new ResourceNotFoundException("role","id",ids);
        return selected;
    }
    public UserPageDTO list(int page,int size,String sortField,String sortDir) {
        int safeSize=Math.max(1,Math.min(size,100));
        long total=users.countUsers();
        int totalPages=(int)Math.min(Integer.MAX_VALUE,(total+safeSize-1)/safeSize);
        int lastPage=Math.min(Math.max(0,totalPages-1),Integer.MAX_VALUE/safeSize);
        int safePage=Math.max(0,Math.min(page,lastPage));
        String field=Set.of("id","email","active").contains(sortField)?sortField:"email";
        String direction="desc".equalsIgnoreCase(sortDir)?"desc":"asc";
        var content=UserMapper.toDTOList(users.listUsersPage(safePage,safeSize,field,direction));
        return new UserPageDTO(content,safePage,safeSize,total,totalPages,field,direction);
    }
    public UserUpdateDTO getForEdit(Long id) {return UserMapper.toUpdateDTO(required(id));}
    public UserDetailDTO getDetail(Long id) {return UserMapper.toDetailDTO(required(id));}
    public List<RoleDTO> roles() {return roles.listAllRoles().stream().map(UserMapper::role).toList();}
    @Transactional public void create(UserCreateDTO dto) {
        String email=dto.getEmail().trim().toLowerCase(Locale.ROOT);
        if(users.existsUserByEmail(email)) throw new DuplicateResourceException("user","email",email);
        var user=UserMapper.toEntity(dto,selected(dto.getRoleIds()));
        user.setPasswordHash(encoder.encode(dto.getPasswordHash()));
        LocalDateTime changed=dto.getLastPasswordChange()==null?LocalDateTime.now():dto.getLastPasswordChange();
        user.setLastPasswordChange(changed);
        user.setPasswordExpiresAt(changed.plusDays(PASSWORD_EXPIRY_DAYS));
        users.insertUser(user);
    }
    @Transactional public void update(UserUpdateDTO dto) {
        var user=required(dto.getId());
        String email=dto.getEmail().trim().toLowerCase(Locale.ROOT);
        if(users.existsUserByEmailAndNotId(email,user.getId())) throw new DuplicateResourceException("user","email",email);
        boolean changePassword=dto.getPasswordHash()!=null && !dto.getPasswordHash().isBlank();
        LocalDateTime changed=dto.getLastPasswordChange();
        if(changed==null) changed=changePassword?LocalDateTime.now():user.getLastPasswordChange();
        if(changed==null) changed=LocalDateTime.now();
        UserMapper.copyToEntity(dto,user,selected(dto.getRoleIds()));
        if(changePassword) user.setPasswordHash(encoder.encode(dto.getPasswordHash()));
        user.setLastPasswordChange(changed);
        user.setPasswordExpiresAt(changed.plusDays(PASSWORD_EXPIRY_DAYS));
        users.updateUser(user);
    }
    @Transactional public void delete(Long id) {
        var user=required(id);
        if(user.getProfile()!=null) files.removeAfterCommit(user.getProfile().getProfileImage());
        users.deleteUser(id);
    }
}
