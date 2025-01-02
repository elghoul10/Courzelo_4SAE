package com.example.test2.Repositories;

import com.example.test2.Entities.ERole;
import com.example.test2.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role , Long> {
    Optional<Role> findByName (ERole name);
    boolean existsByName(ERole r1);

}
