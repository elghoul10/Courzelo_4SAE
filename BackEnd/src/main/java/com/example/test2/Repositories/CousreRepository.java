package com.example.test2.Repositories;

import com.example.test2.Entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CousreRepository extends JpaRepository<Course, Long> {

    @Query(value = "SELECT c FROM Course c WHERE c.createdBy.userId = :userId ORDER BY c.idCourse DESC")
    Course findLatestCourseCreatedByUser(Long userId);
}
