package com.example.test2.Controllers;

import com.example.test2.Entities.Role;
import com.example.test2.Entities.User;
import com.example.test2.Repositories.UserRepository;
import com.example.test2.Services.AdminServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController

@Tag(name = "Admin")

public class AdminController {
    @Autowired
    AdminServices adminServices;
    @Autowired
    UserRepository userRepository;

    @Operation(description = "getAllUsers")
    @GetMapping(path = "/getAllUsers")
    public List<User> getAllUsers() {
        return adminServices.getall();
    }

    @Operation(description = "Update user role")
    @PostMapping(path = "/updateUser/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody Integer roleId) {
        adminServices.UpdateROle(id, roleId);
    }


    @Operation(description = "getAllRole")
    @GetMapping(path = "/getAllRole")
    List<Role> getAllRole() {
        return adminServices.getAllROles();
    }
    @Operation(description = "delete user by id")
    @DeleteMapping("/deleteUser/{id}")
    void deleteUser(@PathVariable Long id) {
        adminServices.deleteUserById(id);
    }

    @GetMapping("/getuser/id")
    public long getUserIdFromUsername(@RequestParam String username){
        return adminServices.getUserIdFromUsername(username);
    }
    @GetMapping("/FindUserId/{id}")
    public Optional<User> retrieveUser(@PathVariable("id") String username) {
        return adminServices.retrieveUser(username);
    }
    @PutMapping("/UpdateUser/{id}")
    public User updateUserById(@PathVariable("id") long id, @RequestBody User updatedUser) {
        return adminServices.updateUserById(id, updatedUser);
    }




}
