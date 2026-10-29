package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.daos;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.entities.Region;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class RegionDAOImpl implements RegionDAO {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Region> rowMapper = (rs, row) ->
            new Region(rs.getLong("id"), rs.getString("code"), rs.getString("name"));

    public RegionDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override public List<Region> findAll() {
        return jdbcTemplate.query("SELECT id, code, name FROM regions ORDER BY name", rowMapper);
    }
    @Override public Optional<Region> findById(Long id) {
        return jdbcTemplate.query("SELECT id, code, name FROM regions WHERE id = ?", rowMapper, id).stream().findFirst();
    }
    @Override public boolean existsRegionByCode(String code) {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM regions WHERE UPPER(code) = UPPER(?)", Integer.class, code.trim());
        return count != null && count > 0;
    }
    @Override public boolean existsRegionByCodeAndNotId(String code, Long id) {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM regions WHERE UPPER(code) = UPPER(?) AND id <> ?", Integer.class, code.trim(), id);
        return count != null && count > 0;
    }
    @Override public int insertRegion(Region region) {
        KeyHolder keys = new GeneratedKeyHolder();
        int changed = jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO regions (code, name) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, region.getCode().trim());
            statement.setString(2, region.getName().trim());
            return statement;
        }, keys);
        if (keys.getKey() != null) region.setId(keys.getKey().longValue());
        return changed;
    }
    @Override public int updateRegion(Region region) {
        return jdbcTemplate.update("UPDATE regions SET code = ?, name = ? WHERE id = ?",
                region.getCode().trim(), region.getName().trim(), region.getId());
    }
    @Override public int deleteRegion(Long id) {
        return jdbcTemplate.update("DELETE FROM regions WHERE id = ?", id);
    }
}
