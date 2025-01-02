package com.example.test2.Repositories;

import com.example.test2.Entities.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken , Long> {
    PasswordResetToken findByToken(String token);
}
