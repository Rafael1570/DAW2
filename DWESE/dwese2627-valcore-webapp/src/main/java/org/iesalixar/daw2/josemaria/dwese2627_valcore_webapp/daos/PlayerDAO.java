package org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.daos;

import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.entities.Player;

import java.util.List;

public interface PlayerDAO {

    List<Player> listAllPlayers();

    Player getPlayerByPuuid(String puuid);
}
