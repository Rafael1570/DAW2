package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.daos;

import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.entities.User;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import java.util.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** DAO manual: Hibernate implementa las operaciones JPA del EntityManager. */
@Repository
@Transactional(readOnly=true)
public class UserDAOImpl implements UserDAO {
    @PersistenceContext private EntityManager entityManager;

    public long countUsers() {
        return entityManager.createQuery("select count(u) from User u",Long.class).getSingleResult();
    }
    public List<User> listUsersPage(int page,int size,String sortField,String sortDir) {
        if(page<0 || size<1 || size>100 || (long)page*size>Integer.MAX_VALUE)
            throw new IllegalArgumentException("Pagination out of range");
        String field=Set.of("id","email","active").contains(sortField)?sortField:"email";
        boolean descending="desc".equalsIgnoreCase(sortDir);
        CriteriaBuilder cb=entityManager.getCriteriaBuilder();

        // Paginar IDs evita limitar en memoria una colección obtenida con JOIN FETCH.
        CriteriaQuery<Long> idsQuery=cb.createQuery(Long.class);
        Root<User> idsRoot=idsQuery.from(User.class);
        idsQuery.select(idsRoot.get("id")).orderBy(order(cb,idsRoot,field,descending));
        List<Long> ids=entityManager.createQuery(idsQuery)
            .setFirstResult(page*size).setMaxResults(size).getResultList();
        if(ids.isEmpty()) return List.of();

        CriteriaQuery<User> query=cb.createQuery(User.class);
        Root<User> root=query.from(User.class);
        root.fetch("roles",JoinType.LEFT);
        query.select(root).distinct(true).where(root.get("id").in(ids))
            .orderBy(order(cb,root,field,descending));
        return entityManager.createQuery(query).getResultList();
    }
    private List<Order> order(CriteriaBuilder cb,Root<User> root,String field,boolean descending) {
        Order primary=descending?cb.desc(root.get(field)):cb.asc(root.get(field));
        return List.of(primary,cb.asc(root.get("id")));
    }
    public Optional<User> findById(Long id) {
        return entityManager.createQuery("select distinct u from User u left join fetch u.roles left join fetch u.profile where u.id=:id",User.class)
            .setParameter("id",id).getResultList().stream().findFirst();
    }
    public Optional<User> findByEmail(String email) {
        return entityManager.createQuery("select distinct u from User u left join fetch u.roles left join fetch u.profile where lower(u.email)=:email",User.class)
            .setParameter("email",email.trim().toLowerCase(Locale.ROOT)).getResultList().stream().findFirst();
    }
    public boolean existsUserByEmail(String email) {
        return entityManager.createQuery("select count(u) from User u where lower(u.email)=:email",Long.class)
            .setParameter("email",email.trim().toLowerCase(Locale.ROOT)).getSingleResult()>0;
    }
    public boolean existsUserByEmailAndNotId(String email,Long id) {
        return entityManager.createQuery("select count(u) from User u where lower(u.email)=:email and u.id<>:id",Long.class)
            .setParameter("email",email.trim().toLowerCase(Locale.ROOT)).setParameter("id",id).getSingleResult()>0;
    }
    @Transactional public void insertUser(User user) {
        entityManager.persist(user);
        entityManager.flush();
    }
    @Transactional public void updateUser(User user) {
        entityManager.merge(user);
        entityManager.flush();
    }
    @Transactional public void deleteUser(Long id) {
        User user=entityManager.find(User.class,id);
        if(user!=null) {entityManager.remove(user);entityManager.flush();}
    }
}
