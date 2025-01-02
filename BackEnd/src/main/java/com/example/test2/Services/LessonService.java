package com.example.test2.Services;

import com.example.test2.Entities.Course;
import com.example.test2.Entities.Lesson;
import com.example.test2.Repositories.CousreRepository;
import com.example.test2.Repositories.LessonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@AllArgsConstructor
public class LessonService implements ILessonService{
    LessonRepository lessonRepository;
    CousreRepository cousreRepository;
    FileStorageService fileStorageService;

    @Transactional
    @Override
    public Lesson addEvent(Lesson event, Long idClub){
        try {
            Course club =  cousreRepository.findById(idClub).orElse(null);
            event.setCourse(club);
            return lessonRepository.save(event);
        } catch (Exception e) {
            e.printStackTrace(); // Log or print the exception
            throw e; // Re-throw the exception if needed
        }
    }

    @Override
    public Lesson getEvent(Long idEvent){
        return this.lessonRepository.findById(idEvent).orElse(null);
    }

    @Override
    public List<Lesson> getAllEvents(){
        return this.lessonRepository.findAll();
    }

    @Override
    public void deleteEvent(Long idEvent){
         this.lessonRepository.deleteById(idEvent);
    }

    @Override
    public Lesson updateEvent(Lesson event, Long id){
        Lesson oldEvent = lessonRepository.findById(event.getIdEvent()).orElse(null);
        Course club = cousreRepository.findById(id).orElse(null);
        event.setCourse(club);
        event.setImageLesson(oldEvent.getImageLesson());
        return lessonRepository.save(event);
    }

    public Lesson handleImageFileUpload(MultipartFile fileImage, long id) {
        if (fileImage.isEmpty()) {
            return null;
        }
        String fileName = fileStorageService.storeFile(fileImage);
        Lesson event = lessonRepository.findById(id).orElse(null);
        event.setImageLesson(fileName);
        return lessonRepository.save(event);
    }

    @Override
    public List<Lesson> getAllLessonsByCourseId(Long idCourse) {
        return lessonRepository.findByCourse_IdCourse(idCourse);
    }
}
