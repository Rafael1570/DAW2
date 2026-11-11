package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Getter
@Setter
@Entity
@Table(name = "regions")
@NoArgsConstructor
@AllArgsConstructor
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 2)
    private String code;

    @Column(nullable = false, length = 100)
    private String name;

    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private java.util.List<Province> provinces = new java.util.ArrayList<>();

    public Region(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
