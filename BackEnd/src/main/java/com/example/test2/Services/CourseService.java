package com.example.test2.Services;

import com.example.test2.Entities.*;
import com.example.test2.Repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.stream.Collectors;




/*import com.google.api.Service.*;
import com.google.api.services.customsearch.Customsearch.Cse.List;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;
*/

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URLEncoder;


import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

@Service
@AllArgsConstructor
public class CourseService implements ICourseService {
    CousreRepository courseService;
    SpecialiteRepository specialiteRepository;
    FileStorageService fileStorageService;
    ReservationCourseRepository reservationCourseRepository;
    UserRepository userRepository;

    RatingRepository  ratingRepository;


    @Transactional
    @Override
    public Course addClub(Course club, String nomUni, Long idUtilisateur) {
        try {
            // Récupérer la spécialité par son nom d'université
            Specialite universite = specialiteRepository.findByNomUniversite(nomUni);
            if (universite == null) {
                throw new IllegalArgumentException("Spécialité introuvable pour l'université : " + nomUni);
            }

            // Définir la spécialité du cours
            club.setSpecialite(universite);

            // Récupérer l'utilisateur créateur par son ID
            User utilisateur = userRepository.findById(idUtilisateur)
                    .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé avec ID : " + idUtilisateur));

            // Associer l'utilisateur créateur au cours
            club.setCreatedBy(utilisateur);

            // Enregistrer le nouveau cours
            Course savedCourse = courseService.save(club);

            return savedCourse;
        } catch (Exception e) {
            // Gérer les exceptions en imprimant la trace et en relançant
            e.printStackTrace();
            throw e;
        }
    }


    @Override
    public void affecterCoursAUtilisateur(Long idCourse, Long idUtilisateur) {
        Course course = courseService.findById(idCourse).orElse(null);
        User utilisateur = userRepository.findById(idUtilisateur).orElse(null);

        if (course != null && utilisateur != null) {
            ReservationCourse reservationCourse = new ReservationCourse();
            reservationCourse.setCourses(course);
            reservationCourse.setUser(utilisateur);


            reservationCourse.setDateResevation(new Date());

            reservationCourseRepository.save(reservationCourse);
        } else {

            ReservationCourse reservationCourse = new ReservationCourse();
            reservationCourse.setCourses(course);
            reservationCourse.setUser(utilisateur);


            reservationCourse.setDateResevation(new Date());

            reservationCourseRepository.save(reservationCourse);
        }
    }
    @Override
    public List<Specialite> getAllUniversites(){
       return specialiteRepository.findAll();
    }

    @Override
    public Course getClub(Long idClub){
        return this.courseService.findById(idClub).orElse(null);
    }

    @Override
    public User getUser(Long idClub){
        return userRepository.findById(idClub).orElse(null);
    }

    @Override
    public List<Course> getAllClubs(){
        return  this.courseService.findAll();
    }

    @Override
    public void deleteClubById(Long idCLub){
        this.courseService.deleteById(idCLub);
    }


    @Override
    public Course updateClub(Course club, String nomUni) {
        Course oldClub = courseService.findById(club.getIdCourse()).orElse(null);
        Specialite universite = specialiteRepository.findByNomUniversite(nomUni);
        club.setSpecialite(universite);
        club.setImgUrl(oldClub.getImgUrl());
        return courseService.save(club);
    }

    public Course handleImageFileUpload(MultipartFile fileImage, long id) {
        if (fileImage.isEmpty()) {
            return null;
        }
        String fileName = fileStorageService.storeFile(fileImage);
        Course club = courseService.findById(id).orElse(null);
        club.setImgUrl(fileName);
        return courseService.save(club);
    }



