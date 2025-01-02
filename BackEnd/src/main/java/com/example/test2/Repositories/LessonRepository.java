package com.example.test2.Repositories;

import com.example.test2.Entities.Course;
import com.example.test2.Entities.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByCourse(Course course);
    List<Lesson> findByCourse_IdCourse(Long idCourse);
}
