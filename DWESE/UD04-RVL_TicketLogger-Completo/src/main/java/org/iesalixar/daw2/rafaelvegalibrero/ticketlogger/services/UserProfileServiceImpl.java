package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.services;

import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.dtos.UserProfileFormDTO;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.entities.UserProfile;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.daos.*;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.exceptions.*;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.mappers.UserProfileMapper;
import java.io.IOException;
import java.util.Locale;
import java.util.Set;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.*;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly=true)
public class UserProfileServiceImpl implements UserProfileService {
    private static final long MAX_IMAGE_SIZE_BYTES=2*1024*1024;
    private final UserDAO users;
    private final UserProfileDAO profiles;
    private final FileStorageService files;
    public UserProfileServiceImpl(UserDAO users,UserProfileDAO profiles,FileStorageService files) {
        this.users=users; this.profiles=profiles; this.files=files;
    }
    public UserProfileFormDTO getFormByEmail(String email) {
        var user=users.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("user","email",email));
        return UserProfileMapper.toFormDto(user,profiles.findByUserId(user.getId()).orElse(null));
    }
    @Transactional public void updateProfile(UserProfileFormDTO dto,MultipartFile image) {
        var user=users.findById(dto.getUserId()).orElseThrow(()->new ResourceNotFoundException("user","id",dto.getUserId()));
        UserProfile profile=profiles.findByUserId(user.getId()).orElse(null);
        String previous=profile==null?null:profile.getProfileImage();
        dto.setProfileImage(previous);
        if(image!=null && !image.isEmpty()) {
            validateProfileImage(image);
            String saved=files.saveFile(image);
            if(saved==null) throw new InvalidFileException("userProfile","profileImageFile",image.getOriginalFilename(),"Could not save image");
            dto.setProfileImage(saved);
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override public void afterCompletion(int status) {
                    files.deleteFile(status==STATUS_COMMITTED ? previous : saved);
                }
            });
        }
        boolean isNew=profile==null;
        if(isNew) profile=UserProfileMapper.toNewEntity(dto,user);
        else UserProfileMapper.copyToExistingEntity(dto,profile);
        user.setProfile(profile);
        if(isNew) profiles.insertUserProfile(profile);
        else profiles.updateUserProfile(profile);
    }
    private void validateProfileImage(MultipartFile image) {
        if(image.getSize()>MAX_IMAGE_SIZE_BYTES) throw new InvalidFileException("userProfile","profileImageFile",image.getSize(),"Image exceeds size limit");
        String type=image.getContentType();
        String name=java.util.Objects.toString(image.getOriginalFilename(),"").toLowerCase(Locale.ROOT);
        int dot=name.lastIndexOf('.');
        if(type==null || !type.startsWith("image/") || dot<0 || !Set.of("png","jpg","jpeg","gif").contains(name.substring(dot+1)))
            throw new InvalidFileException("userProfile","profileImageFile",image.getContentType(),"Invalid image type or contents");
        try(var input=ImageIO.createImageInputStream(image.getInputStream())) {
            if(input==null) throw new InvalidFileException("userProfile","profileImageFile",image.getContentType(),"Invalid image type or contents");
            var readers=ImageIO.getImageReaders(input);
            if(!readers.hasNext()) throw new InvalidFileException("userProfile","profileImageFile",image.getContentType(),"Invalid image type or contents");
            var reader=readers.next();
            try {reader.setInput(input);if((long)reader.getWidth(0)*reader.getHeight(0)>20_000_000L)
                throw new InvalidFileException("userProfile","profileImageFile",image.getSize(),"Image exceeds size limit");}
            finally {reader.dispose();}
        } catch(IOException exception) {throw new InvalidFileException("userProfile","profileImageFile",image.getContentType(),"Invalid image type or contents");}
    }
}
