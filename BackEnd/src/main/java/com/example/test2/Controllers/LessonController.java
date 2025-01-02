package com.example.test2.Controllers;

import com.example.test2.Entities.Lesson;
import com.example.test2.Services.ILessonService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
public class LessonController {
    ILessonService eventService;

    @PostMapping("/dashboard/clubs/addEvent/{id}")
    Lesson addEvent(@RequestBody Lesson event, @PathVariable Long id){
        return eventService.addEvent(event,id);
    }

    @GetMapping("/dashboard/clubs/getOneEvent/{id}")
    Lesson getEvent(@PathVariable Long id){
        return eventService.getEvent(id);
    }

    @GetMapping("/events/front")
    List<Lesson> getAllEvent(){
        return eventService.getAllEvents();
    }

    @DeleteMapping("/dashboard/clubs/deleteEvent/{id}")
    void deleteEvent(@PathVariable Long id){
        this.eventService.deleteEvent(id);
    }

    @PutMapping("/dashboard/clubs/updateEvent/{id}")
    Lesson updateEvent(@RequestBody Lesson event, @PathVariable Long id){
        return  this.eventService.updateEvent(event,id);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "Requestor-Type", exposedHeaders = "X-Get-Header")
    @PostMapping("/dashboard/clubs/events/uploadImage/{id}")
    public Lesson handleImageFileUpload(@RequestParam("fileImage") MultipartFile fileImage, @PathVariable long id) {
        return eventService.handleImageFileUpload(fileImage,id);
    }

    @GetMapping("/dashboard/clubs/getLessonsByCourseId/{idCourse}")
    List<Lesson> getLessonsByCourseId(@PathVariable Long idCourse) {
        return eventService.getAllLessonsByCourseId(idCourse);
    }
}
