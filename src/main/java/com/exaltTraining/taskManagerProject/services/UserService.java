package com.exaltTraining.taskManagerProject.services;

import com.exaltTraining.taskManagerProject.entities.LoginRequest;
import com.exaltTraining.taskManagerProject.entities.User;

public interface UserService {
    User registerUser(User user);
    Boolean login(String email, String password);
}
