package org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.mappers;

import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.dtos.ParticipationCreateDTO;
import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.dtos.ParticipationDTO;
import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.dtos.ParticipationDetailDTO;
import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.dtos.ParticipationUpdateDTO;
import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.entities.Participation;
import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.entities.Player;

import java.util.List;

/**
 * Mapper utilitario entre la entidad Participation y sus DTOs.
 * Implementación simple sin frameworks de mapeo.
 * Todos sus métodos son estáticos para poder usarlos sin instanciar la clase.
 * Aquí se calculan también los atributos derivados del E-R (KDA, ACS y PorcentajeHS).
 */
public final class ParticipationMapper {

    private ParticipationMapper() {
        // Clase de utilidad: no instanciable.
    }

    // ─────────────────────────────────────────────
    // Entity → DTO (listado/tabla básico)
    // ─────────────────────────────────────────────

    /**
     * Convierte una entidad {@link Participation} a {@link ParticipationDTO}.
     */
    public static ParticipationDTO toDTO(Participation entity) {
        if (entity == null) return null;
        ParticipationDTO dto = new ParticipationDTO();
        dto.setId(entity.getId());
        dto.setMatchId(entity.getMatchId());
        // Establecemos solo el Riot ID del jugador, que es lo único que mostramos en los listados.
        Player player = entity.getPlayer();
        dto.setPlayerName(player != null ? player.getRiotName() + "#" + player.getRiotTag() : null);
        dto.setTeam(entity.getTeam());
        dto.setKills(entity.getKills());
        dto.setDeaths(entity.getDeaths());
        dto.setAssists(entity.getAssists());
        dto.setTotalScore(entity.getTotalScore());
        dto.setResult(entity.getResult());
        dto.setKda(calculateKda(entity));
        dto.setAcs(calculateAcs(entity));
        return dto;
    }

    /**
     * Convierte una lista de {@link Participation} a una lista de {@link ParticipationDTO}.
     */
    public static List<ParticipationDTO> toDTOList(List<Participation> entities) {
        if (entities == null) return List.of();
        return entities.stream().map(ParticipationMapper::toDTO).toList();
    }

    // ─────────────────────────────────────────────
    // Entity → DTO (detalle con jugador)
    // ─────────────────────────────────────────────

    /**
     * Convierte una {@link Participation} a {@link ParticipationDetailDTO}, incluyendo el jugador asociado.
     */
    public static ParticipationDetailDTO toDetailDTO(Participation entity) {
        if (entity == null) return null;
        ParticipationDetailDTO dto = new ParticipationDetailDTO();
        dto.setId(entity.getId());
        dto.setMatchId(entity.getMatchId());
        dto.setTeam(entity.getTeam());
        dto.setKills(entity.getKills());
        dto.setDeaths(entity.getDeaths());
        dto.setAssists(entity.getAssists());
        dto.setTotalScore(entity.getTotalScore());
        dto.setRoundsPlayed(entity.getRoundsPlayed());
        dto.setHeadshots(entity.getHeadshots());
        dto.setTotalHits(entity.getTotalHits());
        dto.setResult(entity.getResult());
        dto.setKda(calculateKda(entity));
        dto.setAcs(calculateAcs(entity));
        dto.setHeadshotPercentage(calculateHeadshotPercentage(entity));
        dto.setPlayer(PlayerMapper.toDTO(entity.getPlayer()));
        return dto;
    }

    /**
     * Convierte una {@link Participation} a {@link ParticipationUpdateDTO} para precargar el formulario de edición.
     */
    public static ParticipationUpdateDTO toUpdateDTO(Participation entity) {
        if (entity == null) return null;
        ParticipationUpdateDTO dto = new ParticipationUpdateDTO();
        dto.setId(entity.getId());
        dto.setMatchId(entity.getMatchId());
        dto.setPlayerPuuid(entity.getPlayer() != null ? entity.getPlayer().getPuuid() : null);
        dto.setTeam(entity.getTeam());
        dto.setKills(entity.getKills());
        dto.setDeaths(entity.getDeaths());
        dto.setAssists(entity.getAssists());
        dto.setTotalScore(entity.getTotalScore());
        dto.setRoundsPlayed(entity.getRoundsPlayed());
        dto.setHeadshots(entity.getHeadshots());
        dto.setTotalHits(entity.getTotalHits());
        dto.setResult(entity.getResult());
        return dto;
    }