    @Override
    public String shareFb(Long id){
        String appId = "930407181631867";
        String appSecret = "459e8c9e7384671c47216db0961d126c";
        String accessTokenString = "EAANOM02OwXsBOwKhYwE6QimCX5J1jOaSeUbPbzMZBRP4wtNJ3xyh0ZBrvNDkUOtUMulqZCQZAjNDvbhZBwgZBmzHqOHPOdeV11LbZAjftifOvxI4DcRxOyCBTKLd8XaB6GpKRE2tV7ucuBK9FfWXDtAuldSAb5PiGjerVrtGbDaFdVf5UUvz6hJU07ThTSQqvhytINOjCQYUgcnncPBa6YOvN8ZD";

        // Set up Facebook4J
        Facebook facebook = new FacebookFactory().getInstance();
        facebook.setOAuthAppId(appId, appSecret);
        facebook.setOAuthAccessToken(new AccessToken(accessTokenString, null));

        // Post a status message
        Course actualite = courseService.findById(id).orElse(null);

        String message = "New Post :" + "\n"+ actualite.getCourseTitle() + "\n" + actualite.getCourseDescription()+ "\n" + actualite.getImgUrl()+ "\n";
        try {
            facebook.postStatusMessage(message);
            return "Status message posted successfully.";
        } catch (FacebookException e) {
            e.printStackTrace();
            System.err.println("Error posting status message: " + e.getMessage());
            return  "Erreur";
        }
    }
    public  String getDefinition(String query) {
        String searchUrl = "https://www.google.com/search?q=" + query.replace(' ', '+') + "+definition";

        try {
            Document document = Jsoup.connect(searchUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3")
                    .get();

            Element definitionElement = document.selectFirst("div.BNeawe.s3v9rd.AP7Wnd");
            if (definitionElement != null) {
                return definitionElement.text();
            } else {
                Element dictionaryElement = document.selectFirst("div.BNeawe.tAd8D.AP7Wnd");
                if (dictionaryElement != null) {
                    return dictionaryElement.text();
                } else {
                    return "Definition not found.";
                }
            }
        } catch (IOException e) {
            return "Failed to retrieve definition.";
        }
    }

    public String getImageUrl(String query) {
        try {
            String encodedQuery = URLEncoder.encode(query, "UTF-8");
            String searchUrl = "https://www.googleapis.com/customsearch/v1?q=" + encodedQuery + "&cx=YOUR_CUSTOM_SEARCH_ENGINE_ID&key=YOUR_API_KEY&searchType=image";

            Document document = Jsoup.connect(searchUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3")
                    .ignoreContentType(true)
                    .get();

            String json = document.text();
            JsonElement root = JsonParser.parseString(json);

            if (root.isJsonObject()) {
                JsonObject jsonObject = root.getAsJsonObject();
                JsonArray items = jsonObject.getAsJsonArray("items");

                if (items != null && items.size() > 0) {
                    JsonObject firstItem = items.get(0).getAsJsonObject();
                    JsonElement linkElement = firstItem.get("link");

                    if (linkElement != null) {
                        return linkElement.getAsString();
                    }
                }
            }

            return "Image link not found.";
        } catch (IOException e) {
            return "Failed to retrieve image link.";
        }
    }


    /*public  String getImageLink(String query) {
        try {
            Customsearch customsearch = new Customsearch.Builder(
                    new com.google.api.client.http.javanet.NetHttpTransport(),
                    new com.google.api.client.json.jackson2.JacksonFactory(), null)
                    .setApplicationName("ImageSearch").build();

            Customsearch.Cse.List list = customsearch.cse().list(query);
            list.setKey("AIzaSyBLlgg2pLtecrC5J8pUoNEic2IqwZrGWUQ");
            list.setCx("55451c9abfb3b4b18");
            list.setSearchType("image");
            list.setNum(1L);

            Search results = list.execute();
            List<Result> items = results.getItems();
            if (items != null && !items.isEmpty()) {
                return items.get(0).getLink();
            } else {
                return "Image not found.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to retrieve image link.";
        }
    }*/

    @Override
    public String extractVideoId(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);

            if (jsonNode.has("items") && jsonNode.get("items").isArray()) {
                JsonNode firstItem = jsonNode.get("items").get(0);
                if (firstItem.has("id") && firstItem.get("id").has("videoId")) {
                    return firstItem.get("id").get("videoId").asText();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Rating saveRating(Rating rating, Long idClub) {


        try {
            Course club =  courseService.findById(idClub).orElse(null);
            rating.setCourse(club);
            return ratingRepository.save(rating);
        } catch (Exception e) {
            e.printStackTrace(); // Log or print the exception
            throw e; // Re-throw the exception if needed
        }
    }

    @Override
    public Double calculateAverageRating(Long idClub) {

        List<Rating> ratings = ratingRepository.findByCourse_IdCourse(idClub);

        OptionalDouble average = ratings.stream()
                .mapToLong(Rating::getRatingValue)
                .average();

        return average.orElse(0.0);
    }

    @Override
    public int countUsersAssignedToCoursesCreatedByUser(Long creatorUserId) {
        try {
            // Utiliser le repository ReservationCourseRepository pour compter le nombre d'utilisateurs distincts
            // (User B) ayant réservé des cours ajoutés par l'utilisateur (userId)
            return reservationCourseRepository.countDistinctUsersForCoursesCreatedByUser(creatorUserId);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Double> calculateMonthlyRevenue(Long userId) {
        // Trouver le dernier cours créé par l'utilisateur spécifié (userId)
        Course latestCourse = courseService.findLatestCourseCreatedByUser(userId);

        if (latestCourse == null) {
            return Collections.nCopies(12, 0.0); // Retourner une liste de 12 zéros si aucun cours n'est trouvé
        }

        // Initialiser une liste pour stocker le revenu mensuel de l'année
        List<Double> monthlyRevenue = new ArrayList<>(Collections.nCopies(12, 0.0));

        // Parcourir les réservations du cours
        for (ReservationCourse reservation : latestCourse.getReservationCourses()) {
            // Obtenir la date de réservation
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(reservation.getDateResevation());

            // Obtenir le mois de la réservation (de 0 à 11)
            int month = calendar.get(Calendar.MONTH);

            // Ajouter le prix du cours au revenu mensuel correspondant
            double totalRevenue = monthlyRevenue.get(month) + latestCourse.getPrix();
            monthlyRevenue.set(month, totalRevenue);
        }

        return monthlyRevenue;
    }


    public Iterable<Course> showAll()
    { return courseService.findAll();}


}





