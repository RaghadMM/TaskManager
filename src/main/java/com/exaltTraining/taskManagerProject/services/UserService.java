package com.exaltTraining.taskManagerProject.services;

import com.exaltTraining.taskManagerProject.entities.User;

public interface UserService {
    User registerUser(User user);
    User login(String email, String password);
    User findUserByEmail(String email);
}
