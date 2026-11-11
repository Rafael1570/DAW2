package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.daos;

import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.entities.Region;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class RegionDAOImpl implements RegionDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Region> findAll() {
        return entityManager.createQuery("SELECT r FROM Region r ORDER BY r.id", Region.class).getResultList();
    }

    @Override
    public long countRegions() {
        return entityManager.createQuery("SELECT COUNT(e) FROM Region e", Long.class).getSingleResult();
    }

    @Override
    public List<Region> listRegionsPage(int page, int size, String sortField, String sortDir) {
        if (page < 0 || size < 1 || size > 100 || (long) page * size > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Parámetros de paginación fuera de rango");
        }
        var cb = entityManager.getCriteriaBuilder();
        var query = cb.createQuery(Region.class);
        var root = query.from(Region.class);
        jakarta.persistence.criteria.Path<?> path = switch (sortField) {
            case "id", "code", "name" -> root.get(sortField);
            default -> root.get("name");
        };
        var order = "desc".equalsIgnoreCase(sortDir) ? cb.desc(path) : cb.asc(path);
        // El ID desempata para mantener páginas estables con nombres repetidos.
        query.select(root).orderBy(order, cb.asc(root.get("id")));
        return entityManager.createQuery(query)
                .setFirstResult(page * size).setMaxResults(size).getResultList();
    }

    @Override
    public Optional<Region> findById(Long id) {
        return entityManager.createQuery("SELECT DISTINCT r FROM Region r LEFT JOIN FETCH r.provinces WHERE r.id = :id", Region.class)
                .setParameter("id", id).getResultStream().findFirst();
    }

    @Override
    public boolean existsRegionByCode(String code) {
        return entityManager.createQuery("SELECT COUNT(r) FROM Region r WHERE UPPER(r.code) = :code", Long.class)
                .setParameter("code", code.toUpperCase(Locale.ROOT)).getSingleResult() > 0;
    }

    @Override
    public boolean existsRegionByCodeAndNotId(String code, Long id) {
        return entityManager.createQuery("SELECT COUNT(r) FROM Region r WHERE UPPER(r.code) = :code AND r.id <> :id", Long.class)
                .setParameter("code", code.toUpperCase(Locale.ROOT)).setParameter("id", id).getSingleResult() > 0;
    }

    @Override
    @Transactional
    public int insertRegion(Region region) {
        entityManager.persist(region);
        entityManager.flush();
        return 1;
    }

    @Override
    @Transactional
    public int updateRegion(Region region) {
        Region managed = entityManager.find(Region.class, region.getId());
        if (managed == null) return 0;
        // Actualiza solo los campos del formulario, conservando la colección de provincias.
        managed.setCode(region.getCode());
        managed.setName(region.getName());
        entityManager.flush();
        return 1;
    }

    @Override
    @Transactional
    public int deleteRegion(Long id) {
        Region entity = entityManager.find(Region.class, id);
        if (entity == null) return 0;
        entityManager.remove(entity);
        entityManager.flush();
        return 1;
    }
}
