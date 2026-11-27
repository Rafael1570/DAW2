package org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Marca esta clase como una entidad JPA.
@Table(name = "participations") // Define el nombre de la tabla asociada a esta entidad.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Participation {

    // Identificador único de la participación (AUTO_INCREMENT en la tabla 'participations').
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Identificador de la partida (IdPartida del modelo E-R).
    @Column(name = "match_id", nullable = false, length = 64)
    private String matchId;

    // Relación con la entidad `Player`, el jugador que ha jugado esta participación.
    @ManyToOne(fetch = FetchType.LAZY) // Relación de muchas participaciones a un jugador.
    @JoinColumn(name = "player_puuid", nullable = false) // Clave foránea que referencia a players(puuid).
    private Player player;

    // Equipo del jugador en la partida (atributo codificable: RED o BLUE).
    @Column(name = "team", nullable = false, length = 10)
    private String team;

    @Column(name = "kills", nullable = false)
    private Integer kills;

    @Column(name = "deaths", nullable = false)
    private Integer deaths;

    @Column(name = "assists", nullable = false)
    private Integer assists;

    // Puntuación de combate total de la partida (sirve para calcular el ACS).
    @Column(name = "total_score", nullable = false)
    private Integer totalScore;

    @Column(name = "rounds_played", nullable = false)
    private Integer roundsPlayed;

    // Impactos en la cabeza (sirve para calcular el porcentaje de headshots).
    @Column(name = "headshots", nullable = false)
    private Integer headshots;

    @Column(name = "total_hits", nullable = false)
    private Integer totalHits;

    // Resultado de la partida para el jugador (atributo codificable: VICTORY, DEFEAT o DRAW).
    @Column(name = "result", nullable = false, length = 10)
    private String result;
}
