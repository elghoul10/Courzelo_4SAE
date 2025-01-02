package com.example.test2.Repositories;


import com.example.test2.Entities.Specialite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialiteRepository extends JpaRepository<Specialite, Long> {
    Specialite findByNomUniversite(String nom);
}
