package org.iesalixar.daw2.rafael.entities;

import java.time.LocalDateTime;

public class User {
    private Long id;
    private String username;
    private String passwordHash;
    private Boolean active;
    private Boolean accountNonLocked;
    private LocalDateTime lastPasswordChange;
    private LocalDateTime passwordExpiresAt;
    private Integer failedLoginAttempts;
    private Boolean emailVerified;
    private Boolean mustChangePassword;

    public User() {}

    public User(Long id, String username, String passwordHash, Boolean active,
                Boolean accountNonLocked, LocalDateTime lastPasswordChange,
                LocalDateTime passwordExpiresAt, Integer failedLoginAttempts,
                Boolean emailVerified, Boolean mustChangePassword) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.active = active;
        this.accountNonLocked = accountNonLocked;
        this.lastPasswordChange = lastPasswordChange;
        this.passwordExpiresAt = passwordExpiresAt;
        this.failedLoginAttempts = failedLoginAttempts;
        this.emailVerified = emailVerified;
        this.mustChangePassword = mustChangePassword;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public Boolean getAccountNonLocked() { return accountNonLocked; }
    public void setAccountNonLocked(Boolean accountNonLocked) { this.accountNonLocked = accountNonLocked; }
    public LocalDateTime getLastPasswordChange() { return lastPasswordChange; }
    public void setLastPasswordChange(LocalDateTime lastPasswordChange) { this.lastPasswordChange = lastPasswordChange; }
    public LocalDateTime getPasswordExpiresAt() { return passwordExpiresAt; }
    public void setPasswordExpiresAt(LocalDateTime passwordExpiresAt) { this.passwordExpiresAt = passwordExpiresAt; }
    public Integer getFailedLoginAttempts() { return failedLoginAttempts; }
    public void setFailedLoginAttempts(Integer failedLoginAttempts) { this.failedLoginAttempts = failedLoginAttempts; }
    public Boolean getEmailVerified() { return emailVerified; }
    public void setEmailVerified(Boolean emailVerified) { this.emailVerified = emailVerified; }
    public Boolean getMustChangePassword() { return mustChangePassword; }
    public void setMustChangePassword(Boolean mustChangePassword) { this.mustChangePassword = mustChangePassword; }

    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "', active=" + active
                + ", accountNonLocked=" + accountNonLocked + ", lastPasswordChange="
                + lastPasswordChange + ", passwordExpiresAt=" + passwordExpiresAt
                + ", failedLoginAttempts=" + failedLoginAttempts + ", emailVerified="
                + emailVerified + ", mustChangePassword=" + mustChangePassword + '}';
    }
}
