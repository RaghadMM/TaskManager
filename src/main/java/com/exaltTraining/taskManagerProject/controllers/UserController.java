package com.exaltTraining.taskManagerProject.controllers;

import com.exaltTraining.taskManagerProject.config.JwtService;
import com.exaltTraining.taskManagerProject.entities.LoginRequest;
import com.exaltTraining.taskManagerProject.entities.User;
import com.exaltTraining.taskManagerProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("taskManager")
public class UserController {

    private UserService userService;
    @Autowired
    private JwtService jwtService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public String createUser(@RequestBody User user) {
        try{
            userService.registerUser(user);
        }
        catch(Exception e){
            return "Error creating user: check for email validation ";
        }
        return "User registered successfully";
    }
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        User authenticatedUser =userService.login(email,password);
        if(authenticatedUser != null){
            String token = jwtService.generateToken(email,authenticatedUser.getRole().toString());
            return "User logged in successfully \n Here is the token: " + token;
        }
        else{
            return "User is not authenticated";
        }
    }



}
