package com.example.test2.Repositories;

import com.example.test2.Entities.ReservationCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.test2.Entities.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationCourseRepository extends JpaRepository<ReservationCourse, Long> {



    @Query("SELECT COUNT(DISTINCT rc.user) " +
            "FROM ReservationCourse rc " +
            "WHERE rc.courses.createdBy.userId = :creatorUserId")
    int countDistinctUsersForCoursesCreatedByUser(Long creatorUserId);
}
