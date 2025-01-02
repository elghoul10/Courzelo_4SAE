package com.example.test2.Controllers;

import com.example.test2.Entities.*;
import com.example.test2.Services.ICourseService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;


import java.io.BufferedReader;


import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class CourseController {

    private final ICourseService courseService;

    @PostMapping("/dashboard/clubs/addClub/{idUtilisateur}/{nomUni}")
    public Course addClub(@RequestBody Course club, @PathVariable String nomUni, @PathVariable Long idUtilisateur) {
        return courseService.addClub(club, nomUni, idUtilisateur);
    }

    @GetMapping("/dashboard/clubs/getOneClub/{id}")
    public Course getClub(@PathVariable Long id) {
        return courseService.getClub(id);
    }

    @GetMapping("/dashboard/clubs/getOneUser/{id}")
    public User getUser(@PathVariable Long id) {
        return courseService.getUser(id);
    }

    @GetMapping("/dashboard/universite")
    public List<Specialite> getAllUniversities() {
        return courseService.getAllUniversites();
    }

    @GetMapping("/dashboard/clubs")
    public List<Course> getAllClubs() {
        return courseService.getAllClubs();
    }

    @DeleteMapping("/dashboard/clubs/deleteClub/{id}")
    public void deleteClubById(@PathVariable Long id) {
        courseService.deleteClubById(id);
    }

    @PutMapping("/dashboard/clubs/updateClub/{nomUni}")
    public Course updateClub(@RequestBody Course club, @PathVariable String nomUni) {
        return courseService.updateClub(club, nomUni);
    }

    @PostMapping("/dashboard/clubs/uploadImage/{id}")
    public Course handleImageFileUpload(@RequestParam("fileImage") MultipartFile fileImage, @PathVariable long id) {
        return courseService.handleImageFileUpload(fileImage, id);
    }

    @PostMapping("/shareFb/{id}")
    public String shareFb(@PathVariable Long id) {
        return courseService.shareFb(id);
    }

    @GetMapping("/azz/{query}")
    public ResponseEntity<String> getDefinition(@PathVariable String query) {
        String definition = courseService.getDefinition(query);
        return ResponseEntity.ok(definition);
    }

    @GetMapping("/getImageUrl")
    public String getImageUrl(@RequestParam String query) {
        return "Image Link: " + courseService.getImageUrl(query);
    }

    @PostMapping("/create-meet")
    public String createMeet(@RequestBody String meetingName) {
        return "https://meet.jit.si/" + meetingName;
    }

    @PostMapping("/affecterCoursAUtilisateur")
    public void affecterCoursAUtilisateur() {
        Long idCourse = 4L;
        Long idUtilisateur = 3L;
        courseService.affecterCoursAUtilisateur(idCourse, idUtilisateur);
    }

    @GetMapping("/searchVideo")
    public String searchVideo(@RequestParam String query) {
        return courseService.extractVideoId(query);
    }

    @PostMapping("/saveRating/{idClub}")
    public Rating saveRating(@RequestBody Rating rating, @PathVariable Long idClub) {
        return courseService.saveRating(rating, idClub);
    }

    @GetMapping("/clubs/averageRating/{idClub}")
    public ResponseEntity<Double> getAverageRatingForClub(@PathVariable Long idClub) {
        Double averageRating = courseService.calculateAverageRating(idClub);
        return ResponseEntity.ok(averageRating);
    }

    @GetMapping("/dashboard/countUsersAssignedToCoursesCreatedByUser")
    public ResponseEntity<Integer> countUsersAssignedToCoursesCreatedByUser(@RequestParam Long creatorUserId) {
        int count = courseService.countUsersAssignedToCoursesCreatedByUser(creatorUserId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/monthlyRevenue/{userId}")
    public ResponseEntity<List<Double>> getMonthlyRevenue(@PathVariable Long userId) {
        List<Double> monthlyRevenue = courseService.calculateMonthlyRevenue(userId);
        return ResponseEntity.ok(monthlyRevenue);
    }
}
