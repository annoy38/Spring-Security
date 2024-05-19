package com.security.springsecurityjwt.repository;

import com.security.springsecurityjwt.model.UserClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface IUserRepository extends JpaRepository<UserClass, Long> {
    boolean existsByUserEmail(String userEmail);

    @Query("select u from UserClass u join fetch u.roles r where u.userEmail = :userEmail")
    Optional<UserClass> fetchUserByEmail(@Param("userEmail") String userEmail);

    UserClass findByUserId(long userId);

    @Query("SELECT u FROM UserClass u")
    List<UserClass> getAllUserData();

    @Query("select distinct u from UserClass u join u.roles r")
    List<UserClass> getAllUserWithRoleData();
}

