package com.exaltTraining.taskManagerProject.services;

import com.exaltTraining.taskManagerProject.entities.User;

import java.util.List;

public interface UserService {
    User registerUser(User user);
    User login(String email, String password);
    User findUserByEmail(String email);
    List<User> getAllUsers();
}
