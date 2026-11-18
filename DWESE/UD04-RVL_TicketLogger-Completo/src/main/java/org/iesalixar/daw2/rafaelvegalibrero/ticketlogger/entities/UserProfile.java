package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
@Getter @Setter @Entity @Table(name="user_profiles")
public class UserProfile {
    @Id @Column(name="user_id") private Long id;
    @MapsId @OneToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="user_id") private User user;
    @Column(name="first_name", nullable=false, length=60) private String firstName;
    @Column(name="last_name", nullable=false, length=80) private String lastName;
    @Column(name="phone_number", length=30) private String phoneNumber;
    @Column(name="profile_image", length=255) private String profileImage;
    @Column(length=500) private String bio;
    @Column(length=10) private String locale;
    @Column(name="created_at", nullable=false, updatable=false) private LocalDateTime createdAt;
    @Column(name="updated_at", nullable=false) private LocalDateTime updatedAt;
    @PrePersist void created() { createdAt=LocalDateTime.now(); updatedAt=createdAt; }
    @PreUpdate void updated() { updatedAt=LocalDateTime.now(); }
}
