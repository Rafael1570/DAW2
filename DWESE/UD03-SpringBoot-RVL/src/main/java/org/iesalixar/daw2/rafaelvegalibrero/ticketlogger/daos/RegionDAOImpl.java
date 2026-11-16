package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.daos;

import java.util.List;
import java.util.Optional;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.entities.Region;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class RegionDAOImpl implements RegionDAO {
    private final JdbcTemplate jdbc;
    private final RowMapper<Region> mapper = (rs, rowNum) ->
            new Region(rs.getLong("id"), rs.getString("code"), rs.getString("name"));

    public RegionDAOImpl(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    public List<Region> findAll() {
        return jdbc.query("SELECT id, code, name FROM regions ORDER BY name", mapper);
    }

    public Optional<Region> findById(long id) {
        return jdbc.query("SELECT id, code, name FROM regions WHERE id = ?", mapper, id)
                .stream().findFirst();
    }

    public boolean existsByCode(String code) {
        return jdbc.queryForObject("SELECT COUNT(*) FROM regions WHERE code = ?", Integer.class, code) > 0;
    }

    public boolean existsByCodeAndNotId(String code, long id) {
        return jdbc.queryForObject("SELECT COUNT(*) FROM regions WHERE code = ? AND id <> ?", Integer.class, code, id) > 0;
    }

    public void insert(Region region) {
        jdbc.update("INSERT INTO regions (code, name) VALUES (?, ?)", region.getCode(), region.getName());
    }

    public void update(Region region) {
        jdbc.update("UPDATE regions SET code = ?, name = ? WHERE id = ?", region.getCode(), region.getName(), region.getId());
    }

    public void delete(long id) {
        jdbc.update("DELETE FROM regions WHERE id = ?", id);
    }
}
