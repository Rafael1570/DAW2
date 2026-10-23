package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.daos;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.entities.Province;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.entities.Region;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ProvinceDAOImpl implements ProvinceDAO {
    private static final String SELECT_JOIN = "SELECT p.id, p.code, p.name, r.id AS region_id, r.code AS region_code, r.name AS region_name " +
            "FROM provinces p JOIN regions r ON p.region_id = r.id ";
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Province> rowMapper = (rs, row) -> {
        Region region = new Region(rs.getLong("region_id"), rs.getString("region_code"), rs.getString("region_name"));
        return new Province(rs.getLong("id"), rs.getString("code"), rs.getString("name"), region);
    };

    public ProvinceDAOImpl(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    @Override public List<Province> findAll() {
        return jdbcTemplate.query(SELECT_JOIN + "ORDER BY r.name, p.name", rowMapper);
    }
    @Override public Optional<Province> findById(Long id) {
        return jdbcTemplate.query(SELECT_JOIN + "WHERE p.id = ?", rowMapper, id).stream().findFirst();
    }
    @Override public boolean existsProvinceByCode(String code) {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM provinces WHERE UPPER(code) = UPPER(?)", Integer.class, code.trim());
        return count != null && count > 0;
    }
    @Override public boolean existsProvinceByCodeAndNotId(String code, Long id) {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM provinces WHERE UPPER(code) = UPPER(?) AND id <> ?", Integer.class, code.trim(), id);
        return count != null && count > 0;
    }
    @Override public int insertProvince(Province province) {
        KeyHolder keys = new GeneratedKeyHolder();
        int changed = jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO provinces (code, name, region_id) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, province.getCode().trim());
            statement.setString(2, province.getName().trim());
            statement.setLong(3, province.getRegion().getId());
            return statement;
        }, keys);
        if (keys.getKey() != null) province.setId(keys.getKey().longValue());
        return changed;
    }
    @Override public int updateProvince(Province province) {
        return jdbcTemplate.update("UPDATE provinces SET code = ?, name = ?, region_id = ? WHERE id = ?",
                province.getCode().trim(), province.getName().trim(), province.getRegion().getId(), province.getId());
    }
    @Override public int deleteProvince(Long id) {
        return jdbcTemplate.update("DELETE FROM provinces WHERE id = ?", id);
    }
}
