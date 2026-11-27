package org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO genérico de lectura para Player (desplegable del formulario y detalle de la participación).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerDTO {
    private String puuid;
    private String riotName;
    private String riotTag;
    private String region;
    private Integer accountLevel;
    private String currentRank;

    /** Atributo derivado PartidasRegistradas: solo se rellena en el detalle. */
    private Long registeredMatches;

    /** Riot ID completo, por ejemplo "NeonDrift#EUW". */
    public String getRiotId() {
        return riotName + "#" + riotTag;
    }
}
