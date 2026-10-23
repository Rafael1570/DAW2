package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.daos;

import java.util.List;
import java.util.Optional;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.entities.Province;

public interface ProvinceDAO {
    List<Province> findAll();
    Optional<Province> findById(Long id);
    boolean existsProvinceByCode(String code);
    boolean existsProvinceByCodeAndNotId(String code, Long id);
    int insertProvince(Province province);
    int updateProvince(Province province);
    int deleteProvince(Long id);
}
