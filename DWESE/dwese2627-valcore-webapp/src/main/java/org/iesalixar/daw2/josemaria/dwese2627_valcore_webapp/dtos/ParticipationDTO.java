package org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO genérico de lectura para Participation (listado).
 * Incluye el Riot ID del jugador y los atributos derivados que se muestran en la tabla.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationDTO {
    private Long id;
    private String matchId;
    /** Riot ID del jugador asociado, por ejemplo "NeonDrift#EUW". */
    private String playerName;
    private String team;
    private Integer kills;
    private Integer deaths;
    private Integer assists;
    private Integer totalScore;
    private String result;

    // Atributos derivados (no se guardan en la BD)
    private Double kda;
    /** Puede ser null (DN· en el E-R). */
    private Double acs;
}
