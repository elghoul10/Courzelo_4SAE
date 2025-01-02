package com.example.test2.Services.serviceImpl;

import com.example.test2.Entities.Role;
import com.example.test2.Entities.User;
import com.example.test2.Repositories.RoleRepository;
import com.example.test2.Repositories.UserRepository;
import com.example.test2.Services.AdminServices;
import com.example.test2.Services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AdminServicesImpl implements AdminServices {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    FileStorageService fileStorageService;
    @Override
    public List<User> getall() {
        return userRepository.findAll();
    }

    @Override
    public void UpdateROle(Long userId, Integer roleId) {
        // Fetch the user by their ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Fetch the role by its ID
        Role role = roleRepository.findById(Long.valueOf(roleId))
                .orElseThrow(() -> new RuntimeException("Role not found for id: " + roleId));

        // To replace existing roles with the new role
        Set<Role> newRoles = new HashSet<>();
        newRoles.add(role);
        user.setRoles(newRoles);

        // Alternatively, to add the new role to existing roles (uncomment if needed)
        // Set<Role> existingRoles = new HashSet<>(user.getRoles());
        // existingRoles.add(role);
        // user.setRoles(existingRoles);

        // Save the updated user
        userRepository.save(user);
    }


    @Override
    public List<Role> getAllROles() {
         return roleRepository.findAll();
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public long getUserIdFromUsername(String username) {
        User user = userRepository.findIdByUsername(username);
        if (user != null) {
            return user.getUserId();
        } else {
            // Handle case when user is not found
            return -1; // Or throw an exception, depending on your requirements
        }

    }

    @Override
    public User updateUserById(long id, User updatedUser) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setRoles(updatedUser.getRoles());
            existingUser.setPhone(updatedUser.getPhone());
            existingUser.setLasteName(updatedUser.getLasteName());
            // Update other fields as needed
            return userRepository.save(existingUser);
        } else {
            // Handle user not found
            return null;
        }
    }

    @Override
    public Optional<User> retrieveUser(String id){return userRepository.findByUsername(id);}



    @Override
    public User handleImageFileUpload(MultipartFile fileImage, String id) {
        if (fileImage.isEmpty()) {
            return null;
        }
        String fileName = fileStorageService.storeFile(fileImage);
        User user = userRepository.findIdByUsername(id);
        user.setImgUrl(fileName);
        return userRepository.save(user);
    }


}
