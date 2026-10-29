package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.daos;

import java.util.List;
import java.util.Optional;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.entities.Region;

public interface RegionDAO {
    List<Region> findAll();
    Optional<Region> findById(Long id);
    boolean existsRegionByCode(String code);
    boolean existsRegionByCodeAndNotId(String code, Long id);
    int insertRegion(Region region);
    int updateRegion(Region region);
    int deleteRegion(Long id);
}