    // ─────────────────────────────────────────────
    // DTO (create/update) → Entity
    // ─────────────────────────────────────────────

    /**
     * Crea una nueva entidad {@link Participation} desde un {@link ParticipationCreateDTO}.
     * (id se deja null para autogenerarse)
     */
    public static Participation toEntity(ParticipationCreateDTO dto) {
        if (dto == null) return null;
        Participation e = new Participation();
        e.setMatchId(dto.getMatchId().trim());
        e.setTeam(dto.getTeam());
        e.setKills(dto.getKills());
        e.setDeaths(dto.getDeaths());
        e.setAssists(dto.getAssists());
        e.setTotalScore(dto.getTotalScore());
        e.setRoundsPlayed(dto.getRoundsPlayed());
        e.setHeadshots(dto.getHeadshots());
        e.setTotalHits(dto.getTotalHits());
        e.setResult(dto.getResult());
        // Creamos un jugador vacío y le pasamos solo el PUUID,
        // ya que para la inserción de una participación solo necesitamos la clave del jugador
        Player player = new Player();
        player.setPuuid(dto.getPlayerPuuid());
        e.setPlayer(player);
        return e;
    }

    /**
     * Copia campos editables desde un {@link ParticipationUpdateDTO} a una entidad existente.
     * Recomendado para mantener relaciones y el estado de persistencia del contexto JPA.
     */
    public static void copyToExistingEntity(ParticipationUpdateDTO dto, Participation entity) {
        if (dto == null || entity == null) return;
        entity.setMatchId(dto.getMatchId().trim());
        entity.setTeam(dto.getTeam());
        entity.setKills(dto.getKills());
        entity.setDeaths(dto.getDeaths());
        entity.setAssists(dto.getAssists());
        entity.setTotalScore(dto.getTotalScore());
        entity.setRoundsPlayed(dto.getRoundsPlayed());
        entity.setHeadshots(dto.getHeadshots());
        entity.setTotalHits(dto.getTotalHits());
        entity.setResult(dto.getResult());
        // Creamos un jugador vacío y le pasamos solo el PUUID,
        // ya que para la actualización solo necesitamos la clave del jugador
        Player player = new Player();
        player.setPuuid(dto.getPlayerPuuid());
        entity.setPlayer(player);
        // No tocar entity.setId(...)
    }

    // ─────────────────────────────────────────────
    // Atributos derivados (D· y DN· del E-R)
    // ─────────────────────────────────────────────

    /**
     * KDA = (bajas + asistencias) / muertes. Si no hay muertes se divide entre 1.
     */
    public static Double calculateKda(Participation e) {
        if (e.getKills() == null || e.getAssists() == null || e.getDeaths() == null) return null;
        int deaths = Math.max(e.getDeaths(), 1);
        return round((e.getKills() + e.getAssists()) / (double) deaths, 2);
    }

    /**
     * ACS (Average Combat Score) = puntuación total / rondas jugadas. Null si no hay rondas.
     */
    public static Double calculateAcs(Participation e) {
        if (e.getTotalScore() == null || e.getRoundsPlayed() == null || e.getRoundsPlayed() == 0) return null;
        return round(e.getTotalScore() / (double) e.getRoundsPlayed(), 1);
    }

    /**
     * % HS = impactos en la cabeza / impactos totales * 100. Null si no hay impactos.
     */
    public static Double calculateHeadshotPercentage(Participation e) {
        if (e.getHeadshots() == null || e.getTotalHits() == null || e.getTotalHits() == 0) return null;
        return round(e.getHeadshots() * 100.0 / e.getTotalHits(), 1);
    }

    private static double round(double value, int decimals) {
        double factor = Math.pow(10, decimals);
        return Math.round(value * factor) / factor;
    }
}
