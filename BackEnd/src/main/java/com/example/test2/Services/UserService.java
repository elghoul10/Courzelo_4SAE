package com.example.test2.Services;

import com.example.test2.Entities.User;

import java.util.List;

public interface UserService {

    User addUser(User user);
    User updateUser (User user);
    void Delete(int id);
    List<User> getList();
}
