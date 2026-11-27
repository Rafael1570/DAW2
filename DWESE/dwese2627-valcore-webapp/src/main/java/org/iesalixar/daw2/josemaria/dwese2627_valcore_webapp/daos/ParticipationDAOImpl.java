package org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.entities.Participation;
import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.entities.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional // Todas las operaciones del DAO se ejecutan dentro de una transacción.
public class ParticipationDAOImpl implements ParticipationDAO {

    private static final Logger logger = LoggerFactory.getLogger(ParticipationDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Lista todas las participaciones junto con su jugador (JOIN FETCH).
     *
     * @return lista de participaciones
     */
    @Override
    public List<Participation> listAllParticipations() {
        logger.info("Listing all participations with their players from the database.");
        String query = "SELECT pa FROM Participation pa JOIN FETCH pa.player";
        List<Participation> participations = entityManager.createQuery(query, Participation.class).getResultList();
        logger.info("Retrieved {} participations from the database.", participations.size());
        return participations;
    }

    /**
     * Lista una página de participaciones con su jugador asociado.
     *
     * @param page      página actual (0-based)
     * @param size      número de elementos por página
     * @param sortField campo por el que se ordenan los resultados
     *                  (id, matchId, player.riotName, team, kills, deaths, assists, totalScore, result).
     * @param sortDir   dirección de ordenación ("asc" o "desc").
     * @return lista de participaciones para esa página
     */
    @Override
    public List<Participation> listParticipationsPage(int page, int size, String sortField, String sortDir) {
        logger.info("Listing participations page={}, size={}, sortField={}, sortDir={} from the database.",
                page, size, sortField, sortDir);
        // La página 0 empieza en la primera fila. Cada página anterior ocupa size filas.
        int offset = page * size;

        // 1. Construcción de Criteria
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Participation> cq = cb.createQuery(Participation.class);
        Root<Participation> root = cq.from(Participation.class);
        // Hacemos fetch de player para que venga cargado (equivalente al JOIN FETCH).
        // El fetch se usa también como join para poder ordenar por player.riotName sin hacer un segundo JOIN.
        @SuppressWarnings("unchecked")
        Join<Participation, Player> playerJoin =
                (Join<Participation, Player>) root.<Participation, Player>fetch("player", JoinType.INNER);

        // 2. Determinar el campo de ordenación permitido (whitelist)
        Path<?> sortPath;
        switch (sortField == null ? "" : sortField) {
            case "id" -> sortPath = root.get("id");
            case "matchId" -> sortPath = root.get("matchId");
            case "team" -> sortPath = root.get("team");
            case "kills" -> sortPath = root.get("kills");
            case "deaths" -> sortPath = root.get("deaths");
            case "assists" -> sortPath = root.get("assists");
            case "totalScore" -> sortPath = root.get("totalScore");
            case "result" -> sortPath = root.get("result");
            // La vista envía "player.riotName"; se acepta también "playerName"
            case "playerName", "player.riotName" -> sortPath = playerJoin.get("riotName");
            default -> {
                logger.warn("Unknown sortField '{}' for Participation, defaulting to 'id'.", sortField);
                sortPath = root.get("id");
            }
        }

        // 3. Dirección de ordenación
        boolean descending = "desc".equalsIgnoreCase(sortDir);
        Order order = descending ? cb.desc(sortPath) : cb.asc(sortPath);

        // 4. Aplicar ordenación a la query
        cq.select(root).orderBy(order);

        // 5. Crear TypedQuery, aplicar paginación y ejecutar
        return entityManager.createQuery(cq)
                .setFirstResult(offset)
                .setMaxResults(size)
                .getResultList();
    }

    /**
     * Cuenta todas las participaciones para calcular cuántas páginas hay en el listado.
     */
    @Override
    public long countParticipations() {
        String hql = "SELECT COUNT(pa) FROM Participation pa";
        Long total = entityManager.createQuery(hql, Long.class).getSingleResult();
        return (total != null) ? total : 0L;
    }

    /**
     * Devuelve el número de participaciones de un jugador
     * (atributo derivado PartidasRegistradas de JUGADOR).
     *
     * @param puuid PUUID del jugador
     */
    @Override
    public long countParticipationsByPlayer(String puuid) {
        String hql = "SELECT COUNT(pa) FROM Participation pa WHERE pa.player.puuid = :puuid";
        Long total = entityManager.createQuery(hql, Long.class)
                .setParameter("puuid", puuid)
                .getSingleResult();
        return (total != null) ? total : 0L;
    }

    /**
     * Inserta una nueva participación en la base de datos.
     *
     * @param participation participación a insertar
     */
    @Override
    public void insertParticipation(Participation participation) {
        logger.info("Inserting participation for match: {}", participation.getMatchId());
        participation.setPlayer(resolvePlayer(participation.getPlayer()));
        entityManager.persist(participation);
        logger.info("Inserted participation with ID: {}", participation.getId());
    }

    /**
     * Actualiza una participación existente en la base de datos.
     *
     * @param participation participación con los datos actualizados
     */
    @Override
    public void updateParticipation(Participation participation) {
        logger.info("Updating participation with id: {}", participation.getId());
        participation.setPlayer(resolvePlayer(participation.getPlayer()));
        entityManager.merge(participation);
        logger.info("Updated participation with id: {}", participation.getId());
    }

    /**
     * Elimina una participación de la base de datos por su ID.
     *
     * @param id ID de la participación a eliminar
     */
    @Override
    public void deleteParticipation(Long id) {
        logger.info("Deleting participation with id: {}", id);
        Participation participation = entityManager.find(Participation.class, id);
        if (participation != null) {
            // Se quita también de la colección del jugador para mantener coherente la relación bidireccional.
            if (participation.getPlayer() != null) {
                participation.getPlayer().getParticipations().remove(participation);
            }
            entityManager.remove(participation);
            logger.info("Deleted participation with id: {}", id);
        } else {
            logger.warn("Participation with id: {} not found.", id);
        }
    }

    /**
     * Obtiene una participación por su ID.
     *
     * @param id ID de la participación
     * @return la participación o null si no existe
     */
    @Override
    public Participation getParticipationById(Long id) {
        logger.info("Retrieving participation by id: {}", id);
        Participation participation = entityManager.find(Participation.class, id);
        if (participation != null) {
            logger.info("Participation retrieved: match {}", participation.getMatchId());
        } else {
            logger.warn("No participation found with id: {}", id);
        }
        return participation;
    }

    /**
     * Verifica si un jugador ya tiene registrada una participación en una partida.
     *
     * @param matchId identificador de la partida
     * @param puuid   PUUID del jugador
     * @return true si existe, false en caso contrario
     */
    @Override
    public boolean existsParticipationByMatchAndPlayer(String matchId, String puuid) {
        logger.info("Checking if participation for match: {} and player: {} exists", matchId, puuid);
        String query = "SELECT COUNT(pa) FROM Participation pa "
                + "WHERE UPPER(pa.matchId) = :matchId AND pa.player.puuid = :puuid";
        Long count = entityManager.createQuery(query, Long.class)
                .setParameter("matchId", matchId.trim().toUpperCase())
                .setParameter("puuid", puuid)
                .getSingleResult();
        boolean exists = count != null && count > 0;
        logger.info("Participation for match: {} and player: {} exists: {}", matchId, puuid, exists);
        return exists;
    }

    /**
     * Verifica si un jugador ya tiene registrada una participación en una partida,
     * excluyendo la participación con el ID indicado (para la edición).
     *
     * @param matchId identificador de la partida
     * @param puuid   PUUID del jugador
     * @param id      ID de la participación a excluir
     * @return true si existe otra participación con esa partida y jugador, false en caso contrario
     */
    @Override
    public boolean existsParticipationByMatchAndPlayerAndNotId(String matchId, String puuid, Long id) {
        logger.info("Checking if participation for match: {} and player: {} exists excluding id: {}",
                matchId, puuid, id);
        String query = "SELECT COUNT(pa) FROM Participation pa "
                + "WHERE UPPER(pa.matchId) = :matchId AND pa.player.puuid = :puuid AND pa.id != :id";
        Long count = entityManager.createQuery(query, Long.class)
                .setParameter("matchId", matchId.trim().toUpperCase())
                .setParameter("puuid", puuid)
                .setParameter("id", id)
                .getSingleResult();
        boolean exists = count != null && count > 0;
        logger.info("Participation for match: {} and player: {} exists excluding id {}: {}",
                matchId, puuid, id, exists);
        return exists;
    }

    /**
     * Sustituye el jugador recibido (que puede traer solo el PUUID, p. ej. desde un formulario)
     * por una referencia gestionada por JPA.
     */
    private Player resolvePlayer(Player player) {
        if (player == null || player.getPuuid() == null) {
            return player;
        }
        return entityManager.getReference(Player.class, player.getPuuid());
    }
}
