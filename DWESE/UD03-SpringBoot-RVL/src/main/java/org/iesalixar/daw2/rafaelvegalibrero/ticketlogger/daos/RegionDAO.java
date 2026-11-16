package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.daos;

import java.util.List;
import java.util.Optional;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.entities.Region;

public interface RegionDAO {
    List<Region> findAll();
    Optional<Region> findById(long id);
    boolean existsByCode(String code);
    boolean existsByCodeAndNotId(String code, long id);
    void insert(Region region);
    void update(Region region);
    void delete(long id);
}
