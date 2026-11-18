package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.*;
@Getter @Setter @Entity @Table(name="users")
public class User {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, unique=true, length=100) private String email;
    @Column(name="password_hash", nullable=false, length=500) private String passwordHash;
    @Column(nullable=false) private boolean active = true;
    @Column(name="account_non_locked", nullable=false) private boolean accountNonLocked = true;
    @Column(name="last_password_change") private LocalDateTime lastPasswordChange;
    @Column(name="password_expires_at") private LocalDateTime passwordExpiresAt;
    @Column(name="failed_login_attempts") private int failedLoginAttempts;
    @Column(name="email_verified") private boolean emailVerified;
    @Column(name="must_change_password") private boolean mustChangePassword;
    @OneToOne(mappedBy="user", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.LAZY)
    private UserProfile profile;
    @org.hibernate.annotations.BatchSize(size=50)
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="user_roles", joinColumns=@JoinColumn(name="user_id"), inverseJoinColumns=@JoinColumn(name="role_id"))
    private Set<Role> roles = new HashSet<>();
}
