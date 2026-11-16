package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.daos;

import java.util.List;
import java.util.Optional;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.entities.Province;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.entities.Region;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProvinceDAOImpl implements ProvinceDAO {
    private static final String SELECT = "SELECT p.id, p.code, p.name, r.id AS region_id, "
            + "r.code AS region_code, r.name AS region_name FROM provinces p "
            + "JOIN regions r ON r.id = p.region_id";
    private final JdbcTemplate jdbc;
    private final RowMapper<Province> mapper = (rs, rowNum) -> new Province(
            rs.getLong("id"), rs.getString("code"), rs.getString("name"),
            new Region(rs.getLong("region_id"), rs.getString("region_code"), rs.getString("region_name")));

    public ProvinceDAOImpl(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    public List<Province> findAll() {
        return jdbc.query(SELECT + " ORDER BY p.name", mapper);
    }

    public Optional<Province> findById(long id) {
        return jdbc.query(SELECT + " WHERE p.id = ?", mapper, id).stream().findFirst();
    }

    public boolean existsByCode(String code) {
        return jdbc.queryForObject("SELECT COUNT(*) FROM provinces WHERE code = ?", Integer.class, code) > 0;
    }

    public boolean existsByCodeAndNotId(String code, long id) {
        return jdbc.queryForObject("SELECT COUNT(*) FROM provinces WHERE code = ? AND id <> ?", Integer.class, code, id) > 0;
    }

    public void insert(Province province) {
        jdbc.update("INSERT INTO provinces (code, name, region_id) VALUES (?, ?, ?)",
                province.getCode(), province.getName(), province.getRegion().getId());
    }

    public void update(Province province) {
        jdbc.update("UPDATE provinces SET code = ?, name = ?, region_id = ? WHERE id = ?",
                province.getCode(), province.getName(), province.getRegion().getId(), province.getId());
    }

    public void delete(long id) {
        jdbc.update("DELETE FROM provinces WHERE id = ?", id);
    }
}
