package com.example.test2.Repositories;


import com.example.test2.Entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByCourse_IdCourse(Long idClub);
}
