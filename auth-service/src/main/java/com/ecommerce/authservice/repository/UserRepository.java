package com.ecommerce.authservice.repository;

import com.ecommerce.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);   // Spring сам сгенерирует SQL по имени метода

    boolean existsByEmail(String email);
}