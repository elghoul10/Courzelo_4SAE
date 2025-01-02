package com.example.test2.Repositories;

import com.example.test2.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByUsername(String username);
    User findByEmail(String email);

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    User findIdByUsername(String username);
}
