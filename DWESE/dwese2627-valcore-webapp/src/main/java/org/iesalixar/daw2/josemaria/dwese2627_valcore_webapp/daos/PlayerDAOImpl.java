package org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.entities.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional // Todas las operaciones del DAO se ejecutan dentro de una transacción.
public class PlayerDAOImpl implements PlayerDAO {

    private static final Logger logger = LoggerFactory.getLogger(PlayerDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Lista todos los jugadores ordenados por nombre de Riot (se usa en el desplegable del formulario).
     *
     * @return lista de jugadores
     */
    @Override
    public List<Player> listAllPlayers() {
        logger.info("Listing all players from the database.");
        String query = "SELECT p FROM Player p ORDER BY p.riotName, p.riotTag";
        List<Player> players = entityManager.createQuery(query, Player.class).getResultList();
        logger.info("Retrieved {} players from the database.", players.size());
        return players;
    }

    /**
     * Obtiene un jugador por su PUUID.
     *
     * @param puuid PUUID del jugador
     * @return el jugador o null si no existe
     */
    @Override
    public Player getPlayerByPuuid(String puuid) {
        logger.info("Retrieving player by puuid: {}", puuid);
        Player player = entityManager.find(Player.class, puuid);
        if (player != null) {
            logger.info("Player retrieved: {}#{}", player.getRiotName(), player.getRiotTag());
        } else {
            logger.warn("No player found with puuid: {}", puuid);
        }
        return player;
    }
}
