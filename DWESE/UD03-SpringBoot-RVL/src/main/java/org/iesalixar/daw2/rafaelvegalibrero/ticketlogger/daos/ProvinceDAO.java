package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.daos;

import java.util.List;
import java.util.Optional;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.entities.Province;

public interface ProvinceDAO {
    List<Province> findAll();
    Optional<Province> findById(long id);
    boolean existsByCode(String code);
    boolean existsByCodeAndNotId(String code, long id);
    void insert(Province province);
    void update(Province province);
    void delete(long id);
}
