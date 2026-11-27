package org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.services;

import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.dtos.PlayerDTO;

import java.util.List;

/**
 * Servicio de Player. Solo se usa para cargar el desplegable del formulario de participaciones.
 */
public interface PlayerService {

    /**
     * Lista todos los jugadores (se usa para el desplegable del formulario de participaciones).
     */
    List<PlayerDTO> listAll();
}
