package org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.daos;

import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.entities.Participation;

import java.util.List;

public interface ParticipationDAO {

    List<Participation> listAllParticipations();

    List<Participation> listParticipationsPage(int page, int size, String sortField, String sortDir);

    long countParticipations();

    long countParticipationsByPlayer(String puuid);

    void insertParticipation(Participation participation);

    void updateParticipation(Participation participation);

    Participation getParticipationById(Long id);

    boolean existsParticipationByMatchAndPlayer(String matchId, String puuid);

    boolean existsParticipationByMatchAndPlayerAndNotId(String matchId, String puuid, Long id);

    void deleteParticipation(Long id);
}
