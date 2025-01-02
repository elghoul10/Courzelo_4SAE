package com.example.test2.Services;

import com.example.test2.Entities.Lesson;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ILessonService {
    Lesson addEvent(Lesson event, Long idClub);

    Lesson getEvent(Long idEvent);

    List<Lesson> getAllEvents();

    void deleteEvent(Long idEvent);

    Lesson updateEvent(Lesson event, Long id);

    Lesson handleImageFileUpload(MultipartFile fileImage, long id);
    List<Lesson> getAllLessonsByCourseId(Long idCourse);
}
