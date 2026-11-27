package org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de detalle de Participation.
 * Incluye el jugador al que pertenece y todos los atributos derivados.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationDetailDTO {
    private Long id;
    private String matchId;
    private String team;
    private Integer kills;
    private Integer deaths;
    private Integer assists;
    private Integer totalScore;
    private Integer roundsPlayed;
    private Integer headshots;
    private Integer totalHits;
    private String result;

    // Atributos derivados (no se guardan en la BD)
    private Double kda;
    /** Puede ser null (DN· en el E-R). */
    private Double acs;
    /** Puede ser null (DN· en el E-R): no hay impactos. */
    private Double headshotPercentage;

    /** Jugador asociado a la participación (objeto embebido para el detalle). */
    private PlayerDTO player;
}
