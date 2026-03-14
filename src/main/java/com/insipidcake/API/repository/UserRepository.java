package com.insipidcake.API.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.insipidcake.API.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}