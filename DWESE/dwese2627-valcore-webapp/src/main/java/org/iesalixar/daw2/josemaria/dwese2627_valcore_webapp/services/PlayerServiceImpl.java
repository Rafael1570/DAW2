package org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.services;

import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.daos.PlayerDAO;
import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.dtos.PlayerDTO;
import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.mappers.PlayerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación del servicio de {@link org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.entities.Player}.
 */
@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerDAO playerDAO;

    @Override
    @Transactional(readOnly = true)
    public List<PlayerDTO> listAll() {
        return PlayerMapper.toDTOList(playerDAO.listAllPlayers());
    }
}
