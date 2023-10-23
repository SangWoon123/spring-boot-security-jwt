package com.login.jwt.repository;

import com.login.jwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    Boolean existsByUsername(String username);
    User findByUsername(String username);
}
