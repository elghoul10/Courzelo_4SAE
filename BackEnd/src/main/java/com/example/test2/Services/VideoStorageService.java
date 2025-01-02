package com.example.test2.Services;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class VideoStorageService {

    private final Path videoStorageLocation;

    @Autowired
    public VideoStorageService(Environment env) {
        // Define the directory for video uploads
        this.videoStorageLocation = Paths.get(env.getProperty("app.video.upload-dir", "video-directory"))
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.videoStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Could not create the directory where the video files will be stored.", ex);
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null) {
            return null;
        }
        String[] fileNameParts = fileName.split("\\.");

        return fileNameParts[fileNameParts.length - 1];
    }

    // Adapted method for storing video files
    public String storeVideo(MultipartFile videoFile) {
        // Normalize video file name
        String videoFileName =
                videoFile.getOriginalFilename().split("\\.")[0] + new Date().getTime() + "-video.mp4";

        try {
            // Check if the filename contains invalid characters
            if (videoFileName.contains("..")) {
                throw new RuntimeException(
                        "Sorry! Video filename contains invalid path sequence " + videoFileName);
            }

            Path targetLocation = this.videoStorageLocation.resolve(videoFileName);
            Files.copy(videoFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return videoFileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store video file " + videoFileName + ". Please try again!", ex);
        }
    }
}
