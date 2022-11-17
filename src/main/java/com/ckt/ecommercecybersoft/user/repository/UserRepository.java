package com.ckt.ecommercecybersoft.user.repository;

import com.ckt.ecommercecybersoft.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE lower(u.username)  LIKE %?1% OR lower(u.email) LIKE %?1% OR lower(u.name) LIKE %?1%")
    List<User> findUserByKeyword(String keyword, Pageable pageable);

    @Query("SELECT u FROM User u JOIN FETCH u.cart WHERE u.id = ?1")
    Optional<User> findUserWithCartById(UUID id);

    @Query("SELECT u FROM User u JOIN FETCH u.orders WHERE u.id = ?1")
    Optional<User> findUserWithPostsById(UUID id);

    @Query("SELECT u FROM User u JOIN FETCH u.posts WHERE u.id = ?1")
    Optional<User> findUserWithOrdersById(UUID id);



}
