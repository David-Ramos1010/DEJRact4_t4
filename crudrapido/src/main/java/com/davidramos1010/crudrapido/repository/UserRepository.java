package com.davidramos1010.crudrapido.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.davidramos1010.crudrapido.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}