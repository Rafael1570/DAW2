package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.daos;

import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.entities.Province;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class ProvinceDAOImpl implements ProvinceDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Province> findAll() {
        return entityManager.createQuery("SELECT p FROM Province p JOIN FETCH p.region ORDER BY p.id", Province.class).getResultList();
    }

    @Override
    public long countProvinces() {
        return entityManager.createQuery("SELECT COUNT(e) FROM Province e", Long.class).getSingleResult();
    }

    @Override
    public List<Province> listProvincesPage(int page, int size, String sortField, String sortDir) {
        if (page < 0 || size < 1 || size > 100 || (long) page * size > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Parámetros de paginación fuera de rango");
        }
        var cb = entityManager.getCriteriaBuilder();
        var query = cb.createQuery(Province.class);
        var root = query.from(Province.class);
        root.fetch("region", jakarta.persistence.criteria.JoinType.INNER);
        jakarta.persistence.criteria.Path<?> path = switch (sortField) {
            case "id", "code", "name" -> root.get(sortField);
            case "regionName" -> root.get("region").get("name");
            default -> root.get("name");
        };
        var order = "desc".equalsIgnoreCase(sortDir) ? cb.desc(path) : cb.asc(path);
        // El ID desempata para mantener páginas estables con nombres repetidos.
        query.select(root).orderBy(order, cb.asc(root.get("id")));
        return entityManager.createQuery(query)
                .setFirstResult(page * size).setMaxResults(size).getResultList();
    }

    @Override
    public Optional<Province> findById(Long id) {
        return entityManager.createQuery("SELECT DISTINCT p FROM Province p JOIN FETCH p.region WHERE p.id = :id", Province.class)
                .setParameter("id", id).getResultStream().findFirst();
    }

    @Override
    public boolean existsProvinceByCode(String code) {
        return entityManager.createQuery("SELECT COUNT(p) FROM Province p WHERE UPPER(p.code) = :code", Long.class)
                .setParameter("code", code.toUpperCase(Locale.ROOT)).getSingleResult() > 0;
    }

    @Override
    public boolean existsProvinceByCodeAndNotId(String code, Long id) {
        return entityManager.createQuery("SELECT COUNT(p) FROM Province p WHERE UPPER(p.code) = :code AND p.id <> :id", Long.class)
                .setParameter("code", code.toUpperCase(Locale.ROOT)).setParameter("id", id).getSingleResult() > 0;
    }

    @Override
    @Transactional
    public int insertProvince(Province province) {
        entityManager.persist(province);
        entityManager.flush();
        return 1;
    }

    @Override
    @Transactional
    public int updateProvince(Province province) {
        if (entityManager.find(Province.class, province.getId()) == null) return 0;
        entityManager.merge(province);
        entityManager.flush();
        return 1;
    }

    @Override
    @Transactional
    public int deleteProvince(Long id) {
        Province entity = entityManager.find(Province.class, id);
        if (entity == null) return 0;
        entityManager.remove(entity);
        entityManager.flush();
        return 1;
    }
}
