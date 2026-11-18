package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.dtos;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class UserProfileFormDTO {
    @NotNull @Positive private Long userId;
    private String email;
    @NotBlank(message="{msg.userProfile.firstName.notBlank}")
    @Size(max=60, message="{msg.userProfile.firstName.size}")
    private String firstName;
    @NotBlank(message="{msg.userProfile.lastName.notBlank}")
    @Size(max=80, message="{msg.userProfile.lastName.size}")
    private String lastName;
    @Size(max=30, message="{msg.userProfile.phoneNumber.size}")
    private String phoneNumber;
    @Size(max=255) private String profileImage;
    @Size(max=500, message="{msg.userProfile.bio.size}")
    private String bio;
    @Size(max=10, message="{msg.userProfile.locale.size}")
    private String locale;
}
