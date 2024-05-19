package com.security.springsecurityjwt.repository;

import com.security.springsecurityjwt.model.UserClass;
import com.security.springsecurityjwt.service.UserServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryDAO {

    @Autowired
    private EntityManager entityManager;

    public Optional<UserClass> findByUserEmail(String userEmail){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserClass> query = criteriaBuilder.createQuery(UserClass.class);
        Root<UserClass> userRoot = query.from(UserClass.class);

        userRoot.fetch("roles", JoinType.LEFT);

        Predicate emailPredicate = criteriaBuilder.equal(userRoot.get("userEmail"), userEmail);
        query.where(emailPredicate);

        return Optional.ofNullable(entityManager.createQuery(query).getSingleResult());
    }

    public List<UserClass> findAllUsers() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserClass> query = criteriaBuilder.createQuery(UserClass.class);
        Root<UserClass> userRoot = query.from(UserClass.class);

        query.select(userRoot);

        TypedQuery<UserClass> typedQuery = entityManager.createQuery(query);
        List<UserClass> users = typedQuery.getResultList();

        users.forEach(entityManager::detach);

        return users;
    }

    public List<UserClass> findAllUsersWithRole() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserClass> query = criteriaBuilder.createQuery(UserClass.class);
        Root<UserClass> userRoot = query.from(UserClass.class);

        query.select(userRoot);

        return entityManager.createQuery(query).getResultList();
    }
}
