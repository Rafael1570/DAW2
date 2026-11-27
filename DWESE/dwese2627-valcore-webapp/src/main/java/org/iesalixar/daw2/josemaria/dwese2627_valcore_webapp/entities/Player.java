package org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity // Marca esta clase como una entidad gestionada por JPA.
@Table(name = "players") // Especifica el nombre de la tabla asociada a esta entidad.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    // PUUID de la cuenta de Riot. Es la clave primaria y no se autogenera: la asigna Riot.
    @Id
    @Column(name = "puuid", nullable = false, length = 78)
    private String puuid;

    // Nombre de la cuenta de Riot (parte anterior al '#'). Ejemplo: "NeonDrift".
    @Column(name = "riot_name", nullable = false, length = 16)
    private String riotName;

    // Tag de la cuenta de Riot (parte posterior al '#'). Ejemplo: "EUW".
    @Column(name = "riot_tag", nullable = false, length = 5)
    private String riotTag;

    // Región del servidor (atributo codificable: eu, na, ap, kr, latam, br).
    @Column(name = "region", nullable = false, length = 5)
    private String region;

    // Nivel de la cuenta.
    @Column(name = "account_level", nullable = false)
    private Integer accountLevel;

    // Rango competitivo actual (codificable y admite nulos: un jugador sin rango tiene null).
    @Column(name = "current_rank", length = 30)
    private String currentRank;

    /**
     * Lista de participaciones del jugador.
     * - LAZY: no se cargan hasta que se accede a 'participations'.
     * - mappedBy: el dueño de la relación es Participation.player.
     * - Con cascade ALL: se borrarían las participaciones del jugador si se elimina el jugador.
     */
    @OneToMany(
            mappedBy = "player",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL},
            orphanRemoval = false
    )
    @ToString.Exclude            // Evita bucles en toString()
    @EqualsAndHashCode.Exclude   // Evita ciclos en equals/hashCode
    private List<Participation> participations = new ArrayList<>();

    public Player(String puuid, String riotName, String riotTag, String region,
                  Integer accountLevel, String currentRank) {
        this.puuid = puuid;
        this.riotName = riotName;
        this.riotTag = riotTag;
        this.region = region;
        this.accountLevel = accountLevel;
        this.currentRank = currentRank;
    }
}
