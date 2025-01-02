package com.example.test2.Services;

import com.example.test2.Entities.Role;
import com.example.test2.Entities.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface AdminServices {
    List<User> getall();
    void UpdateROle(Long userId, Integer roleId);
    List<Role> getAllROles();
    void deleteUserById(Long id);
    long getUserIdFromUsername(String username);
     User updateUserById(long id, User updatedUser);
     Optional<User> retrieveUser(String id);

    public User handleImageFileUpload(MultipartFile fileImage, String id);
}
