package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.*;
@Getter @Setter @Entity @Table(name="roles")
public class Role {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, unique=true, length=50) private String name;
    @Column(name="display_name", nullable=false, length=100) private String displayName;
    @Column(length=255) private String description;
    @ManyToMany(mappedBy="roles") private Set<User> users = new HashSet<>();
}
