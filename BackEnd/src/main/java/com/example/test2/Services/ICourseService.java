package com.example.test2.Services;

import com.example.test2.Entities.Course;
import com.example.test2.Entities.Rating;
import com.example.test2.Entities.Specialite;
import com.example.test2.Entities.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICourseService {
    Course addClub(Course club, String nomUni,Long idUtilisateur);
    List<Specialite> getAllUniversites();

    Course getClub(Long idClub);

    List<Course> getAllClubs();

    void deleteClubById(Long idCLub);

    Course updateClub(Course club, String nomUni);

    Course handleImageFileUpload(MultipartFile fileImage, long id);


    public String shareFb(Long id);

    public  String getDefinition(String query);

    /*public  String getImageLink(String query);*/
    public String getImageUrl(String query);

    public void affecterCoursAUtilisateur(Long idCourse, Long idUtilisateur);
    public User getUser(Long idClub);
    String extractVideoId(String jsonResponse);
    public Rating saveRating(Rating rating, Long idClub) ;
    public Double calculateAverageRating(Long idClub);
    public int countUsersAssignedToCoursesCreatedByUser(Long creatorUserId);
    public List<Double> calculateMonthlyRevenue(Long userId);
}
