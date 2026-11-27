package org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.services;

import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.daos.ParticipationDAO;
import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.daos.PlayerDAO;
import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.dtos.ParticipationCreateDTO;
import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.dtos.ParticipationDTO;
import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.dtos.ParticipationDetailDTO;
import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.dtos.ParticipationUpdateDTO;
import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.entities.Participation;
import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.entities.Player;
import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.exceptions.DuplicateResourceException;
import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.exceptions.ResourceNotFoundException;
import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.mappers.ParticipationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación de la lógica de negocio (casos de uso) para el CRUD de {@link Participation}.
 * <p>
 * Esta capa se encarga de:
 * <ul>
 *   <li>Interactuar con los DAOs ({@link ParticipationDAO} y {@link PlayerDAO}) para acceder a base de datos.</li>
 *   <li>Aplicar reglas de negocio (por ejemplo, que un jugador no esté dos veces en la misma partida).</li>
 *   <li>Transformar entidades a DTOs y viceversa mediante {@link ParticipationMapper}.</li>
 *   <li>Lanzar excepciones semánticas reutilizables ({@link ResourceNotFoundException},
 *       {@link DuplicateResourceException}) para que la capa web decida cómo presentarlas.</li>
 * </ul>
 */
@Service
@Transactional
public class ParticipationServiceImpl implements ParticipationService {

    @Autowired
    private ParticipationDAO participationDAO;

    @Autowired
    private PlayerDAO playerDAO;

    /**
     * Devuelve una lista paginada de participaciones.
     * El DAO recibe página, tamaño, campo y dirección de ordenación; aquí se construye
     * el {@link Page} que necesita la vista a partir de la lista y el total de registros.
     *
     * @param pageable parámetros de paginación y ordenación
     * @return página de {@link ParticipationDTO}
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParticipationDTO> list(Pageable pageable) {
        // Campo y dirección de ordenación (por defecto: id asc)
        String sortField = "id";
        String sortDir = "asc";
        if (pageable.getSort().isSorted()) {
            Sort.Order order = pageable.getSort().iterator().next();
            sortField = order.getProperty();
            sortDir = order.getDirection().name().toLowerCase();
        }

        List<Participation> participations = participationDAO.listParticipationsPage(
                pageable.getPageNumber(), pageable.getPageSize(), sortField, sortDir);
        long total = participationDAO.countParticipations();

        return new PageImpl<>(ParticipationMapper.toDTOList(participations), pageable, total);
    }

    /**
     * Obtiene los datos necesarios para cargar el formulario de edición.
     *
     * @param id identificador de la participación
     * @return DTO de edición
     * @throws ResourceNotFoundException si no existe la participación con ese id
     */
    @Override
    @Transactional(readOnly = true)
    public ParticipationUpdateDTO getForEdit(Long id) {
        Participation participation = findParticipationOrThrow(id);
        return ParticipationMapper.toUpdateDTO(participation);
    }

    /**
     * Crea una nueva participación.
     *
     * @param dto datos del formulario de creación
     * @throws DuplicateResourceException si el jugador ya tiene una participación en esa partida
     * @throws ResourceNotFoundException  si no existe el jugador indicado
     */
    @Override
    public void create(ParticipationCreateDTO dto) {
        if (participationDAO.existsParticipationByMatchAndPlayer(dto.getMatchId(), dto.getPlayerPuuid())) {
            throw new DuplicateResourceException("participation", "matchId+player",
                    dto.getMatchId() + "/" + dto.getPlayerPuuid());
        }
        Player player = findPlayerOrThrow(dto.getPlayerPuuid());

        Participation participation = ParticipationMapper.toEntity(dto);
        // El mapper solo conoce el PUUID. Asociamos el jugador que ya hemos recuperado de la base de datos.
        participation.setPlayer(player);
        participationDAO.insertParticipation(participation);
    }

    /**
     * Actualiza una participación existente.
     *
     * @param dto datos del formulario de edición
     * @throws DuplicateResourceException si otra participación ya tiene esa partida y ese jugador
     * @throws ResourceNotFoundException  si no existe la participación a actualizar o el jugador indicado
     */
    @Override
    public void update(ParticipationUpdateDTO dto) {

        // Excluimos la participación editada para permitir guardar la misma pareja de partida y jugador.
        if (participationDAO.existsParticipationByMatchAndPlayerAndNotId(
                dto.getMatchId(), dto.getPlayerPuuid(), dto.getId())) {
            throw new DuplicateResourceException("participation", "matchId+player",
                    dto.getMatchId() + "/" + dto.getPlayerPuuid());
        }

        Participation participation = findParticipationOrThrow(dto.getId());
        Player player = findPlayerOrThrow(dto.getPlayerPuuid());

        ParticipationMapper.copyToExistingEntity(dto, participation);
        participation.setPlayer(player);
        participationDAO.updateParticipation(participation);
    }

    /**
     * Elimina una participación por id.
     *
     * @param id identificador de la participación
     * @throws ResourceNotFoundException si no existe la participación a eliminar
     */
    @Override
    public void delete(Long id) {
        findParticipationOrThrow(id);
        participationDAO.deleteParticipation(id);
    }

    /**
     * Devuelve el detalle de una participación (incluyendo su jugador asociado
     * y el número de partidas registradas de ese jugador).
     *
     * @param id identificador de la participación
     * @return DTO de detalle
     * @throws ResourceNotFoundException si no existe la participación
     */
    @Override
    @Transactional(readOnly = true)
    public ParticipationDetailDTO getDetail(Long id) {
        Participation participation = findParticipationOrThrow(id);
        ParticipationDetailDTO dto = ParticipationMapper.toDetailDTO(participation);
        if (dto.getPlayer() != null) {
            // Atributo derivado PartidasRegistradas del jugador
            dto.getPlayer().setRegisteredMatches(
                    participationDAO.countParticipationsByPlayer(dto.getPlayer().getPuuid()));
        }
        return dto;
    }

    /**
     * Busca una participación por id o lanza {@link ResourceNotFoundException} si no existe.
     * El DAO devuelve null cuando no la encuentra.
     */
    private Participation findParticipationOrThrow(Long id) {
        Participation participation = participationDAO.getParticipationById(id);
        if (participation == null) {
            throw new ResourceNotFoundException("participation", "id", id);
        }
        return participation;
    }

    /**
     * Busca un jugador por PUUID o lanza {@link ResourceNotFoundException} si no existe.
     */
    private Player findPlayerOrThrow(String puuid) {
        Player player = playerDAO.getPlayerByPuuid(puuid);
        if (player == null) {
            throw new ResourceNotFoundException("player", "puuid", puuid);
        }
        return player;
    }
}
