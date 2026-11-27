package org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.services;

import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.dtos.ParticipationCreateDTO;
import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.dtos.ParticipationDTO;
import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.dtos.ParticipationDetailDTO;
import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.dtos.ParticipationUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Servicio con los casos de uso (CRUD) de Participation.
 */
public interface ParticipationService {

    Page<ParticipationDTO> list(Pageable pageable);

    ParticipationUpdateDTO getForEdit(Long id);

    void create(ParticipationCreateDTO dto);

    void update(ParticipationUpdateDTO dto);

    void delete(Long id);

    ParticipationDetailDTO getDetail(Long id);
}
