package org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.mappers;

import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.dtos.PlayerDTO;
import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.entities.Player;

import java.util.List;

/**
 * Mapper utilitario entre la entidad Player y su DTO.
 * Implementación simple sin frameworks de mapeo.
 * Es una clase de utilidad: todos sus métodos son estáticos y no se puede instanciar.
 */
public final class PlayerMapper {

    private PlayerMapper() {
        // Clase de utilidad: no instanciable.
    }

    /**
     * Convierte una entidad {@link Player} a {@link PlayerDTO}.
     * El campo registeredMatches se deja a null; lo rellena el servicio cuando hace falta (detalle).
     */
    public static PlayerDTO toDTO(Player entity) {
        if (entity == null) return null;
        PlayerDTO dto = new PlayerDTO();
        dto.setPuuid(entity.getPuuid());
        dto.setRiotName(entity.getRiotName());
        dto.setRiotTag(entity.getRiotTag());
        dto.setRegion(entity.getRegion());
        dto.setAccountLevel(entity.getAccountLevel());
        dto.setCurrentRank(entity.getCurrentRank());
        return dto;
    }

    /**
     * Convierte una lista de {@link Player} a una lista de {@link PlayerDTO}.
     */
    public static List<PlayerDTO> toDTOList(List<Player> entities) {
        if (entities == null) return List.of();
        return entities.stream().map(PlayerMapper::toDTO).toList();
    }
}
