package org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para creación (INSERT) de Participation.
 * El id lo genera la BD. El jugador llega solo con su PUUID (desde el <select> del formulario).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationCreateDTO {

    // En este DTO este campo siempre vendrá null porque es una inserción
    private Long id;

    @NotBlank(message = "{msg.participation.matchId.notEmpty}")
    @Size(max = 64, message = "{msg.participation.matchId.size}")
    private String matchId;

    @NotBlank(message = "{msg.participation.playerPuuid.notNull}")
    private String playerPuuid;

    @NotBlank(message = "{msg.participation.team.notEmpty}")
    // El "?" deja pasar el vacío: de ese caso ya se encarga @NotBlank y así no salen dos mensajes
    @Pattern(regexp = "(RED|BLUE)?", message = "{msg.participation.team.pattern}")
    private String team;

    @NotNull(message = "{msg.participation.kills.notNull}")
    @Min(value = 0, message = "{msg.participation.kills.min}")
    private Integer kills;

    @NotNull(message = "{msg.participation.deaths.notNull}")
    @Min(value = 0, message = "{msg.participation.deaths.min}")
    private Integer deaths;

    @NotNull(message = "{msg.participation.assists.notNull}")
    @Min(value = 0, message = "{msg.participation.assists.min}")
    private Integer assists;

    @NotNull(message = "{msg.participation.totalScore.notNull}")
    @Min(value = 0, message = "{msg.participation.totalScore.min}")
    private Integer totalScore;

    @NotNull(message = "{msg.participation.roundsPlayed.notNull}")
    @Min(value = 1, message = "{msg.participation.roundsPlayed.min}")
    @Max(value = 50, message = "{msg.participation.roundsPlayed.max}")
    private Integer roundsPlayed;

    @NotNull(message = "{msg.participation.headshots.notNull}")
    @Min(value = 0, message = "{msg.participation.headshots.min}")
    private Integer headshots;

    @NotNull(message = "{msg.participation.totalHits.notNull}")
    @Min(value = 0, message = "{msg.participation.totalHits.min}")
    private Integer totalHits;

    @NotBlank(message = "{msg.participation.result.notEmpty}")
    @Pattern(regexp = "(VICTORY|DEFEAT|DRAW)?", message = "{msg.participation.result.pattern}")
    private String result;
}
